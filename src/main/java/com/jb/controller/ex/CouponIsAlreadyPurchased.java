package com.jb.controller.ex;

@SuppressWarnings("serial")
public class CouponIsAlreadyPurchased extends Exception {
	public CouponIsAlreadyPurchased(String msg) {
			super(msg);
	}

}
