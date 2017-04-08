package com.teambeer.query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teambeer.query.repository.*;
import com.teambeer.ruleengine.Expense;

@RestController
public class BeerStatsController {
	
	@Autowired
	private BeerRepository beerRepo;
	
	@Autowired
	private ExpensesRepository expenseRepo;
	
	@PostConstruct
	public void fillRepo() {
		expenseRepo.storeExpense(new Expense(1.00, 10.00, LocalDateTime.now(), "", ""));
		expenseRepo.storeExpense(new Expense(5.00, 11.20, LocalDateTime.now(), "", ""));
		expenseRepo.storeExpense(new Expense(3.00, 22.00, LocalDateTime.now(), "", ""));
		expenseRepo.storeExpense(new Expense(2.00, 10.00, LocalDateTime.now().minusDays(1), "", ""));
		expenseRepo.storeExpense(new Expense(5.00, 22.00, LocalDateTime.now().minusDays(1), "", ""));
		expenseRepo.storeExpense(new Expense(0.00, 10.00, LocalDateTime.now().minusDays(1), "", ""));
		expenseRepo.storeExpense(new Expense(7.00, 10.00, LocalDateTime.now().minusDays(3), "", ""));
		expenseRepo.storeExpense(new Expense(10.00, 10.00, LocalDateTime.now().minusDays(3), "", ""));
		expenseRepo.storeExpense(new Expense(0.00, 20.00, LocalDateTime.now().minusDays(3), "", ""));
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
