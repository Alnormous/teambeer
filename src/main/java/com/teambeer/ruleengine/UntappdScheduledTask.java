package com.teambeer.ruleengine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teambeer.query.repository.CheckinRepository;
import com.teambeer.untappd.UntappdController;
import com.teambeer.untappd.UserNotFoundException;

import javax.annotation.PostConstruct;

@Component
@EnableScheduling
public class UntappdScheduledTask {

	@Value("${untappd.user}")
	private String user;

	@Autowired
	private UntappdController untappd;
	
	@Autowired
	private CheckinRepository checkinRepo;
	
	@Autowired
	private MatcherEngine matcherEngine;

	private Logger log = LoggerFactory.getLogger(UntappdScheduledTask.class);

	
	@Scheduled(cron = "*/45 * * * * *")
	//@PostConstruct
	public void getUntappdCheckins() throws UserNotFoundException {
		log.info("Getting checkins from Untappd for user {}", user);
		untappd.getCheckinsByUser(user).forEach(item -> {
			if (checkinRepo.storeCheckin(item)) {
				log.info("### STORING & ANALYSING: " + item.getCheckinId() + " : " + item.getCreatedAt() + " : " + item.getBeer().getBeerName());
				final LocalDateTime dateTime = LocalDateTime.parse(item.getCreatedAt(), DateTimeFormatter.RFC_1123_DATE_TIME);
				final String venueName = item.getVenue() != null ? item.getVenue().getVenueName() : null;
				matcherEngine.analyzeBeer(item.getBeer().getBeerName(), dateTime, venueName);
			}
		});
	}

}
