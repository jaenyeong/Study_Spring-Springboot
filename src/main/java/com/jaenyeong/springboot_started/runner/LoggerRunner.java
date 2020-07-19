package com.jaenyeong.springboot_started.runner;

import com.jaenyeong.springboot_started.properties.JaenyeongProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LoggerRunner implements ApplicationRunner {

	// SLF4j 사용
	private final Logger logger = LoggerFactory.getLogger(LoggerRunner.class);

	@Autowired
	private String hello;

	@Autowired
	private JaenyeongProperties jaenyeongProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("[LoggerRunner]----------------------------------------------------------");
		logger.debug("hello : " + hello);
		logger.debug("jaenyeongProperties Name : " + jaenyeongProperties.getName());
		logger.debug("jaenyeongProperties FullName : " + jaenyeongProperties.getFullName());
		logger.debug("jaenyeongProperties proddb : " + jaenyeongProperties.getProddb());
		logger.debug("[LoggerRunner]----------------------------------------------------------");
	}
}
