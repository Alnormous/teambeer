package com.teambeer.ruleengine;

import com.teambeer.beerApi.BeerPriceApi;
import com.teambeer.untappd.Untappd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Service
public class MatcherEngine {

	@Autowired
	BeerPriceApi beerPriceApi;

	@Autowired
	StarlingApi starlingApi;

	public Expense analyzeBeer(String beerName) {
		double price = beerPriceApi.findBeerPrice(beerName);
		List<TractionDetails> transactionsList = starlingApi.getLatestTrascationDetails();

		return null;

	}

}
