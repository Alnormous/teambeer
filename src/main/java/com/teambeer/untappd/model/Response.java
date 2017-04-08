package com.teambeer.untappd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	
	private Checkins checkins;

	public Checkins getCheckins() {
		return checkins;
	}

	public void setCheckins(Checkins checkins) {
		this.checkins = checkins;
	}
}
