package com.jaenyeong.springboot_started.exception;

public class SampleError {
	private String message;
	private String reason;

	public String getMessage() {
		return message;
	}

	public SampleError setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getReason() {
		return reason;
	}

	public SampleError setReason(String reason) {
		this.reason = reason;
		return this;
	}
}
