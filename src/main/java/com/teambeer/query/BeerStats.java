package com.teambeer.query;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class BeerStats {
	
	private int userId;
	
	private LocalDate day;
	
	private double beerMoney;
	
	private double totalMoney;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public double getBeerMoney() {
		return beerMoney;
	}

	public void setBeerMoney(double beerMoney) {
		this.beerMoney = beerMoney;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	


}
