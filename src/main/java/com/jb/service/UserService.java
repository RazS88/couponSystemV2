package com.jb.service;

import com.jb.entity.User;

public interface UserService {
	/* Query for Jpa to Find User by Name And Password */
	User findUserByEmailAndPassword(String email, String password);
}
