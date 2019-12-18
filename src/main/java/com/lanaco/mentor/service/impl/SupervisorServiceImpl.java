package com.lanaco.mentor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.SupervisorDAO;
import com.lanaco.mentor.model.Supervisor;
import com.lanaco.mentor.service.SupervisorService;

@Service
public class SupervisorServiceImpl implements SupervisorService {

	@Autowired
	public SupervisorDAO supervisorDAO;
	
	
	@Override
	public ArrayList<Supervisor> getAll() {
		return (ArrayList<Supervisor>)supervisorDAO.findAll();
	}

	@Override
	public Supervisor getOne(String name) {
		return supervisorDAO.findOneByUsername(name);
	}

	@Override
	public String save(Supervisor recObj) {
		if (recObj.getUsername() == null || recObj.getUsername().equals("") || recObj.getPassword() == null
				|| recObj.getPassword().equals("")) {
			return "Fail, data missing";
		}
		Supervisor supervisor = supervisorDAO.findOneByUsername(recObj.getUsername());
		if (supervisor != null) {
			return "Fail, supervisor with provided name already exists but name must be unique!";
		}

		supervisor = new Supervisor(recObj.getUsername(), recObj.getPassword(),recObj.getEmail());

		try {
			supervisorDAO.save(supervisor);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			return "Exception in Supervisor Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in POST: ]", ex2);
			return "Exception in Supervisor Controller POST (ex2), contact admins!";
		}
		return "OK, Supervisor saved";
	}

	@Override
	public String edit(Supervisor recObj) {
		if (recObj.getUsername() == null || recObj.getUsername().equals("") || recObj.getPassword() == null
				|| recObj.getPassword().equals("")) {
			return "Fail, data missing!";
		}
		Supervisor supervisor = supervisorDAO.findOneByUsername(recObj.getUsername());
		if (supervisor == null) {
			return "Fail, supervisor with provided name not found!";
		}
		supervisor.setPassword(recObj.getPassword());

		try {
			supervisorDAO.save(supervisor);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in PUT: ]", ex1);
			return "Exception in supervisor Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in PUT: ]", ex2);
			return "Exception in supervisor Controller PUT (ex2), contact admins!";
		}
		return "OK, supervisor edited!";
	}

	@Override
	public Supervisor findOneByUsernameAndPassword(String username, String password) {
		return supervisorDAO.findOneByUsernameAndPassword(username, password);
	}

}
