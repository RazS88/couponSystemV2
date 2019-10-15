package com.jb.service;

import java.util.List;

import com.jb.entity.Company;
import com.jb.entity.Customer;
import com.jb.entity.User;
import com.jb.service.ex.UserNotExisisteException;

public interface AdminService {
	/* for future work with User entity if it will be update */

	/* List of all Users in DB */
	List<User> findAllUsers();

	/* Create Customer User */
	User createUserCustomer(String email, String password, String name);

	/* Create Company User */
	User createUserCompany(String email, String password, String name);

	/* Update any User */
	User updateUser(User user);

	/* Get Optional User From DB By ID */
	User getUserById(long id);

	/* Remove Use from DB By ID */
	void removeUser(long id) throws UserNotExisisteException;

	/* List of all Customers in DB */
	List<Customer> getAllCustomers();

	/* List of all Companies in DB */
	List<Company> getAllCompanies();

}
