package com.teambeer.query.repository;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.teambeer.untappd.model.Item;

@Service
public class CheckinRepository {
	
	private static final String UNTAPPD_CHECKINS = "checkins";

	@Autowired
	private HazelcastInstance hazelcast;

	private IMap<Long, Item> checkins;

	@PostConstruct
	public void init() {
		checkins = hazelcast.getMap(UNTAPPD_CHECKINS);
	}

	public boolean storeCheckin(Item checkin) {
		if (!checkins.containsKey(checkin.getCheckinId())) {
			storeCheckin(checkin);
			return true;
		}
		return false;
	}

	public Item getByCheckin(Item checkin) {
		return checkins.get(checkin.getCheckinId());
	}

	public Collection<Item> getAll() {
		return checkins.values();
	}

	public void removeCheckin(Item checkin) {
		checkins.remove(checkin.getCheckinId());
	}
	
	public void updateCheckin(Item checkin) {
		checkins.replace(checkin.getCheckinId(), checkin);
	}
}
