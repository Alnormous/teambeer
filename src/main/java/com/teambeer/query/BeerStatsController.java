package com.teambeer.query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teambeer.query.repository.*;

@RestController
public class BeerStatsController {
	
	@Autowired
	private BeerRepository beerRepo;
	
	@PostConstruct
	public void fillRepo() {
		// Get rid of this once we have data.
		BeerStats bs = new BeerStats();
		bs.setBeerMoney(1.00);
		bs.setTotalMoney(2.00);
		bs.setDay(LocalDate.now());
		bs.setUserId(1);
		beerRepo.addBeerStats(bs, 1);
		
		BeerStats bs2 = new BeerStats();
		bs2.setBeerMoney(1.10);
		bs2.setTotalMoney(3.00);
		bs2.setDay(LocalDate.now().minusDays(1));
		bs2.setUserId(1);
		beerRepo.addBeerStats(bs2, 1);
	}
	
	@RequestMapping("/query/beerstats/{id}")
	public List<BeerStats> getBeerStats(@PathVariable("id") int userId) {
		return beerRepo.getBeerStats(userId);
	}
	
	@RequestMapping("/query/beerstats/{id}/day/{day}")
	public BeerStats getBeerStats(@PathVariable("id") int userId, @PathVariable("day") int daysAgo) {
		LocalDate dayToQuery = LocalDate.now().minusDays(daysAgo);
		BeerStats bs = beerRepo.getBeerStats(userId, dayToQuery);
		if (bs == null) {
			bs = new BeerStats();
			bs.setDay(dayToQuery);
			bs.setBeerMoney(0);
			bs.setTotalMoney(0);
			bs.setUserId(userId);
		}
		return bs;
	}

}
