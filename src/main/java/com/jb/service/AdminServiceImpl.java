package com.jb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.CompanyRepository;
import com.jb.dao.CustomerRepository;
import com.jb.dao.UserRepository;
import com.jb.entity.Company;
import com.jb.entity.Customer;
import com.jb.entity.User;
import com.jb.service.ex.UserNotExisisteException;

/*Help Spring to know that this is service and give all the c r u d e
 * works with Jpa and Controller */
@Service
/* Scope for not being singleton */
@Scope("prototype")
public class AdminServiceImpl implements AdminService {


	// Fields

	/* All Repo Fields Use */
	private UserRepository userDao;

	private CustomerRepository customerDao;

	private CompanyRepository companyDao;

	// Make a DI
	@Autowired
	// Constructor initializing RepoFields
	public AdminServiceImpl(UserRepository adminDao, CustomerRepository customerDao, CompanyRepository companyDao) {
		this.userDao = adminDao;
		this.customerDao = customerDao;
		this.companyDao = companyDao;

	}

	/**
	 * Get Email Password Name Create new Client from type Customer and new User Set
	 * all fields that Customer need Save to DB the User Customer
	 */

	@Override
	public User createUserCustomer(String email, String password, String name) {

		Customer customer = new Customer();
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setId(0);
		user.setClient(customer);
		customer.setName(name);
		customer.setId(0);
		customerDao.save(customer);
		return userDao.save(user);

	}

	/**
	 * Get Email Password Name Create new Client from type Company and new User Set
	 * all fields that Company need Save to DB the User Company
	 */

	@Override
	public User createUserCompany(String email, String password, String name) {
		Company company = new Company();
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setId(0);
		user.setClient(company);
		company.setName(name);
		company.setId(0);
		companyDao.save(company);
		return userDao.save(user);

	}

	/**
	 * Get User and Update to DB the User
	 */

	@Override
	public User updateUser(User user) {
		return userDao.save(user);

	}

	/**
	 * Get User By ID and return the specific User
	 */
	@Override
	public User getUserById(long id) {
		return userDao.findById(id).orElse(null);
	}

	/**
	 * Get ID of User Find the User in DB Get The Type of User And delete him From
	 * specific Entity and from User Entity
	 */

	@Override
	public void removeUser(long id) throws UserNotExisisteException {
		Optional<User> findById = userDao.findById(id);
		if (findById.isPresent()) {
			if (findById.get().getClient() instanceof Customer) {
				customerDao.deleteById(id);
			} else {
				companyDao.deleteById(id);
			}
		}
		userDao.deleteById(id);

	}

	/**
	 * Get all Customers in DB
	 */
	@Override
	public List<Customer> getAllCustomers() {
		return customerDao.findAll();
	}

	/**
	 * Get all Companies in DB
	 */
	@Override
	public List<Company> getAllCompanies() {
		return companyDao.findAll();
	}

	/**
	 * Get all Companies in DB for future work with User entity if it will be update
	 */
	@Override
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

}
