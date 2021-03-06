package com.teambeer.ruleengine;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Expense implements Serializable {

	public double spentOnBeer;
	public double totalBill;
	public LocalDateTime timeOfTransaction;
	public String description;
	public String location;
	public String mastercardLocationId;
	public String id;
	public String transactionId;
	public boolean paid;

	public Expense(double spentOnBeer, double totalBill, LocalDateTime timeOfTransaction, String description, String location, String locationId) {
		this.spentOnBeer = spentOnBeer;
		this.totalBill = totalBill;
		this.timeOfTransaction = timeOfTransaction;
		this.id = UUID.randomUUID().toString();
		this.location = location;
		this.mastercardLocationId = locationId;
		this.description = description;
		this.paid = false;
	}
	
	public Expense(double spentOnBeer, double totalBill, LocalDateTime timeOfTransaction, String description, String location, String locationId, String transactionId) {
		this.spentOnBeer = spentOnBeer;
		this.totalBill = totalBill;
		this.timeOfTransaction = timeOfTransaction;
		this.id = UUID.randomUUID().toString();
		this.location = location;
		this.mastercardLocationId = locationId;
		this.description = description;
		this.transactionId = transactionId;
	}


}
