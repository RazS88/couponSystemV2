package com.jb.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jb.entity.Coupon;

/*Helps spring to know that is a repository for the Entity(REST) Works with DB*/
@Repository
/*
 * By Implements the interface JpaRepo we get all C . R . U . D Function By give
 * to Jpa Class And ID (type)
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	/* coupons */
	/*
	 * Query by Field End Date to get All Coupons Before given Date for A specific
	 * User with Id
	 */
	@Query("FROM Coupon as c WHERE c.endDate <= :date and userId=:userId")
	List<Coupon> findAllBeforeEndDate(@Param(value = "date") Date date, @Param(value = "userId") long userId);

	/* Query by Field End Date to get All Coupons Before given Date for this User */
	@Query("FROM Coupon as c WHERE c.endDate <= :date ")
	List<Coupon> findAllBeforeEndDate(@Param(value = "date") Date date);

	/* List of All Coupon in DB */
	List<Coupon> findAll();

	// Fillers
	/* List of All Coupons by given Category */
	List<Coupon> findAllByCategory(int category);

	// Fillers
	/* List of All Coupons by given Category */
	List<Coupon> findAllByPriceLessThan(double price);

	/* Company */
	/* List of User By given Id */
	List<Coupon> findAllByCompanyId(long id);

	// Fillers
	/* List of coupons between tow given dates */
	List<Coupon> findAllByEndDateBetween(Date date01, Date date02);

	// Fillers
	/* List of All user Coupons by given Category and ID */
	List<Coupon> findAllByCompanyIdAndCategory(long id, int category);

	// Fillers
	/* List of All user Coupons by given Price and ID */
	List<Coupon> findAllByCompanyIdAndPriceLessThan(long id, double price);

	/* Customer */

	/* List of User By given Id */
	List<Coupon> findAllByCustomersId(long userId);

	// Fillers
	/* List of All user Coupons by given Category and ID */
	List<Coupon> findAllByCustomersAndCategory(long id, int category);

	// Fillers
	/* List of All user Coupons by given Price and ID */
	List<Coupon> findAllByCustomersAndPriceLessThan(long id, double price);

}
