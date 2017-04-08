package com.teambeer.ruleengine;

import com.teambeer.beerApi.BeerPriceApi;
import com.teambeer.starlingApi.StarlingService;
import com.teambeer.starlingApi.TransactionsOut;
import com.teambeer.untappd.Untappd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Service
public class MatcherEngine {

	List<String> ACCEPTED_CODES = new ArrayList<String>() {{
		add("5812");
		add("5813");
	}};

	@Autowired
	BeerPriceApi beerPriceApi;

	@Autowired
	StarlingService starlingApi;

	public Expense analyzeBeer(String beerName) {
		double price = beerPriceApi.findBeerPrice(beerName);
		List<TransactionsOut> transactionsList = starlingApi.latestTransactions();

		Comparator<TransactionsOut> byTransactionDate = (e1, e2) ->
				e1.getTransactiontime().compareTo(e2.getTransactiontime());

		Optional<TransactionsOut> transactionsOutOptional = transactionsList.stream().sorted(byTransactionDate).filter((t) -> {
			return ACCEPTED_CODES.contains(t.getMerchantcode());
		}).findFirst();

		TransactionsOut transactionsOut = transactionsOutOptional.get();
		Expense expense = new Expense(price, transactionsOut.getTransactionamount(), transactionsOut.getTransactiontime());

		return expense;

	}

}
