package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import com.lanaco.mentor.model.Destination;



public interface DestinationDAO extends CrudRepository<Destination, Long> {

	public Destination findOneByName(String name);
	
	public ArrayList<Destination> findAll();

}
