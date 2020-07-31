package com.jaenyeong.springboot_started.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SecurityAccount {
	@Id @GeneratedValue
	private Long id;
	private String userName;
	private String password;

	public Long getId() {
		return id;
	}

	public SecurityAccount setId(Long id) {
		this.id = id;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public SecurityAccount setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SecurityAccount setPassword(String password) {
		this.password = password;
		return this;
	}
}
