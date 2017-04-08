package com.teambeer.untappd.model;

import java.time.LocalDateTime;

public class Checkin {
	
	private long checkinId;
	
	private LocalDateTime createdAt;
	
	private String venueName;
	
	private String primaryCategory;
	
	private Location location;
	
	public Checkin(long checkinId, LocalDateTime createdAt, String venueName, String primaryCategory, Location location) {
		this.checkinId = checkinId;
		this.createdAt = createdAt;
		this.venueName = primaryCategory;
		this.location = location;
	}

	public long getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(long checkinId) {
		this.checkinId = checkinId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getPrimaryCategory() {
		return primaryCategory;
	}

	public void setPrimaryCategory(String primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
