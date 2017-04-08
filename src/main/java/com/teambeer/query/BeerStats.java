package com.teambeer.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.teambeer.ruleengine.Expense;

public class BeerStats {
	
	private int userId;
	
	private LocalDate day;
	
	private BigDecimal beerMoney;
	
	private BigDecimal totalMoney;
	
	public BeerStats() {
		
	}
	
	public BeerStats(double beerMoney, double totalMoney) {
		this.beerMoney = new BigDecimal(beerMoney).setScale(2, RoundingMode.HALF_UP);
		this.totalMoney = new BigDecimal(totalMoney).setScale(2, RoundingMode.HALF_UP);
	}
	
	public BeerStats(Expense exp) {
		this.beerMoney = new BigDecimal(exp.spentOnBeer).setScale(2, RoundingMode.HALF_UP);
		this.totalMoney = new BigDecimal(exp.totalBill).setScale(2, RoundingMode.HALF_UP);
		this.day = exp.timeOfTransaction.toLocalDate();
	}
	
	public static BeerStats emptyStats(int userId) {
		BeerStats bs = new BeerStats();
		bs.setBeerMoney(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
		bs.setTotalMoney(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP));
		bs.setUserId(userId);
		return bs;
	}
	
	public BeerStats merge(BeerStats bs2) {
		this.beerMoney = this.beerMoney.add(bs2.beerMoney);
		this.totalMoney = this.totalMoney.add(bs2.totalMoney);
		return this;
	}

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

	public BigDecimal getBeerMoney() {
		return beerMoney;
	}

	public void setBeerMoney(BigDecimal beerMoney) {
		this.beerMoney = beerMoney;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	


}
