package com.jaenyeong.springboot_started.auto_configure;

public class HolomanBack {
	String name;
	int howLong;

	public String getName() {
		return name;
	}

	public HolomanBack setName(String name) {
		this.name = name;
		return this;
	}

	public int getHowLong() {
		return howLong;
	}

	public HolomanBack setHowLong(int howLong) {
		this.howLong = howLong;
		return this;
	}

	@Override
	public String toString() {
		return "Holoman{" +
				"name='" + name + '\'' +
				", howLong=" + howLong +
				'}';
	}
}
