package com.teambeer.untappd.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Checkins {

	private Integer count;
	
	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
