package com.jaenyeong.springboot_started.springboot_jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Account {
	@Id @GeneratedValue
	private Long id;
	private String userName;
	private String password;
	private String email;
	private boolean active;

	public Long getId() {
		return id;
	}

	public Account setId(Long id) {
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

	public String getPassword() {
		return password;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Account setEmail(String email) {
		this.email = email;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public Account setActive(boolean active) {
		this.active = active;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return isActive() == account.isActive() &&
				Objects.equals(getId(), account.getId()) &&
				Objects.equals(getUserName(), account.getUserName()) &&
				Objects.equals(getPassword(), account.getPassword()) &&
				Objects.equals(getEmail(), account.getEmail());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUserName(), getPassword(), getEmail(), isActive());
	}
}
