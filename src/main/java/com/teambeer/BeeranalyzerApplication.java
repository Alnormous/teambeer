package com.teambeer;

import com.teambeer.ruleengine.UntappdScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeeranalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeeranalyzerApplication.class, args);
	}
}
