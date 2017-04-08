package com.teambeer.ruleengine;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
public class Expense {

	public double spentOnBeer;
	public double totalBill;
	public LocalDateTime timeOfTransaction;
	public String id;

	public Expense(double spentOnBeer, double totalBill, LocalDateTime timeOfTransaction) {
		this.spentOnBeer = spentOnBeer;
		this.totalBill = totalBill;
		this.timeOfTransaction = timeOfTransaction;
		this.id = UUID.randomUUID().toString();
	}


}
