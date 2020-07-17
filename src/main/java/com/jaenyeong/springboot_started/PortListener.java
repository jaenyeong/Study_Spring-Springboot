package com.jaenyeong.springboot_started;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PortListener implements ApplicationListener<ServletWebServerInitializedEvent> {

	// 웹서버 초기화가 되면 호출됨
	@Override
	public void onApplicationEvent(ServletWebServerInitializedEvent event) {
		ServletWebServerApplicationContext applicationContext = event.getApplicationContext();
		System.out.println(applicationContext.getWebServer().getPort());
	}
}
