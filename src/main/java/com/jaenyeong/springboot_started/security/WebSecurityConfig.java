package com.jaenyeong.springboot_started.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// 자동 설정 사용하지 않고 모방하여 직접 설정
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/security/hello", "/rest/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.and()
				.httpBasic();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 비밀번호 인코딩을 처리 하지 않기 때문에 운영에 절대 사용하지 말 것
//		return NoOpPasswordEncoder.getInstance();

		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
