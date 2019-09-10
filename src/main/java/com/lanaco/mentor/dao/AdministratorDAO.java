package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;



public interface AdministratorDAO extends CrudRepository<Administrator,Long>{
	
	public Administrator findOneByUsernameAndPassword(String username, String password);
	public Administrator findOneByUsername(String username);

	public ArrayList<Administrator> findAllByAirCompany(Aircompany airCompany);
	
	
}
