package com.teambeer.untappd.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Brewery implements Serializable {
	
	private static final long serialVersionUID = 5094008708818499824L;

	@JsonProperty("brewery_id")
	private Integer breweryId;
	
	@JsonProperty("brewery_name")
	private String breweryName;

	public int getBreweryId() {
		return breweryId;
	}

	public void setBreweryId(Integer breweryId) {
		this.breweryId = breweryId;
	}

	public String getBreweryName() {
		return breweryName;
	}

	public void setBreweryName(String breweryName) {
		this.breweryName = breweryName;
	}
}
