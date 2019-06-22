package com.rps.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springbatch")
public class SpringBatchLauncherTest {

	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Qualifier("importJob1")
	@Autowired
	private Job job;

	@GetMapping("/execute1")
	public String executeJob1() throws Exception {
		jobLauncher.run(job, new JobParameters());
		return "executed!";
	}
}
