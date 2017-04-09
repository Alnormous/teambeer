package com.teambeer.query.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teambeer.query.BeerStats;

@Component
public class BeerRespositoryImpl implements BeerRepository {
	
	@Autowired
	ExpensesRepository expensesRepository;


	@Override
	public BeerStats getBeerStats(int userId, LocalDate day) {
		BeerStats bs = expensesRepository.getAll().stream()
			.filter(exp -> exp.timeOfTransaction != null && exp.timeOfTransaction.truncatedTo(ChronoUnit.DAYS).toLocalDate().isEqual(day))
			.map(exp -> new BeerStats(exp))
			.reduce(BeerStats.emptyStats(userId), (bs1, bs2) -> bs1.merge(bs2));
		bs.setDay(day);
		Double totalExpenses = expensesRepository.getAll().stream()
			.filter(exp -> exp.timeOfTransaction != null && exp.timeOfTransaction.truncatedTo(ChronoUnit.DAYS).toLocalDate().isEqual(day))
			.collect(Collectors.summingDouble(e -> e.totalBill));
		bs.setTotalMoney(new BigDecimal(totalExpenses));
		return bs;
	}
	
	@Override
	public List<BeerStats> getBeerStats(int userId) {
		List<BeerStats> stats = new ArrayList<>();
		LocalDate day = LocalDate.now();
		for (int i = 0; i < 10; i++) {
			stats.add(getBeerStats(userId, day));
			day = day.minusDays(1);
		}
		return stats;
	}
	

}
