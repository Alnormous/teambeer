package com.teambeer.matcher;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.teambeer.ruleengine.Expense;
import com.teambeer.ruleengine.MatcherEngine;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatcherEnginerTest {
	
	@Autowired
	MatcherEngine engine;
	
	@Test
	public void testMatcher() {
		Expense e = engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		System.out.println(e);
		System.out.println(e.spentOnBeer);
	}
	
	@Test
	public void testMatcher2() {
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		Expense e = engine.analyzeBeer("Punk IPA", LocalDateTime.now(), "Old Fountain");
		System.out.println(e);
		System.out.println(e.spentOnBeer);
	}

}
