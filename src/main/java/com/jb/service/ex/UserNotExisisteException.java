package com.jb.service.ex;

@SuppressWarnings("serial")
public class UserNotExisisteException extends Exception {

	public UserNotExisisteException(String msg) {
	super(msg);
	}
}
