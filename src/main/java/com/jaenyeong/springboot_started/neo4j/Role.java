package com.jaenyeong.springboot_started.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Role {
	@Id @GeneratedValue
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public Role setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}
}
