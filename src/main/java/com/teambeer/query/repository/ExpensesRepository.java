package com.teambeer.query.repository;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.teambeer.ruleengine.Expense;

/**
 * Created by artyom.fedenka on 4/8/17.
 * ONWARDS!!!!
 */
@Service
public class ExpensesRepository {

	private static final String EXPENSES = "expenses";

	@Autowired
	private HazelcastInstance hazelcast;

	private IMap<String, Expense> expenses;

	@PostConstruct
	public void init() {
		expenses = hazelcast.getMap(EXPENSES);
	}

	public void storeExpense(Expense expense) {
		expenses.put(expense.id, expense);
	}

	public Expense getByIdExpense(String id) {
		return expenses.get(id);
	}

	public Collection<Expense> getAll() {
		return expenses.values();
	}

	public void removeById(String id) {
		expenses.remove(id);
	}



}
