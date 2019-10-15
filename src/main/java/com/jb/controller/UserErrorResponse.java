package com.jb.controller;

import org.springframework.http.HttpStatus;

/*Class for handle Error And return only What we want*/
public class UserErrorResponse {
	// Fields
	private HttpStatus status;
	private String message;
	private long timestamp;

	// Constructor initializing Fields
	public UserErrorResponse(HttpStatus status, String message, long timestamp) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	/*
	 * Static Function that run all the time and if there is exception the exception
	 * will be on this Template
	 */
	public static UserErrorResponse now(HttpStatus status, String message) {
		return new UserErrorResponse(status, message, System.currentTimeMillis());
	}

	// Getters and setters
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
