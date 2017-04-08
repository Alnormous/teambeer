package com.teambeer.query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
		bs.setBeerMoney(new BigDecimal(1.00));
		bs.setTotalMoney(new BigDecimal(2.00));
		bs.setDay(LocalDate.now());
		bs.setUserId(1);
		beerRepo.addBeerStats(bs, 1);
		
		BeerStats bs2 = new BeerStats();
		bs2.setBeerMoney(new BigDecimal(1.10));
		bs2.setTotalMoney(new BigDecimal(3.00));
		bs2.setDay(LocalDate.now().minusDays(1));
		bs2.setUserId(1);
		beerRepo.addBeerStats(bs2, 1);
	}
	
	@RequestMapping("/query/beerstats/{id}")
	public List<BeerStats> getBeerStats(@PathVariable("id") int userId) {
		return beerRepo.getBeerStats(userId);
	}
	
	@RequestMapping("/query/beerstats/total/{id}")
	public BeerStats getBeerStatsTotal(@PathVariable("id") int userId) {
		return beerRepo.getBeerStats(userId).stream().reduce(BeerStats.emptyStats(userId), (bs1, bs2) -> bs1.merge(bs2));
	}
	
	@RequestMapping("/query/beerstats/{id}/day/{day}")
	public BeerStats getBeerStats(@PathVariable("id") int userId, @PathVariable("day") int daysAgo) {
		LocalDate dayToQuery = LocalDate.now().minusDays(daysAgo);
		BeerStats bs = beerRepo.getBeerStats(userId, dayToQuery);
		if (bs == null) {
			bs = BeerStats.emptyStats(userId);
			bs.setDay(dayToQuery);
		}
		return bs;
	}

}
