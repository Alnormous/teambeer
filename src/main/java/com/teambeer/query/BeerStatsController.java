package com.teambeer.query;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerStatsController {
	
	
	@RequestMapping("/query/beerstats/{id}")
	public BeerStats getBeerStats(@PathVariable("id") int userId) {
		return null;
	}
	

}
