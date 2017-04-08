package com.teambeer.query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.teambeer.query.repository.*;
import com.teambeer.ruleengine.Cost;
import com.teambeer.ruleengine.Expense;

@RestController
public class BeerStatsController {
	
	@Autowired
	private BeerRepository beerRepo;
	
	@Autowired
	private ExpensesRepository expenseRepo;
	
	@Autowired
	private CostsRepository costsRepo;
	
	@PostConstruct
	public void fillRepo() {
		expenseRepo.storeExpense(new Expense(1.00, 10.00, LocalDateTime.now(), "Punk IPA", "Pub 1", "23rewf"));
		expenseRepo.storeExpense(new Expense(5.00, 11.20, LocalDateTime.now(), "Punk IPA", "Pub 2", "wiaeudgfvhb"));
		expenseRepo.storeExpense(new Expense(3.00, 22.00, LocalDateTime.now(), "Dead Pony Club", "Pub 1", "23rewf"));
		expenseRepo.storeExpense(new Expense(2.00, 10.00, LocalDateTime.now().minusDays(1), "Dead Pony Club", "Pub 1", "23rewf"));
		expenseRepo.storeExpense(new Expense(5.00, 22.00, LocalDateTime.now().minusDays(1), "Crownie", "Pub 2", "wiaeudgfvhb"));
		expenseRepo.storeExpense(new Expense(0.00, 10.00, LocalDateTime.now().minusDays(1), "Kozsci", "Pub 1", "23rewf"));
		expenseRepo.storeExpense(new Expense(7.20, 10.00, LocalDateTime.now().minusDays(3), "New", "Pub 2", "wiaeudgfvhb"));
		expenseRepo.storeExpense(new Expense(10.00, 10.00, LocalDateTime.now().minusDays(3), "Old", "Pub 2", "wiaeudgfvhb"));
		expenseRepo.storeExpense(new Expense(0.00, 20.00, LocalDateTime.now().minusDays(3), "Kozsci", "Pub 1", "23rewf"));
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
	
	@RequestMapping("/query/expenses/{id}")
	public List<Expense> getExpenses(@PathVariable("id") int userId) {
		return new ArrayList<>(expenseRepo.getAll());
	}
	
	@RequestMapping(value="/query/expense/{id}/cost/{cost}", method=RequestMethod.POST)
	public void setExpense(@PathVariable("id") String id, @PathVariable("cost") double cost) {
		Expense e = expenseRepo.getByIdExpense(id);
		if (e != null) {
			e.spentOnBeer = cost;
			expenseRepo.updateExpense(e);
		}
		Cost c = new Cost();
		c.beerName = e.description;
		c.mastercardLocatinId = e.mastercardLocationId;
		costsRepo.storeCost(c);
	}

}
