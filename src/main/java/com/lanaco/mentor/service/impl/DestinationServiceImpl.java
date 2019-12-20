package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
		return (ArrayList<Destination>) destinationDAO.findAllByIsActive(true);
	}

	@Override
	public Destination getOne(String name) {
		return destinationDAO.findOneByNameAndIsActive(name,true);
	}

	@Override
	public String save(Destination recObj) {
		if (recObj.getName() == null || recObj.getName().equals("")){
			return "Fail, data missing";
		}
		Destination destination = destinationDAO.findOneByNameAndIsActive(recObj.getName(),true);
		if (destination != null) {
			return "Fail, destination with provided name already exists but name must be unique!";
		}

		destination = new Destination(recObj.getName(),true);

		try {
			destinationDAO.save(destination);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Destination Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Destination Controller POST (ex2), contact admins!";
		}
		return "OK, Destination saved";
	}

	@Override
	public String edit(Destination recObj) {
		if (recObj.getName() == null || "".equals(recObj.getName())) {
			return "Fail, data missing!";
		}
		Destination destination = destinationDAO.findOneByIdAndIsActive(recObj.getId(),true);
			if (destination == null) {
			return "Fail, destination with provided id not found!";
		}
			
		Destination destinationOther = destinationDAO.findOneByNameAndIsActive(recObj.getName(),true);
		if (destinationOther != null) {
			return "Fail, destination with provided name already exists but name must be unique!";
		}
			
			
		destination.setName(recObj.getName());

		try {
			destinationDAO.save(destination);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Destination Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Destination Controller PUT (ex2), contact admins!";
		}
		return "OK, Destination edited!";
	}
	@Override
	public String flagNotActive(String name) {
		Destination destination = destinationDAO.findOneByNameAndIsActive(name,true);
		if (name == null || name.equals("")) {
			return "Fail, data missing!";
		}
		if (destination == null) {
			return "Fail, destination with provided name not found!";
		}

		destination.setIsActive(false);
		
		try {
			destinationDAO.save(destination);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Destination Controller DELETE (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Destination Controller DELETE (ex2), contact admins!";
		}

		return "OK, Destination deleted!";
	}

}
