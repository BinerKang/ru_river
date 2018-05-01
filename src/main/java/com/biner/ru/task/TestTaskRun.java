package com.biner.ru.task;

import org.apache.log4j.Logger;

public class TestTaskRun {

	private final Logger logger = Logger.getLogger(getClass()); 
	
	public void doSomething () {
		System.out.println(System.currentTimeMillis());
		logger.info("Test task is running...");
	}
}
