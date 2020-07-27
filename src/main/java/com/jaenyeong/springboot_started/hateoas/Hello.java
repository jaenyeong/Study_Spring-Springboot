package com.jaenyeong.springboot_started.hateoas;

public class Hello {
	private String prefix;
	private String name;

	public String getPrefix() {
		return prefix;
	}

	public Hello setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public String getName() {
		return name;
	}

	public Hello setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "Hello{" +
				"prefix='" + prefix + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
