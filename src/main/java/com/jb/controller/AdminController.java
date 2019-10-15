package com.jb.controller;

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
import com.jb.entity.Company;
import com.jb.entity.Customer;
import com.jb.entity.User;
import com.jb.rest.ClientSession;
import com.jb.service.AdminService;
import com.jb.service.ex.UserNotExisisteException;

/*Help Spring to know that this is Controller Rest 
 * works with Client Side and Http */
@RestController
/* Name of Servlet */
@RequestMapping("/api")
public class AdminController {

	// Fields

	/* Map with Key and Value For Create Session */
	private Map<String, ClientSession> tokenMap;
	/* Get the Token And work with */
	private String token;

	// Constructor initializing Fields
	/* Qualifier is for telling Spring that its connection to component with bean */
	public AdminController(@Qualifier("tokens") Map<String, ClientSession> tokenMap) {
		this.tokenMap = tokenMap;
	}

	/**
	 * Make a Path Get Session Send Json to Controller with all arguments of User
	 * get the right Service return Response entity with user
	 */
	@PostMapping("/customer/params")
	public ResponseEntity<User> createUserCustomer(@RequestParam String email, @RequestParam String password,
			@RequestParam String name) throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);

		if (session != null) {
			AdminService service = session.getAdminService();
			User customer = service.createUserCustomer(email, password, name);
			if (customer != null) {
				return ResponseEntity.ok(customer);
			}

		}
		throw new SystemMalfunctionException("cant create customer");

	}

	/**
	 * Make a Path Get Session Send Json to Controller with all arguments of User
	 * get the right Service return Response entity with user
	 */

	@PostMapping("/company")
	public ResponseEntity<User> createUserCompany(String email, String password, String name)
			throws SystemMalfunctionException {
		ClientSession session = getSession(this.token);

		if (session != null) {
			AdminService service = session.getAdminService();
			User company = service.createUserCompany(email, password, name);
			if (company != null) {
				return ResponseEntity.ok(company);
			}

		}
		throw new SystemMalfunctionException("cant create company");

	}

	/**
	 * Make a Path Get Session Send Json to Controller with all arguments of User to
	 * update get the right Service return Response entity with update user
	 */
	@PutMapping("/user")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws UserNotExisisteException {
		ClientSession session = getSession(this.token);

		if (session != null) {
			AdminService service = session.getAdminService();
			User userById = service.getUserById(user.getId());
			if (userById != null) {
				user.setClient(userById.getClient());
				User updateUser = service.updateUser(user);
				return ResponseEntity.ok(updateUser);
			}
		}
		throw new UserNotExisisteException("cant update coupon");
	}

	/**
	 * Make a Path Get Session Get from Path the Id get the right Service return
	 * Response entity with remove user
	 */
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable long id)
			throws SystemMalfunctionException, UserNotExisisteException {
		ClientSession session = getSession(this.token);
		if (session != null) {
			AdminService service = session.getAdminService();
			User removeById = service.getUserById(id);
			if (removeById != null) {
				service.removeUser(id);
				return ResponseEntity.ok(removeById);
			}
		}
		throw new SystemMalfunctionException("cant remove user");
	}

	/**
	 * Get Token Get All Customers Find all Customers return Response entity with
	 * all Customers
	 */
	@GetMapping("/customers/{token}")
	public ResponseEntity<Collection<Customer>> getAllCustomers(@PathVariable String token)
			throws InvalidListException {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			AdminService service = session.getAdminService();
			List<Customer> allCustomers = service.getAllCustomers();
			if (allCustomers != null) {
				return ResponseEntity.ok(allCustomers);
			}
		}
		throw new InvalidListException("System Mlfunction");

	}

	/**
	 * Get Token Get All Companies Find all Companies return Response entity with
	 * all Companies
	 */
	@GetMapping("/companies/{token}")
	public ResponseEntity<Collection<Company>> getAllCompanies(@PathVariable String token) throws InvalidListException {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			AdminService service = session.getAdminService();
			List<Company> allCompanies = service.getAllCompanies();
			if (allCompanies != null) {
				return ResponseEntity.ok(allCompanies);
			}
		}
		throw new InvalidListException("System Mlfunction");

	}

	/**
	 * Get Token Get All Users Find all Users return Response entity with all Users
	 */
	/* for future work with User entity if it will be update */
	@GetMapping("/users/{token}")
	public ResponseEntity<Collection<User>> getAllUsers(@PathVariable String token) throws InvalidListException {
		ClientSession session = getSession(token);
		this.token = token;
		if (session != null) {
			AdminService service = session.getAdminService();
			List<User> findAllUsers = service.findAllUsers();
			if (findAllUsers != null) {
				return ResponseEntity.ok(findAllUsers);
			}
		}
		throw new InvalidListException("System Mlfunction");

	}

	/**
	 * Make a Path Get Session Get from Path the Id get the right Service return
	 * Response entity with get user with ID
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) throws InvalidListException {
		ClientSession session = getSession(token);

		if (session != null) {
			AdminService service = session.getAdminService();
			User userById = service.getUserById(id);
			if (userById != null) {
				return ResponseEntity.ok(userById);
			}
		}
		throw new InvalidListException("System Mlfunction");

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
