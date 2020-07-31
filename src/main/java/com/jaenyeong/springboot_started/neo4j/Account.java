package com.jaenyeong.springboot_started.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Account {
	@Id @GeneratedValue
	private Long id;
	private String userName;
	private String email;

	// Account가 role 다수를 가지고 있음
	@Relationship(type = "has")
	private Set<Role> roles = new HashSet<>();

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

	public String getEmail() {
		return email;
	}

	public Account setEmail(String email) {
		this.email = email;
		return this;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public Account setRoles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}
}
