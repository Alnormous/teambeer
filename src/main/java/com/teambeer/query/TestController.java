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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class TestController {

	@Autowired
	MatcherEngine marcherService;

	@RequestMapping("/starling")
	public Expense starling(@RequestParam("name") String name) {
		return marcherService.analyzeBeer(name);
	}
	
	@RequestMapping("/starling/{name}/date/{date}/location/{location}")
	public Expense starling(@RequestParam("name") String name, @RequestParam("date") String date, @RequestParam("location") String location) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDateTime localDate = LocalDateTime.parse(date, formatter);
		return marcherService.analyzeBeer(name, localDate, location);
	}
}
