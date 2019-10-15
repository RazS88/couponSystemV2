package com.jb.controller;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.controller.ex.InvalidListException;
import com.jb.controller.ex.SystemMalfunctionException;
import com.jb.entity.Coupon;
import com.jb.rest.ClientSession;
import com.jb.service.CompanyService;
import com.jb.service.ex.CouponIsNotExistsException;
import com.jb.service.ex.InvalidUpdateCouponException;

/*Help Spring to know that this is Controller Rest 
 * works with Client Side and Http */
@RestController
/* Name of Servlet */
@RequestMapping("/api")
public class CompanyController {

	// Fields

	/* Map with Key and Value For Create Session */
	private Map<String, ClientSession> tokenMap;

	/* Get the Token And work with */
	private String token;

	// Constructor initializing Fields
	/* Qualifier is for telling Spring that its connection to component with bean */
	public CompanyController(@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
		this.tokenMap = tokenMap;
	}

	/**
	 * Get Token get the right Service Get All Companies Coupons Find all Companies
	 * Coupons return Response entity with all Companies Coupons
	 */
	@GetMapping("/coupons/{token}")
	public ResponseEntity<Collection<Coupon>> getCompanyCoupon(@PathVariable String token) throws InvalidListException {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			CompanyService companyService = session.getCompanyService();
			Collection<Coupon> allCompanyCoupons = companyService.getAllCompanyCoupons();
			if (allCompanyCoupons != null) {
				return ResponseEntity.ok(allCompanyCoupons);
			}
		}
		throw new InvalidListException("System Mlfunction");

	}

	/**
	 * Make a Path Get Session Get from Path the Id Get the Coupon with ID get the
	 * right Service return Response entity with Get coupon
	 */
	@GetMapping("/coupon/{id}")
	public ResponseEntity<Coupon> getCoupon(@PathVariable long id) throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			CompanyService service = session.getCompanyService();
			Coupon couponById = service.getCouponById(id);

			if (couponById != null) {
				return ResponseEntity.ok(couponById);
			}
		}
		throw new SystemMalfunctionException("there is no Coupon");
	}

	/**
	 * Make a Path Get Session Send Json to Controller with all arguments of Coupon
	 * get the right Service return Response entity with coupon
	 */
	@PostMapping("/coupon")
	public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);

		if (session != null) {
			CompanyService service = session.getCompanyService();
			coupon.setId(0);
			Coupon newCoupon = service.createCoupon(coupon);
			if (newCoupon != null) {
				return ResponseEntity.ok(newCoupon);
			}

		}
		throw new SystemMalfunctionException("cant create coupon");

	}

	/**
	 * Make a Path Get Session Send Json to Controller with all arguments of Coupon
	 * to update get the right Service return Response entity with update Coupon
	 */
	@PutMapping("/coupons")
	public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon) throws InvalidUpdateCouponException {
		ClientSession session = getSession(this.token);
		Coupon updateCoupon;
		if (session != null) {
			CompanyService service = session.getCompanyService();
			updateCoupon = service.updateCoupon(coupon);
			if (updateCoupon != null) {
				return ResponseEntity.ok(updateCoupon);
			}
		}
		throw new InvalidUpdateCouponException("cant update coupon");
	}

	/**
	 * Make a Path Get Session Get from Path the Id get the right Service return
	 * Response entity with remove Coupon
	 */
	@DeleteMapping("/coupon/{id}")
	public ResponseEntity<Coupon> deleteCoupon(@PathVariable long id)
			throws CouponIsNotExistsException, SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			CompanyService service = session.getCompanyService();
			Coupon removeCoupon = service.getCouponById(id);
			System.out.println(removeCoupon.toString());
			if (removeCoupon != null) {
				service.removeCoupon(id);
				return ResponseEntity.ok(removeCoupon);
			}
		}
		throw new SystemMalfunctionException("cant remove coupon");
	}

	/**
	 * Make a Path Get Session Put optional date get the Date get the right Service
	 * return Response entity with all Coupons before Date
	 */
	@GetMapping("/coupons/date")
	public ResponseEntity<List<Coupon>> findCouponsBeforeEndDate(@RequestParam String date)
			throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		Date sqlDate = Date.valueOf(date);
		if (session != null) {
			CompanyService service = session.getCompanyService();
			List<Coupon> findAllCouponsBeforeDate = service.findCouponsBeforeEndDate(sqlDate);
			if (findAllCouponsBeforeDate != null) {
				return ResponseEntity.ok(findAllCouponsBeforeDate);
			}

		}
		throw new SystemMalfunctionException("there is no Coupons ");

	}

	/**
	 * Make a Path Get Session Put two optional date get the Dates get the right
	 * Service return Response entity with all Coupons between Dates
	 */
	@GetMapping("/coupons/dates")
	public ResponseEntity<List<Coupon>> findAllCouponsBetweenDate(@RequestParam String date01,
			@RequestParam String date02) throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		Date sqlDate01 = Date.valueOf(date01);
		Date sqlDate02 = Date.valueOf(date02);
		if (session != null) {
			CompanyService service = session.getCompanyService();
			List<Coupon> findAllCouponsBetweenDate = service.findAllCouponsBetweenDate(sqlDate01, sqlDate02);
			if (findAllCouponsBetweenDate != null) {
				return ResponseEntity.ok(findAllCouponsBetweenDate);
			}

		}
		throw new SystemMalfunctionException("there is no Coupons ");

	}

	/**
	 * Make a Path Get Session Get Category Get all Coupons By Category get the
	 * right Service return Response entity with all Coupons By Category
	 */
	@GetMapping("/coupons/category")
	public ResponseEntity<List<Coupon>> findAllCouponsByCategory(@RequestParam int category)
			throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			CompanyService service = session.getCompanyService();
			List<Coupon> findAllByCategory = service.findAllByCategory(category);
			if (findAllByCategory != null) {
				return ResponseEntity.ok(findAllByCategory);
			}

		}
		throw new SystemMalfunctionException("there is no Coupons ");

	}

	/**
	 * Make a Path Get Session Get Price Get all Coupons By Price get the right
	 * Service return Response entity with all Coupons By Price
	 */
	@GetMapping("/coupons/price")
	public ResponseEntity<List<Coupon>> findAllCouponsByPriceLessThan(@RequestParam double price)
			throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			CompanyService service = session.getCompanyService();
			List<Coupon> findAllByPriceLess = service.findAllByPriceLess(price);
			if (findAllByPriceLess != null) {
				return ResponseEntity.ok(findAllByPriceLess);
			}

		}
		throw new SystemMalfunctionException("there is no Coupons ");

	}

	// Get the Session
	private ClientSession getSession(String token) {
		return tokenMap.get(token);
	}

	// Set the Session
	public Map<String, ClientSession> getTokenMap() {
		return tokenMap;
	}

}
