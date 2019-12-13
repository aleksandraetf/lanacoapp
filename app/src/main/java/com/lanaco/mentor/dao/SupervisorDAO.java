package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Supervisor;


public interface SupervisorDAO extends CrudRepository<Supervisor, Long> {

	public Supervisor findOneByUsernameAndPassword(String username, String password);

	public Supervisor findOneByUsername(String username);
	
	public ArrayList<Supervisor> findAll();

}