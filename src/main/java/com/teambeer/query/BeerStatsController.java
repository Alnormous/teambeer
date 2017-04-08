package com.teambeer.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teambeer.query.repository.*;

@RestController
public class BeerStatsController {
	
	@Autowired
	private BeerRepository beerRepo;
	
	@RequestMapping("/query/beerstats/{id}")
	public BeerStats getBeerStats(@PathVariable("id") int userId) {
		return null;
	}

}
