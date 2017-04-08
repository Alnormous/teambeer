package com.teambeer.ruleengine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teambeer.beerApi.BeerPriceApi;
import com.teambeer.starlingApi.StarlingService;
import com.teambeer.starlingApi.objects.MerchantLocation;
import com.teambeer.starlingApi.objects.StarlingTransaction;

@Service
public class MatcherEngine {

	@SuppressWarnings("serial")
	List<Integer> ACCEPTED_CODES = new ArrayList<Integer>() {{
		add(5812);
		add(5813);
	}};

	@Autowired
	BeerPriceApi beerPriceApi;

	@Autowired
	StarlingService starlingApi;

	public Expense analyzeBeer(String beerName) {
		double price = beerPriceApi.findBeerPrice(beerName);
		List<StarlingTransaction> transactionsList = starlingApi.latestOutBoundTransactions();
		List<MerchantLocation> merchantLocations = new ArrayList<>();
		transactionsList.forEach((t) -> {
			t.merchantLocation = starlingApi.merchantLocation(t.merchantId, t.merchantLocationId);
		});

		Comparator<StarlingTransaction> byTransactionDate = (e1, e2) ->
				e1.created.compareTo(e2.created);

		Optional<StarlingTransaction> transactionsOutOptional = transactionsList.stream().sorted(byTransactionDate).filter((t) -> {
			return ACCEPTED_CODES.contains(t.merchantLocation.mastercardMerchantCategoryCode) && Math.abs(t.amount) > price;
		}).findFirst();

		StarlingTransaction transactionsOut = transactionsOutOptional.get();
		return new Expense(price, Math.abs(transactionsOut.amount), 
				transactionsOut.created, beerName, 
				transactionsOut.merchantLocation.locationName,
				transactionsOut.merchantLocationId);
	}
	
	public Expense analyzeBeer(String beerName, LocalDateTime when, String location) {
		double price = beerPriceApi.findBeerPrice(beerName);
		List<StarlingTransaction> transactionsList = starlingApi.latestOutBoundTransactions();
		List<MerchantLocation> merchantLocations = new ArrayList<>();
		transactionsList.forEach((t) -> {
			t.merchantLocation = starlingApi.merchantLocation(t.merchantId, t.merchantLocationId);
		});

		Comparator<StarlingTransaction> byTransactionDate = (e1, e2) ->
				e1.created.compareTo(e2.created);

		List<StarlingTransaction> transactions = transactionsList.stream().sorted(byTransactionDate)
				.filter((t) -> {
					return ACCEPTED_CODES.contains(t.merchantLocation.mastercardMerchantCategoryCode) && Math.abs(t.amount) > price;
				}).filter(t -> (t.created.plusHours(5).isAfter(when) && t.created.minusHours(5).isBefore(when)))
				.collect(Collectors.toList());
		if (location != null) {
			transactions.sort(new Comparator<StarlingTransaction>() {
				@Override
				public int compare(StarlingTransaction o1, StarlingTransaction o2) {
					return ((Integer)StringUtils.getLevenshteinDistance(o2.merchantLocation.locationName, location))
							.compareTo(StringUtils.getLevenshteinDistance(o1.merchantLocation.locationName, location));
				}
			});
		}

		StarlingTransaction transactionsOut = transactions.get(0);
		if (transactionsOut == null) {
			return analyzeBeer(beerName);
		}
		
		
		return new Expense(price, Math.abs(transactionsOut.amount), 
				transactionsOut.created, beerName, 
				transactionsOut.merchantLocation.locationName,
				transactionsOut.merchantLocationId);
	}

}
