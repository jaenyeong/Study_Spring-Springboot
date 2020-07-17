package com.jaenyeong.springboot_started.auto_configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableConfigurationProperties(HolomanProperties.class)
public class HolomanConfigurationBack {

//	@Bean
//	// 빈이 등록되지 않은 경우 등록
//	@ConditionalOnMissingBean
//	// HolomanProperties 설정을 주입 받아 설정된 데이터 삽입
//	public HolomanBack holoman(HolomanProperties properties) {
//		HolomanBack holoman = new HolomanBack();
////		holoman.setHowLong(5);
////		holoman.setName("Jaenyeong");
//		holoman.setHowLong(properties.getHowLong());
//		holoman.setName(properties.getName());
//		return holoman;
//	}
}
