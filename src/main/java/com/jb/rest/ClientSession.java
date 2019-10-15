package com.jb.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jb.service.AdminService;
import com.jb.service.CompanyService;
import com.jb.service.CustomerService;

//regular component that every one use it and for spring to know in Configuration
@Component
/* Scope for not being singleton */
@Scope("prototype")
public class ClientSession {
	
	// Fields

	/* All Service Fields Use */
	
	private CompanyService companyService;
	
	private CustomerService customerService;
	
	private AdminService adminService;
	
	private long lastAccessedMillis;
	
	//Getters and Setters
	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	public long getLastAccessedMillis() {
		return lastAccessedMillis;
	}

	// Change for the current time to Know When Lost Or made An action
	public void accessed() {
		this.lastAccessedMillis =System.currentTimeMillis();
	}
	
	
	

}
