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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.controller.ex.CouponIsAlreadyPurchased;
import com.jb.controller.ex.InvalidListException;
import com.jb.controller.ex.SystemMalfunctionException;
import com.jb.entity.Coupon;
import com.jb.entity.Customer;
import com.jb.entity.User;
import com.jb.rest.ClientSession;
import com.jb.service.CompanyService;
import com.jb.service.CustomerService;
import com.jb.service.ex.CouponIsNotExistsException;

/*Help Spring to know that this is Controller Rest 
 * works with Client Side and Http */
@RestController
/* Name of Servlet */
@RequestMapping("/api")
public class CustomerController {

	// Fields

	/* Map with Key and Value For Create Session */
	private Map<String, ClientSession> tokenMap;

	/* Get the Token And work with */
	private String token;

	// Constructor initializing Fields
	/* Qualifier is for telling Spring that its connection to component with bean */
	public CustomerController(@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
		this.tokenMap = tokenMap;
	}

	/**
	 * Get Token get the right Service Get All Customer Coupons Find all Customer
	 * Coupons return Response entity with all Customer Coupons
	 */
	@GetMapping("/couponsc/{token}")
	public ResponseEntity<Collection<Coupon>> getCustomerCoupons(@PathVariable String token)
			throws InvalidListException {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			CustomerService customerService = session.getCustomerService();
			Collection<Coupon> allCustomerCoupons = customerService.getAllCustomerCoupon();
			if (allCustomerCoupons != null) {
				return ResponseEntity.ok(allCustomerCoupons);
			}
		}
		throw new InvalidListException("System Mlfunction");

	}

	/**
	 * Get Token get the right Service Get All Coupons Find all Coupons return
	 * Response entity with all Coupons
	 */
	@GetMapping("/coupons")
	public ResponseEntity<Collection<Coupon>> getAllCoupons() throws InvalidListException {
		ClientSession session = getSession(this.token);

		if (session != null) {
			CustomerService service = session.getCustomerService();
			List<Coupon> findAllCoupons = service.findAll();
			if (findAllCoupons != null) {
				return ResponseEntity.ok(findAllCoupons);
			}
		}
		throw new InvalidListException("System Mlfunction");

	}

	/**
	 * Make a Path Get Session Get from Path the Id Get the Coupon with ID get the
	 * right Service return Response entity with Get coupon
	 */
	@GetMapping("/couponc/{id}")
	public ResponseEntity<Coupon> getCoupon(@PathVariable long id) throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			CustomerService service = session.getCustomerService();
			Coupon couponById = service.getCouponById(id);

			if (couponById != null) {
				return ResponseEntity.ok(couponById);
			}
		}
		throw new SystemMalfunctionException("there is no Coupon");
	}

	/**
	 * Make a Path Get Session Get from Path the Id get the right Service make an
	 * connection with coupon ID save them in Table return Response entity with
	 * purchase Coupon
	 */
	@DeleteMapping("/purchase/{id}")
	public ResponseEntity<Coupon> purchaseCoupon(@PathVariable long id)
			throws CouponIsNotExistsException, SystemMalfunctionException, CouponIsAlreadyPurchased {
		ClientSession session = getSession(this.token);
		if (session != null) {
			Customer customer;
			CustomerService service = session.getCustomerService();
			Coupon purchaseCoupon = service.getCouponById(id);
			User userById = service.getUserById();
			if (userById != null) {
				customer = (Customer) userById.getClient();

				if (purchaseCoupon != null && !customer.getCoupons().contains(purchaseCoupon)
						&& purchaseCoupon.getAmount() > 0) {
					service.purchase(purchaseCoupon, customer);
					return ResponseEntity.ok(purchaseCoupon);

				}

			}

			throw new CouponIsAlreadyPurchased("cant purchase this coupon!!!!!!");
		}
		throw new SystemMalfunctionException("cant purchase this coupon");
	}

	/**
	 * Make a Path Get Session Put optional date get the Date get the right Service
	 * return Response entity with all Coupons before Date
	 */
	@GetMapping("/couponsc/date")
	public ResponseEntity<List<Coupon>> findCustomersCouponsBeforeEndDate(@RequestParam String date)
			throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		Date sqlDate = Date.valueOf(date);
		if (session != null) {
			CustomerService service = session.getCustomerService();
			List<Coupon> findAllCouponsBeforeDate = service.findAllBeforeEndDate(sqlDate);
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
	@GetMapping("/couponsc/dates")
	public ResponseEntity<List<Coupon>> findBeforeEndDate(@RequestParam String date) throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		Date sqlDate = Date.valueOf(date);
		if (session != null) {
			CustomerService service = session.getCustomerService();
			List<Coupon> findAllCouponsBeforeDate = service.findAllBeforeEndDate(sqlDate);
			if (findAllCouponsBeforeDate != null) {
				return ResponseEntity.ok(findAllCouponsBeforeDate);
			}

		}
		throw new SystemMalfunctionException("there is no Coupons ");

	}

	/**
	 * Make a Path Get Session Get Category Get all Coupons By Category get the
	 * right Service return Response entity with all Coupons By Category
	 */
	@GetMapping("/couponsc/category")
	public ResponseEntity<List<Coupon>> findAllCouponsByCategory(@RequestParam int category)
			throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			CustomerService service = session.getCustomerService();
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
	@GetMapping("/couponsc/price")
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
