package com.teambeer.query;

import com.teambeer.beerApi.BeerPriceApi;
import com.teambeer.ruleengine.Expense;
import com.teambeer.ruleengine.MatcherEngine;
import com.teambeer.starlingApi.StarlingService;
import com.teambeer.starlingApi.objects.StarlingTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@RestController
public class TestController {

	@Autowired
	StarlingService starlingService;


	@RequestMapping("/pay")
	public void pay() {
		starlingService.makeLocalPayment("f2654368-7799-44e4-8b39-636da8fcb48e", 2.0);
	}
}
