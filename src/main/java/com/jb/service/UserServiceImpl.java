package com.jb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.UserRepository;
import com.jb.entity.User;

/*Help Spring to know that this is service and give all the c r u d e
 * works with Jpa and Controller */
@Service
/* Scope for not being singleton */
@Scope("prototype")
public class UserServiceImpl implements UserService {

	// Fields
	private UserRepository userRepo;

	// Make a DI
	@Autowired
	// Constructor initializing RepoFields
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * For Login Get Email And Password Find if Exists in DB
	 */
	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}

}
