package com.teambeer.untappd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
	
	@JsonProperty("checkin_id")
	private Long checkinId;
	
	@JsonProperty("created_at")
	private String createdAt;

	private Beer beer;
	
	private Brewery brewery;

	/* NOTE: Removed for now as this is not working. */
	//public Venue venue;

	public Long getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(Long checkinId) {
		this.checkinId = checkinId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Beer getBeer() {
		return beer;
	}

	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	public Brewery getBrewery() {
		return brewery;
	}

	public void setBrewery(Brewery brewery) {
		this.brewery = brewery;
	}

	/*public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}*/
}
