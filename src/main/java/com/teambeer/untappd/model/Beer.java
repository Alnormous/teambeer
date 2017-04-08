package com.teambeer.untappd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Beer {
	
	private Long bid;
	
	@JsonProperty("beer_name")
	private String beerName;

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public String getBeerName() {
		return beerName;
	}

	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}
}	