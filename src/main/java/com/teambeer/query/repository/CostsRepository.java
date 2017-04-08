package com.teambeer.query.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.teambeer.ruleengine.Cost;

@Component
public class CostsRepository {
	
	private static final String COSTS = "costs";

	@Autowired
	private HazelcastInstance hazelcast;
	
	private IMap<String, List<Cost>> costs;
	
	@PostConstruct
	public void init() {
		costs = hazelcast.getMap(COSTS);
	}

	public void storeCost(Cost cost) {
		if (!costs.containsKey(cost.mastercardLocatinId)) {
			costs.put(cost.mastercardLocatinId, new ArrayList<>());
		}
		costs.get(cost.mastercardLocatinId).add(cost);
	}

	public List<Cost> getByLocalId(String id) {
		return costs.get(id);
	}

}
