package com.jb.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.CouponRepository;
import com.jb.dao.CustomerRepository;
import com.jb.dao.UserRepository;
import com.jb.entity.Coupon;
import com.jb.entity.Customer;
import com.jb.entity.User;

/*Help Spring to know that this is service and give all the c r u d e
 * works with Jpa and Controller */
@Service
/* Scope for not being singleton */
@Scope("prototype")
public class CustomerServiceImpl implements CustomerService {

	// Fields

	/* ID Field for set User */

	private long userId;

	/* All Repo Fields Use */

	private UserRepository userDao;

	private CustomerRepository customerDao;

	private CouponRepository couponDao;

	// Make a DI
	@Autowired
	// Constructor initializing RepoFields
	public CustomerServiceImpl(UserRepository userDao, CouponRepository couponDao, CustomerRepository customerDao) {
		this.userDao = userDao;
		this.couponDao = couponDao;
		this.customerDao = customerDao;
	}

	/**
	 * Get an Coupon by Id Of Company From DB
	 */
	@Override
	public Coupon getCouponById(long id) {
		return couponDao.findById(id).orElse(null);
	}

	/**
	 * Get All Current Coupons Of Company
	 */
	@Override
	public List<Coupon> getAllCustomerCoupon() {
		return couponDao.findAllByCustomersId(this.userId);
	}

	/**
	 * Get Coupon and Customer Make an inner join Table for Coupon and Customer in
	 * DB Save the Coupon and save the Customer
	 */
	@Override
	public Customer purchase(Coupon coupon, Customer customer) {
		customer.addCoupon(coupon);
		couponDao.save(coupon);
		return customerDao.save(customer);

	}

	/**
	 * Get Date Find all Coupons before Date
	 */
	@Override
	public List<Coupon> findAllBeforeEndDate(Date date) {
		return couponDao.findAllBeforeEndDate(date);
	}

	/**
	 * Get Category Find all Coupons by given Category
	 */
	@Override
	public List<Coupon> findAllByCategory(int category) {
		return couponDao.findAllByCategory(category);
	}

	/**
	 * Get Price Find all Coupons by less then given Price
	 */
	@Override
	public List<Coupon> findAllByPriceLess(double price) {
		return couponDao.findAllByPriceLessThan(price);
	}

	/**
	 * Set the Current ID Of Customer
	 */
	public void setCustomerId(long userId) {
		this.userId = userId;
	}

	/**
	 * Find all Expired Coupons in DB
	 */
	@Override
	public List<Coupon> findExpiredCoupon() {
		return couponDao.findAllBeforeEndDate(new Date(System.currentTimeMillis()), this.userId);

	}

	/**
	 * Get an Optional User by Id Of User From DB
	 */
	@Override
	public User getUserById() {
		return userDao.findById(this.userId).orElse(null);
	}

	/**
	 * Get all Coupon in DB
	 */
	@Override
	public List<Coupon> findAll() {
		return couponDao.findAll();
	}

	/**
	 * Get All Current Coupons Of Customer
	 */
	@Override
	public List<Coupon> findAllByCustomersId(long userId) {

		return couponDao.findAllByCustomersId(this.userId);
	}

	/**
	 * Get Category and customer ID Find all Customer Coupons by given Category
	 */
	@Override
	public List<Coupon> findAllByCustomersAndCategory(long id, int category) {
		return couponDao.findAllByCustomersAndCategory(this.userId, category);
	}

	/**
	 * Get Price and customer ID Find all Customer Coupons by less then given Price
	 */
	@Override
	public List<Coupon> findAllByCustomersAndPriceLessThan(long id, double price) {
		return couponDao.findAllByCustomersAndPriceLessThan(this.userId, price);
	}

	/**
	 * Get Date and customer ID Find all Customer Coupons before Date
	 */
	@Override
	public List<Coupon> findAllCustomersCouponsBeforeEndDate(Date date) {
		return couponDao.findAllBeforeEndDate(date, this.userId);
	}

}
