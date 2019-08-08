package com.acg.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(CustomTask.class);

	@Override
	public void run() {
		logger.info("Started executing custom task in thread {}", Thread.currentThread().getName());
	}

}
