package com.example.demo.scheduler;

import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableScheduling
public class CustomRuntimeCron implements SchedulingConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(CustomRuntimeCron.class);
	private ScheduledTaskRegistrar taskRegistrar;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		this.taskRegistrar = taskRegistrar;
		logger.info("Scheduling configured tasks...");
		Runnable runnable = new CustomTask();
		long interval = 3000;
		long initialDelay = 5000;
		taskRegistrar.addCronTask(new CronTask(runnable, new CronTrigger("30 8 14 * * *", TimeZone.getDefault())));// 2:08:30pm
		// 5 sec initial delay as soon as app is up, then trigger job in 0/3secs.
		taskRegistrar.addFixedRateTask(new IntervalTask(runnable, interval, initialDelay)); 
		//2019-08-08 14:13:23.192  INFO 11128 --- [           main] com.example.demo.AsyncDemoApplication    : Started AsyncDemoApplication in 3.154 seconds (JVM running for 4.531)
		//2019-08-08 14:13:28.129  INFO 11128 --- [lTaskScheduler1] com.example.demo.scheduler.CustomTask    : Started executing custom task in thread ThreadPoolTaskScheduler1
	}

	@RestController
	class TestCronController{
		@GetMapping("/cronrequest")
		public void dynamicCron() {
			Runnable runnable = new CustomTask();
			long initialDelay = 5000;
			taskRegistrar.getScheduler().schedule(runnable, new CronTrigger("30 47 16 * * *", TimeZone.getDefault()));
			taskRegistrar.getScheduler().scheduleAtFixedRate(runnable, new Date(), initialDelay); 
		}
	}
}
