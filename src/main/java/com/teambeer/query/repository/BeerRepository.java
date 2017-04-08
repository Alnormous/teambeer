package com.teambeer.query.repository;

import com.teambeer.query.BeerStats;

public interface BeerRepository {
	
	BeerStats getBeerStats(int userId);
	
	void addBeerStats(BeerStats beerStats, int userId);

}
