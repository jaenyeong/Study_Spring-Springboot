package com.jaenyeong.springboot_started.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// ApplicationContext가 생성된 이후에 실행되는 이벤트의 리스너는 빈으로 등록하여 실행
@Component
public class Sample2Listener implements ApplicationListener<ApplicationStartedEvent> {

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		System.out.println("===============");
		System.out.println("App is Started");
		System.out.println("===============");
	}
}
