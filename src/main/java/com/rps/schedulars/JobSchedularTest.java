package com.rps.schedulars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobSchedularTest {
	private Logger logger = LoggerFactory.getLogger(LocalSchedular.class);

	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("importJob1")
	@Autowired
	private Job job;
	
	
	@Scheduled(cron = "0 */1 * * * ?")
	public void job1() throws Exception {
		JobParameters jp = new JobParametersBuilder()
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
		jobLauncher.run(job, jp);
	}
	
}
