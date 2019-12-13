package com.lanaco.mentor.service;

import java.util.ArrayList;

import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;

public interface AdministratorService extends GenericServiceInterface<Administrator>{


	public ArrayList<Administrator> findAllByAirCompany(Aircompany airCompany);
	public Administrator findOneByUsernameAndPassword(String username, String password);
	public String flagNotActive(String username);

}
