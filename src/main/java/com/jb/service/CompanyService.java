package com.jb.service;

import java.sql.Date;
import java.util.List;

import com.jb.entity.Coupon;
import com.jb.service.ex.CouponIsNotExistsException;
import com.jb.service.ex.InvalidUpdateCouponException;

public interface CompanyService {

	Coupon updateCoupon(Coupon coupon) throws InvalidUpdateCouponException;

	void removeCoupon(long id) throws CouponIsNotExistsException;

	/* Get Optional Coupon From DB By ID */
	Coupon getCouponById(long id);

	/* List of all Company Coupons in DB */
	List<Coupon> getAllCompanyCoupons();

	/* create Coupon in DB */
	Coupon createCoupon(Coupon coupon);

	/* Set the specific Company By given ID */
	void setCompanyId(long id);

	/* List of all Company Coupons between Two given Dates */
	List<Coupon> findAllCouponsBetweenDate(Date date01, Date date02);

	/* List of all Company Coupons Before given Date */
	List<Coupon> findCouponsBeforeEndDate(Date date);

	/* List of all Company Coupons By Category */
	List<Coupon> findAllByCategory(int category);

	/* List of all Company Coupons by Price Less from given Price */
	List<Coupon> findAllByPriceLess(double price);

	/* List of all Company Expired coupons */
	List<Coupon> findExpiredCoupon();

}
