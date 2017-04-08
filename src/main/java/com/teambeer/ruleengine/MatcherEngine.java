package com.teambeer.ruleengine;

import com.teambeer.beerApi.BeerPriceApi;
import com.teambeer.starlingApi.StarlingService;
import com.teambeer.starlingApi.TransactionsOut;
import com.teambeer.untappd.Untappd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Service
public class MatcherEngine {

	@Autowired
	BeerPriceApi beerPriceApi;

	@Autowired
	StarlingService starlingApi;

	public Expense analyzeBeer(String beerName) {
		double price = beerPriceApi.findBeerPrice(beerName);
		List<TransactionsOut> transactionsList = starlingApi.latestTransactions();

		Comparator<TransactionsOut> byTransactionDate = (e1, e2) ->
				e1.getTransactiontime().compareTo(e2.getTransactiontime());

		transactionsList.stream().sorted(byTransactionDate).findFirst();
		return null;

	}

}
