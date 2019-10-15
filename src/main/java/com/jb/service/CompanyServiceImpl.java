package com.jb.service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.CouponRepository;
import com.jb.dao.UserRepository;
import com.jb.entity.Company;
import com.jb.entity.Coupon;
import com.jb.entity.User;
import com.jb.service.ex.CouponIsNotExistsException;
import com.jb.service.ex.InvalidUpdateCouponException;

/*Help Spring to know that this is service and give all the c r u d e
 * works with Jpa and Controller */
@Service
/* Scope for not being singleton */
@Scope("prototype")
public class CompanyServiceImpl implements CompanyService {

	// Fields

	/* ID Field for set User*/
	
	private long userId;

	/* All Repo Fields Use */
	
	private UserRepository userDao;

	private CouponRepository couponDao;

	//Make a DI
	@Autowired
	// Constructor initializing RepoFields
	public CompanyServiceImpl(CouponRepository couponDao, UserRepository userDao) {
		this.couponDao = couponDao;
		this.userDao = userDao;
	}


	/**
	 * Create Coupon By Company To DB
	 * Set the Current Company 
	 * Save to DB 
	 */
	public Coupon createCoupon(Coupon coupon) {
		Optional<User> userOpt = userDao.findById(userId);
		if (userOpt.isPresent()) {
			Company company = (Company) userOpt.get().getClient();
			company.add(coupon);
			return couponDao.save(coupon);
		}
		return null;
	}


	/**
	 * Get an ID Of Company 
	 * Find All Coupons of Current Company
	 */
	public Collection<Coupon> findAllCompanyCoupon(long companyId) {
		return couponDao.findAllByCompanyId(companyId);
	}

	/**
	 * Update Coupon To DB
	 * Set the Current Company 
	 * Save to DB 
	 */
	@Override
	public Coupon updateCoupon(Coupon coupon) throws InvalidUpdateCouponException {
      Optional<User> userOpt = userDao.findById(userId);
      Optional<Coupon> couponOpt = couponDao.findById(coupon.getId());
		
		if (couponOpt.isPresent()) {
			Coupon currentcoupon = couponOpt.get();
             if (currentcoupon.getTitle().equals(coupon.getTitle())&&coupon.getAmount()>=0) {
            	 coupon.setCompany((Company) userOpt.get().getClient());
 				return	 couponDao.save(coupon);
			}					
		}
		throw new InvalidUpdateCouponException("cant update the couopn");

	}
	
	

	/**
	 * Create Coupon By Company To DB
	 * Set the Current Company 
	 * Save to DB 
	 */
	@Override
	public void removeCoupon(long id) throws CouponIsNotExistsException {
	      Optional<Coupon> couponOpt= couponDao.findById(id);

	      if (couponOpt.isPresent()) {
	    	 couponDao.deleteById(id);
	      System.out.println("coupon remove!!!!");
	    	}else {
	    	  throw new CouponIsNotExistsException("Coupon with this id is not exists");
	}}


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
	public List<Coupon> getAllCompanyCoupons() {
		return couponDao.findAllByCompanyId(userId);
	}
	
	
	/**
	 * Set the Current ID Of Company
	 */
	public void setCompanyId(long id){
		 this.userId = id;
		 
	 }


	/**
	 * Get two Dates
	 * Find between Dates All Coupons   
	 */
	@Override
	public List<Coupon> findAllCouponsBetweenDate(Date date01, Date date02) {
		return couponDao.findAllByEndDateBetween(date01, date02);
	}
	
	/**
	 * Get Date
	 * Find all Coupons before Date
	 */
	@Override
	public List<Coupon> findCouponsBeforeEndDate(Date date) {
		
		return couponDao.findAllBeforeEndDate(date, this.userId);
	}

	/**
	 * Get Category
	 * Find all Coupons by given Category
	 */
	@Override
	public List<Coupon> findAllByCategory(int category) {
	return couponDao.findAllByCompanyIdAndCategory(this.userId,category);
	}

	/**
	 * Get Price
	 * Find all Coupons by less then given Price
	 */
	@Override
	public List<Coupon> findAllByPriceLess(double price) {
	return couponDao.findAllByCompanyIdAndPriceLessThan(this.userId,price);
	}

	/**
	 * Find all Expired Coupons in DB
	 */
	@Override
	public List<Coupon> findExpiredCoupon() {
	return couponDao.findAllBeforeEndDate(new Date(System.currentTimeMillis()), this.userId);
	}








}
