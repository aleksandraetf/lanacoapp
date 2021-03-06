package com.lanaco.mentor.service;

import com.lanaco.mentor.model.User;

public interface UserService extends GenericServiceInterface<User>{

	public User findOneByUsernameAndPassword(String username, String password);

	public String flagNotActive(String username);
	
	public User findOneByUsername(String username);
}
