package com.teambeer.untappd.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {
	
	private static final long serialVersionUID = 2673190868012237726L;

	@JsonProperty("venue_address")
	private String venueAddress;
	
	@JsonProperty("venue_city")
	private String venueCity;
	
	@JsonProperty("venue_state")
	private String venueState;
	
	@JsonProperty("venue_country")
	private String venueCountry;

	private Double lat;
	
	private Double lng;

	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	public String getVenueCity() {
		return venueCity;
	}

	public void setVenueCity(String venueCity) {
		this.venueCity = venueCity;
	}

	public String getVenueState() {
		return venueState;
	}

	public void setVenueState(String venueState) {
		this.venueState = venueState;
	}

	public String getVenueCountry() {
		return venueCountry;
	}

	public void setVenueCountry(String venueCountry) {
		this.venueCountry = venueCountry;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
}
