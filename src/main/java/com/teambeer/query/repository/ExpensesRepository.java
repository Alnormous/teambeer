package com.teambeer.query.repository;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.teambeer.ruleengine.Expense;

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
	
	public void updateExpense(Expense expense) {
		expenses.replace(expense.id, expense);
	}
	
	public Collection<Expense> getAllWithTransactionId(final String transactionId) {
		Collection<Expense> all = getAll();
		return all.stream().filter(e -> e.transactionId.equals(transactionId)).collect(Collectors.toList());
	}



}
