package com.teambeer.query.repository;

import java.time.LocalDate;
import java.util.List;

import com.teambeer.query.BeerStats;

public interface BeerRepository {
	
	List<BeerStats> getBeerStats(int userId);
	
	BeerStats getBeerStats(int userId, LocalDate day);
	
	void addBeerStats(BeerStats beerStats, int userId);
	
}
