package com.jb.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jb.controller.AdminController;
import com.jb.controller.CompanyController;
import com.jb.controller.CustomerController;
import com.jb.controller.LoginController;
import com.jb.controller.UserErrorResponse;
import com.jb.controller.ex.CouponIsAlreadyPurchased;
import com.jb.controller.ex.InvalidListException;
import com.jb.controller.ex.SystemMalfunctionException;
import com.jb.controller.ex.UserMalformExeption;
import com.jb.rest.ex.InvalidLoginExeption;
import com.jb.service.ex.CouponIsNotExistsException;
import com.jb.service.ex.InvalidUpdateCouponException;
import com.jb.service.ex.UserNotExisisteException;



@ControllerAdvice(assignableTypes = {LoginController.class,CompanyController.class,CustomerController.class,AdminController.class})
public class UserRestExcptionHandler {
	
	@ExceptionHandler(InvalidLoginExeption.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidLoginExeption ex) {
		return UserErrorResponse.now(HttpStatus.UNAUTHORIZED,String.format("unauthroized : %s", ex.getMessage()));
	}
	
	@ExceptionHandler(UserMalformExeption.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleTaskMalform(UserMalformExeption ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("task malformed : %s", ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidListException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidListException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("bad request : %s", ex.getMessage()));
	}
	
	@ExceptionHandler(SystemMalfunctionException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(SystemMalfunctionException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("bad request: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidUpdateCouponException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(InvalidUpdateCouponException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("bad request: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(CouponIsNotExistsException.class)
	@ResponseStatus(value = HttpStatus.LOCKED)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(CouponIsNotExistsException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("coupon is not exists: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(CouponIsAlreadyPurchased.class)
	@ResponseStatus(value = HttpStatus.LOCKED)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(CouponIsAlreadyPurchased ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("coupon is already purchased: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(UserNotExisisteException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public UserErrorResponse handleUnauthrized(UserNotExisisteException ex) {
		return UserErrorResponse.now(HttpStatus.BAD_REQUEST,String.format("bad request: %s", ex.getMessage()));
	}
	

}
