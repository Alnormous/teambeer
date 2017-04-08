package com.teambeer.ruleengine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
		return new Expense(price, Math.abs(transactionsOut.amount), transactionsOut.created, beerName, transactionsOut.merchantLocation.locationName);
	}

}
