package com.acg.utilities;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class CustomCron implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		Runnable runnable = new CustomTask();
		long interval = 3000;
		long initialDelay = 5000;
		taskRegistrar.addCronTask(new CronTask(runnable, new CronTrigger("30 14 14 * * *", TimeZone.getDefault())));// 2:08:30pm
		taskRegistrar.addFixedRateTask(new IntervalTask(runnable, interval, initialDelay)); // 5 sec initial delay as
																							// soon as app is up, then
																							// trigger job in 0/3secs.
		//2019-08-08 14:13:23.192  INFO 11128 --- [           main] com.example.demo.AsyncDemoApplication    : Started AsyncDemoApplication in 3.154 seconds (JVM running for 4.531)
		//2019-08-08 14:13:28.129  INFO 11128 --- [lTaskScheduler1] com.example.demo.scheduler.CustomTask    : Started executing custom task in thread ThreadPoolTaskScheduler1
	}

}
