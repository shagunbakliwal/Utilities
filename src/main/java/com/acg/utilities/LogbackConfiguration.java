package com.acg.utilities;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;

@Configuration
public class LogbackConfiguration {
	@Bean
	public LoggerContext doSomething() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext);
		encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%15thread] %15logger : %msg%n");
		encoder.start();
		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setContext(loggerContext);
		fileAppender.setName("timestamp");
		fileAppender.setFile("log/" + System.currentTimeMillis() + ".log");
		fileAppender.setEncoder(encoder);
		fileAppender.start();
		// attach the rolling file appender to the logger of your choice
		Logger logbackLogger = loggerContext.getLogger("timestamp1timestamp1timestamp1timestamp1");
		logbackLogger.addAppender(fileAppender);
		// OPTIONAL: print logback internal status messages
		StatusPrinter.print(loggerContext);

		// log something
		logbackLogger.info("hello");
		//2019-08-08 17:34:42.920 INFO --- [           main] timestamp1timestamp1timestamp1timestamp1 : hello
		return loggerContext;
	}
}
