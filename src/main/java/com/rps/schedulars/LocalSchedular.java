package com.rps.schedulars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LocalSchedular {
	private Logger logger = LoggerFactory.getLogger(LocalSchedular.class);
	
	@Scheduled(cron = "0 * 9 * * ?")
	public void job1() {
		logger.debug("CRON - JOB Execution");
	}
	
	@Scheduled(fixedRate = 1000, initialDelay = 3000)
	public void job2() {
		logger.debug("CRON Fixed - JOB Execution");
	}
	
	
	
}
