package com.lanaco.mentor.dao;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.User;


public interface UserDAO extends CrudRepository<User, Long> {

	public User findOneByUsernameAndPassword(String username, String password);
	public User findOneByUsername(String username);
	
	

}