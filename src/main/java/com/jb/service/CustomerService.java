package com.jb.service;

import java.sql.Date;
import java.util.List;

import com.jb.entity.Coupon;
import com.jb.entity.Customer;
import com.jb.entity.User;

public interface CustomerService {

	/* Get Optional User From DB By ID */
	Coupon getCouponById(long id);

	/* List of all Coupons in DB */
	List<Coupon> findAll();

	/* List of all Customer Coupons in DB */
	List<Coupon> getAllCustomerCoupon();

	/* Function to add " purchase" coupon for Customer */
	Customer purchase(Coupon coupon, Customer customer);

	/* List of All Coupons in DB before given Date */
	List<Coupon> findAllBeforeEndDate(Date date);

	/* List of all coupon in DB by given Category */
	List<Coupon> findAllByCategory(int category);

	/* List of all coupon in DB by given price */
	List<Coupon> findAllByPriceLess(double price);

	/* Set the specific Customer */
	void setCustomerId(long userId);

	/* List of all Customer Coupons by given Id */
	List<Coupon> findAllByCustomersId(long userId);

	/* List of All Customers coupons by given Category and ID of Customer */
	List<Coupon> findAllByCustomersAndCategory(long id, int category);

	/* List of All Customers coupons Price Less by given Price and ID of Customer */
	List<Coupon> findAllByCustomersAndPriceLessThan(long id, double price);

	/* List of Expired Coupon */
	List<Coupon> findExpiredCoupon();

	/* Get this user from DB */
	User getUserById();

	/* List of All Customers coupons Before Date by given Date */
	List<Coupon> findAllCustomersCouponsBeforeEndDate(Date date);

}
