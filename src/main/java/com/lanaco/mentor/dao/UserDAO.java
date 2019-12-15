package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.User;


public interface UserDAO extends CrudRepository<User, Long> {

	public User findOneByUsernameAndPasswordAndIsActive(String username, String password,boolean isActive);
	public User findOneByUsernameAndIsActive(String username,boolean isActive);
	public User findOneByEmailAndIsActive(String email,boolean isActive);
	public ArrayList<User> findAllByIsActive(boolean isActive);
	
}