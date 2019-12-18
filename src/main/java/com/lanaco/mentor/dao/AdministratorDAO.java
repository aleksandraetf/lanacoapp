package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;



public interface AdministratorDAO extends CrudRepository<Administrator,Long>{
	
	public Administrator 
		findOneByUsernameAndPasswordAndIsActive(String username, String password,boolean isActive);
	public Administrator findOneByUsernameAndIsActive(String username,boolean isActive);

	public ArrayList<Administrator> findAllByAirCompanyAndIsActive(Aircompany airCompany,boolean isActive);
	
	public ArrayList<Administrator> findAllByIsActive(boolean isActive);

	public Administrator findOneByEmailAndIsActive(String email,boolean isActive);
	
}
