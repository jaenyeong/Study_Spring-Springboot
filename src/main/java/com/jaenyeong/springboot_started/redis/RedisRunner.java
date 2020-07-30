package com.jaenyeong.springboot_started.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisRunner implements ApplicationRunner {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	AccountRepository accountRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set("jaenyeong", "noah");
		values.set("spring_boot", "2.3.1");
		values.set("Hello", "world");

		Account account = new Account();
		account.setEmail("jaenyeong.dev@gmail.com");
		account.setUserName("jaenyeong");

		accountRepository.save(account);
		Optional<Account> byId = accountRepository.findById(account.getId());
		System.out.println("Redis runner User Name : " + byId.get().getUserName());
		System.out.println("Redis runner Email : " + byId.get().getEmail());
	}
}
