package com.teambeer.query.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teambeer.query.BeerStats;

@Component
public class HashMapBeerRespositoryImpl implements BeerRepository {


	private Map<Integer, List<BeerStats>> stats = new HashMap<>();

	@Override
	public List<BeerStats> getBeerStats(int userId) {
		return stats.get(userId);
	}

	@Override
	public void addBeerStats(BeerStats beerStats, int userId) {
		if (!stats.containsKey(userId)) {
			stats.put(userId, new ArrayList<>());
			stats.get(userId).add(beerStats);
		} else {
			Optional<BeerStats> existingStat = stats.get(userId).stream()
					.filter(s -> s.getDay() != null)
					.filter(s -> s.getDay().isEqual(beerStats.getDay()))
					.findAny();
			if (existingStat.isPresent()) {
				stats.get(userId).remove(existingStat.get());
			}
			stats.get(userId).add(beerStats);
		}
	}

	@Override
	public BeerStats getBeerStats(int userId, LocalDate day) {
		if (stats.containsKey(userId)) {
			return stats.get(userId).stream().filter(bs -> bs.getDay().isEqual(day)).findFirst().orElse(null);
		} else {
			return null;
		}
	}
	

}
