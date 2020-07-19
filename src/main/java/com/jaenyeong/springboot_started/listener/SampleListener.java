package com.jaenyeong.springboot_started.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

// 등록되어 있는 빈중에 해당하는 이벤트에 리스너를 알아서 실행해줌
// 관건은 이벤트가 발생하는 시점
// applicationContext가 생성된 후 발생하는 이벤트에 대한 리스너는 실행됨
// applicationContext가 생성되기 이전에 발생된 이벤트의 리스너는 빈으로 등록해도 실행되지 않음
// 빈으로 등록하는 것의 의미 없음
//@Component
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {

	@Override
	public void onApplicationEvent(ApplicationStartingEvent event) {
		System.out.println("===============");
		System.out.println("App is Starting");
		System.out.println("===============");
	}
}
