package com.teambeer.query;

import java.time.LocalDate;

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
