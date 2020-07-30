package com.jaenyeong.springboot_started.redis;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash("accounts")
public class Account {

	@Id
	private String id;
	private String userName;
	private String email;

	public String getId() {
		return id;
	}

	public Account setId(String id) {
		this.id = id;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public Account setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Account setEmail(String email) {
		this.email = email;
		return this;
	}
}
