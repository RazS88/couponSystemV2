package com.jb.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.rest.ClientSession;
import com.jb.rest.UserSystem;
import com.jb.rest.ex.InvalidLoginExeption;

/*Help Spring to know that this is Controller Rest 
 * works with Client Side and Http */
@RestController
@RequestMapping("/api")
public class LoginController {
	// Fields

	/* Get the Type Of User */
	private UserSystem userSystem;
	
	/* Map with Key and Value For Create Session */
	private Map<String, ClientSession> tokensMap;
	
	/* Max connection */
	private static final int LENGTH_TOKEN = 15;

	// Constructor initializing Fields
	/* Qualifier is for telling Spring that its connection to component with bean */
	public LoginController(UserSystem userSystem, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
		this.userSystem = userSystem;
		this.tokensMap = tokensMap;
	}

	
	/**
	 * Login with Email and Password
	 * Get Client and token
	 * return UUID in template for User Safety :) 
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password)
			throws InvalidLoginExeption {

		ClientSession session;

		session = userSystem.login(email, password);
		String token = generateToken();

		tokensMap.put(token, session);
		System.out.println(token);
		return ResponseEntity.ok(token);

	}

	private String generateToken() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, LENGTH_TOKEN);
	}

}
