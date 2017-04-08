package com.teambeer.query.repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teambeer.query.BeerStats;

@Component
public class HashMapBeerRespositoryImpl implements BeerRepository {
	
	@Autowired
	ExpensesRepository expensesRepository;


	@Override
	public BeerStats getBeerStats(int userId, LocalDate day) {
		return expensesRepository.getAll().stream()
			.filter(exp -> exp.timeOfTransaction.truncatedTo(ChronoUnit.DAYS).toLocalDate().isEqual(day))
			.map(exp -> new BeerStats(exp))
			.reduce(BeerStats.emptyStats(userId), (bs1, bs2) -> bs1.merge(bs2));
	}
	
	@Override
	public List<BeerStats> getBeerStats(int userId) {
		List<BeerStats> stats = new ArrayList<>();
		LocalDate day = LocalDate.now();
		for (int i = 0; i < 10; i++) {
			stats.add(getBeerStats(userId, day));
			day.minusDays(1);
		}
		return stats;
	}
	

}
