package com.lanaco.mentor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.DestinationDAO;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.service.DestinationService;

@Service
public class DestinationServiceImpl implements DestinationService{

	
	@Autowired
	DestinationDAO destinationDAO;

	@Override
	public ArrayList<Destination> getAll() {
		return (ArrayList<Destination>) destinationDAO.findAll();
	}

	@Override
	public Destination getOne(String name) {
		return destinationDAO.findOneByName(name);
	}

	@Override
	public String save(Destination recObj) {
		if (recObj.getName() == null || recObj.getName().equals("")) {
			return "Fail, data missing";
		}
		Destination destination = destinationDAO.findOneByName(recObj.getName());
		if (destination != null) {
			return "Fail, user with provided name already exists but name must be unique!";
		}

		destination = new Destination(recObj.getName());

		try {
			destinationDAO.save(destination);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			return "Exception in User Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
		//	log.error("[User Controller exception in POST: ]", ex2);
			return "Exception in User Controller POST (ex2), contact admins!";
		}
		return "OK, Destination saved";
	}

	@Override
	public String edit(Destination recObj) {
		if (recObj.getName() == null || recObj.getName().equals("")) {
			return "Fail, data missing!";
		}
		Destination destination = destinationDAO.findOneByName(recObj.getName());
			if (destination == null) {
			return "Fail, destination with provided name not found!";
		}
		destination.setName(recObj.getName());

		try {
			destinationDAO.save(destination);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in PUT: ]", ex1);
			return "Exception in Destination Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in PUT: ]", ex2);
			return "Exception in Destination Controller PUT (ex2), contact admins!";
		}
		return "OK, Destination edited!";
	}

}
