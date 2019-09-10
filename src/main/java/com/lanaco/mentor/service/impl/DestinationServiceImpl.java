package com.lanaco.mentor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.lanaco.mentor.dao.DestinationDAO;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.service.DestinationService;

public class DestinationServiceImpl implements DestinationService{

	
	@Autowired
	DestinationDAO destinationDAO;

	@Override
	public ArrayList<Destination> getAll() {
		return (ArrayList<Destination>) destinationDAO.findAll();
	}

	@Override
	public Destination getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Destination recObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(Destination recObj) {
		// TODO Auto-generated method stub
		return null;
	}

}
