package com.teambeer.query.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.teambeer.query.BeerStats;

@Component
public class HashMapBeerRespositoryImpl implements BeerRepository {
	
	private Map<Integer, BeerStats> stats = new HashMap<>();

	@Override
	public BeerStats getBeerStats(int userId) {
		return stats.get(userId);
	}

	@Override
	public void addBeerStats(BeerStats beerStats, int userId) {
		stats.put(userId, beerStats);
		
	}
	

}
