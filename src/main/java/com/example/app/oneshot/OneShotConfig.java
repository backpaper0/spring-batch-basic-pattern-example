package com.example.app.oneshot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OneShotConfig {

	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepBuilderFactory steps;
	@Autowired
	private DeleteAllAddressTasklet deleteAllAddressTasklet;

	@Bean
	public Step oneShotStep() {
		return steps.get("oneShotStep")
				.tasklet(deleteAllAddressTasklet)
				.build();
	}

	@Bean
	public Job oneShotJob(Step oneShotStep) {
		return jobs.get("oneShotJob")
				.start(oneShotStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
