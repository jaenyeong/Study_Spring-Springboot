package com.jaenyeong.springboot_started.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;

//import javax.validation.constraints.NotEmpty;

//import org.springframework.boot.convert.DurationUnit;

// 아래 프로퍼티를 사용하기 위해 빈 등록
@Component
// @ConfigurationProperties("jaenyeong") 어노테이션까지 태깅시 값을 바인딩 받을 수 있는 상태
// 바로 값을 가져다 쓸수는 없는 상태
@ConfigurationProperties("jaenyeong")
@Validated
public class JaenyeongProperties {
	private String name;
	private int age;
	private String fullName;

	// @DurationUnit 어노테이션을 사용하지 않아도 프로퍼티 값 suffix가 s라면 바인딩 됨
//	@DurationUnit(ChronoUnit.SECONDS)
	private Duration sessionTimeout = Duration.ofSeconds(30);

	// 빈값 확인
	@NotEmpty
	private String nullParam;

	public String getName() {
		return name;
	}

	public JaenyeongProperties setName(String name) {
		this.name = name;
		return this;
	}

	public int getAge() {
		return age;
	}

	public JaenyeongProperties setAge(int age) {
		this.age = age;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public JaenyeongProperties setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public Duration getSessionTimeout() {
		return sessionTimeout;
	}

	public JaenyeongProperties setSessionTimeout(Duration sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
		return this;
	}

	public String getNullParam() {
		return nullParam;
	}

	public JaenyeongProperties setNullParam(String nullParam) {
		this.nullParam = nullParam;
		return this;
	}
}
