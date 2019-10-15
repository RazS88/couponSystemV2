package com.jb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.entity.Client;
import com.jb.entity.Company;
import com.jb.entity.Customer;
import com.jb.entity.User;
import com.jb.rest.ex.InvalidLoginExeption;
import com.jb.service.AdminService;
import com.jb.service.CompanyService;
import com.jb.service.CustomerService;
import com.jb.service.UserService;

/*Help Spring to know that this is service */
@Service
public class UserSystem {

	// Fields

	/* All Service Fields Use */
	private ClientSession session;

	private UserService userService;

	private CompanyService companyService;

	private CustomerService customerService;

	private AdminService adminService;

	// Make a DI
	@Autowired
	// Constructor initializing ServiceFields
	public UserSystem(ClientSession session, UserService userService, CompanyService companyService,
			CustomerService customerService, AdminService adminService) {
		this.session = session;
		this.userService = userService;
		this.companyService = companyService;
		this.customerService = customerService;
		this.adminService = adminService;
	}

	/**
	 * Get email and Password Cake the type of Customer Get the right Service for
	 * User Type
	 */
	public ClientSession login(String email, String password) throws InvalidLoginExeption {
		String password1 = "1234";

		String email1 = "admin";

		if (email.equals(email1) && password.equals(password1)) {
			session.setAdminService(adminService);

			session.accessed();
			return session;
		}

		User user = userService.findUserByEmailAndPassword(email, password);

		if (user == null) {
			throw new InvalidLoginExeption("Email or password are invalid");
		}

		Client clientType = user.getClient();

		if (clientType instanceof Company) {

			session.setCompanyService(companyService);
			companyService.setCompanyId(user.getId());

			session.accessed();
			return session;

		} else if (clientType instanceof Customer) {
			session.setCustomerService(customerService);
			customerService.setCustomerId(user.getId());
			session.accessed();
			return session;

		}
		return null;

	}

}
