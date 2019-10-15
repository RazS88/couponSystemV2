package com.jb.service.ex;

@SuppressWarnings("serial")
public class CouponIsNotExistsException extends Exception {
	public CouponIsNotExistsException(String msg) {
		super( msg);
	}

}
