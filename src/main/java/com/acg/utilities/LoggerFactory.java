package com.acg.utilities;
import org.springframework.util.StringUtils;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.OutputStreamAppender;

public class LoggerFactory {

	private static final String defaultExtension = "log";

	public static PatternLayoutEncoder patternLayoutEncoder(LoggerContext loggerContext) {
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext);
		encoder.setPattern("%date{yyyy-MM-dd HH:mm:ss.SSS} %-5level --- [%20thread] %20logger : %msg%n");
		encoder.start();
		return encoder;
	}

	public static OutputStreamAppender<ILoggingEvent> fileAppender(LoggerContext loggerContext, String logPath,
			String fileName, String extension) {
		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setContext(loggerContext);
		fileAppender.setAppend(true);
		fileAppender.setName("customFileAppender");
		if (!StringUtils.isEmpty(logPath) && !logPath.equals("."))
			fileAppender.setFile(logPath + "/" + fileName + "." + extension);
		else
			fileAppender.setFile(fileName + "." + extension);
		fileAppender.setEncoder(patternLayoutEncoder(loggerContext));
		fileAppender.start();
		return fileAppender;
	}

	public static Logger getLogger(Class<?> clazz, String logPath, String fileName, String extension) {
		LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
		Logger logger = loggerContext.getLogger(clazz.getName());
		logger.addAppender(fileAppender(loggerContext, logPath, fileName, extension));
		loggerContext.start();
		return logger;
	}

	public static Logger getLogger(Class<?> clazz) {
		LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
		Logger logger = loggerContext.getLogger(clazz.getName());
		logger.addAppender(fileAppender(loggerContext, ".", clazz.getSimpleName(), defaultExtension));
		loggerContext.start();
		return logger;
	}

}
