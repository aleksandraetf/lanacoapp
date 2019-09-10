package com.lanaco.mentor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.UserDAO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	public UserDAO userDAO;
	
	
	@Override
	public ArrayList<User> getAll() {
		return (ArrayList<User>)userDAO.findAll();
	}

	@Override
	public User getOne(String name) {
		return userDAO.findOneByUsername(name);
	}

	@Override
	public String save(User recObj) {
		if (recObj.getUsername() == null || recObj.getUsername().equals("") || recObj.getPassword() == null
				|| recObj.getPassword().equals("") || recObj.getEmail() == null
						|| recObj.getEmail().equals("")) {
			return "Fail, data missing";
		}
		User user = userDAO.findOneByUsername(recObj.getUsername());
		if (user != null) {
			return "Fail, user with provided name already exists but name must be unique!";
		}

		user = new User(recObj.getUsername(), recObj.getPassword(),recObj.getEmail(), true);

		try {
			userDAO.save(user);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			return "Exception in user Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in POST: ]", ex2);
			return "Exception in user Controller POST (ex2), contact admins!";
		}
		return "OK, user saved";
	}

	@Override
	public String edit(User recObj) {
		if (recObj.getUsername() == null || recObj.getUsername().equals("") || recObj.getPassword() == null
				|| recObj.getPassword().equals("")) {
			return "Fail, data missing!";
		}
		User user = userDAO.findOneByUsername(recObj.getUsername());
		if (user == null) {
			return "Fail, user with provided name not found!";
		}
		user.setPassword(recObj.getPassword());

		try {
			userDAO.save(user);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in PUT: ]", ex1);
			return "Exception in User Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in PUT: ]", ex2);
			return "Exception in User Controller PUT (ex2), contact admins!";
		}
		return "OK, User edited!";
	}

	@Override
	public User findOneByUsernameAndPassword(String username, String password) {
		return userDAO.findOneByUsernameAndPassword(username, password);
	}

}
