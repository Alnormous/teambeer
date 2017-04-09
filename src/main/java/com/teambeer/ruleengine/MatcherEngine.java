package com.teambeer.ruleengine;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teambeer.beerApi.BeerPriceApi;
import com.teambeer.query.repository.CostsRepository;
import com.teambeer.query.repository.ExpensesRepository;
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
	
	@Autowired
	ExpensesRepository expenseRepo;
	
	@Autowired
	CostsRepository costRepo;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MatcherEngine.class);

	public Expense analyzeBeer(String beerName) {
		logger.debug("Beer recieved: " + beerName);
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
		logger.info("Beer recieved: " + beerName + " at a time");
		double price = beerPriceApi.findBeerPrice(beerName);
		final double tescoPrice = price;
		List<StarlingTransaction> transactionsList = starlingApi.latestOutBoundTransactions();
		List<MerchantLocation> merchantLocations = new ArrayList<>();
		transactionsList.forEach((t) -> {
			t.merchantLocation = starlingApi.merchantLocation(t.merchantId, t.merchantLocationId);
		});

		Comparator<StarlingTransaction> byTransactionDate = (e1, e2) ->
				e1.created.compareTo(e2.created);

		logger.info("Before filtering, transactions list is " + transactionsList.size());
		List<StarlingTransaction> transactions = transactionsList.stream().sorted(byTransactionDate)
				.filter((t) -> {
					return ACCEPTED_CODES.contains(t.merchantLocation.mastercardMerchantCategoryCode) && Math.abs(t.amount) > tescoPrice;
				}).filter(t -> (t.created.plusHours(12).isAfter(when) && t.created.minusHours(12).isBefore(when)))
				.collect(Collectors.toList());
		logger.info("Possible number of transactions: " + transactions.size());
		if (location != null) {
			logger.info("Location name comparisons");
			transactions.sort(new Comparator<StarlingTransaction>() {
				@Override
				public int compare(StarlingTransaction o1, StarlingTransaction o2) {
					return ((Integer)StringUtils.getLevenshteinDistance(o1.merchantLocation.locationName, location))
							.compareTo(StringUtils.getLevenshteinDistance(o2.merchantLocation.locationName, location));
				}
			});
			logger.info("sorting done");
		} else {
			logger.info("Timing stuff");
			Long epochSecond = when.toInstant(ZoneOffset.UTC).getEpochSecond();
			transactions.sort(new Comparator<StarlingTransaction>() {
				@Override
				public int compare(StarlingTransaction o1, StarlingTransaction o2) {
					
					return ((Long)(o2.created.toInstant(ZoneOffset.UTC).getEpochSecond() - epochSecond)).compareTo(
							(Long)(o1.created.toInstant(ZoneOffset.UTC).getEpochSecond() - epochSecond));
				}
			});
		}
		logger.info("Possible number of transactions 2: " + transactions.size());
		if (transactions.size() > 0) {
			logger.info("Beer " + beerName + " has possible transactions " + transactions.get(0).id);
	
			Iterator<StarlingTransaction> iterator = transactions.iterator();
			while (iterator.hasNext()) {
				StarlingTransaction t = iterator.next();
				Double totalBeer = expenseRepo.getAll().stream().filter(x -> x.transactionId != null && x.transactionId.equals(t.id))
						.collect(Collectors.summingDouble(x -> x.spentOnBeer));
				Double totalBill = expenseRepo.getAll().stream().filter(x -> x.transactionId != null && x.transactionId.equals(t.id)).findAny().orElse(new Expense(0, 0, null, null, null, null)).totalBill;
				if (totalBill == 0) {
					totalBill = -(t.amount);
				}
				Double remainder = totalBill - totalBeer;
				logger.info("Total bill: " + totalBill + " total beer: " + totalBeer + " and remained is " + remainder);
				if (remainder > 0) {
					if (Math.abs(remainder - price) < (price * 0.2)) {
						System.out.println(price + " " + remainder);
						price = remainder;
					}
					List<Cost> costs = costRepo.getByLocalId(t.merchantLocationId);
					if (costs != null && costs.size() > 0) {
						Optional<Cost> cost = costs.stream().filter(c -> c.beerName.equals(beerName)).findAny();
						if (cost.isPresent()) {
							price = cost.get().cost;
						}
					}
					logger.info("Setting expense, with price " + price);
					Expense e = new Expense(price, Math.abs(t.amount), 
							when, beerName, 
							t.merchantLocation.locationName,
							t.merchantLocationId,
							t.id);
					expenseRepo.storeExpense(e);
					logger.info("We fucking used this expense like a mother fucker");
					triggerAddingNonBeerTransactions(transactionsList);
					return e;
				} else {
					logger.info("We fucked this one right off");
				}
			}
		}
			
		logger.info("We're sending a zero expense");
		Expense e = new Expense(0.00, 0.00, 
				when, beerName, 
				null,
				null,
				UUID.randomUUID().toString());
		expenseRepo.storeExpense(e);
		triggerAddingNonBeerTransactions(transactionsList);
		return e;
	}

	
	
	public void triggerAddingNonBeerTransactions(List<StarlingTransaction> transactionsList) {
		for (StarlingTransaction transaction: transactionsList) {
			if (expenseRepo.getAllWithTransactionId(transaction.id).isEmpty()) {
				Expense e = new Expense(0.00, -transaction.amount, 
						transaction.created, "No beer", 
						null,
						null,
						transaction.id);
				expenseRepo.storeExpense(e);
			}
		}
	}

}
