package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.AdministratorDAO;
import com.lanaco.mentor.dao.AirCompanyDAO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	public AdministratorDAO adminDAO;
	
	@Autowired
	public AirCompanyDAO airCompanyDAO;
	

	@Override
	public ArrayList<Administrator> getAll() {
		return (ArrayList<Administrator>)adminDAO.findAllByIsActive(true); 
	}

	@Override
	public Administrator getOne(String name) {
		return adminDAO.findOneByUsernameAndIsActive(name,true);
	}
	
	@Override
	public ArrayList<Administrator> findAllByAirCompany(Aircompany airCompany) {
		return (ArrayList<Administrator>)adminDAO.findAllByAirCompanyAndIsActive(airCompany,true);
	}

	@Override
	public Administrator findOneByUsernameAndPassword(String username, String password) {
		return adminDAO.findOneByUsernameAndPasswordAndIsActive(username, password,true);
	}

	@Override
	public String save(Administrator recObj) {
		if (recObj.getUsername() == null || "".equals(recObj.getUsername()) || recObj.getPassword() == null
				|| "".equals(recObj.getPassword()) || recObj.getAirCompany()==null || 
				recObj.getAirCompany().getName()==null || "".equals(recObj.getAirCompany().getName())) {
			return "Fail, data missing";
		}
		Aircompany airCompany= airCompanyDAO.findOneByNameAndIsActive(recObj.getAirCompany().getName(),true);
		Administrator admin = adminDAO.findOneByUsernameAndIsActive(recObj.getUsername(),true);
		if (admin != null) {
			return "Fail, administrator with provided name already exists but name must be unique!";
		}

		if(airCompany==null) {
			return "Fail, nemoguce kreirati Administratora jer referencirana Aviokompanija("
					+recObj.getAirCompany().getName()
					+") ne postoji!";
		}
		
		
		try {
			admin = new Administrator(recObj.getUsername(), recObj.getPassword(),airCompany, recObj.getIsActive());
			adminDAO.save(admin);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			ex1.printStackTrace();
			return "Exception in Admin Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in POST: ]", ex2);
			ex2.printStackTrace();
			return "Exception in Admin Controller POST (ex2), contact admins!";
		}
		return "OK, Admin saved";
	}

	@Override
	public String edit(Administrator recObj) {
		if (recObj.getUsername() == null || "".equals(recObj.getUsername()) || recObj.getPassword() == null
				|| "".equals(recObj.getPassword())) {
			return "Fail, data missing!";
		}
		Administrator admin = adminDAO.findOneByUsernameAndIsActive(recObj.getUsername(),true);
		if (admin == null) {
			return "Fail, admin with provided name not found!";
		}
		admin.setPassword(recObj.getPassword());
		admin.setIsActive(recObj.getIsActive());

		try {
			adminDAO.save(admin);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in PUT: ]", ex1);
			ex1.printStackTrace();
			return "Exception in Admin Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			ex2.printStackTrace();
			//log.error("[User Controller exception in PUT: ]", ex2);
			return "Exception in Admin Controller PUT (ex2), contact admins!";
		}
		return "OK, Admin edited!";
	}

	@Override
	public String flagNotActive(String username) {
		Administrator administrator = adminDAO.findOneByUsernameAndIsActive(username,true);
		if (username == null || "".equals(username)) {
			return "Fail, data missing!";
		}
		if (administrator == null) {
			return "Fail, administrator with provided name not found!";
		}
		
		
		administrator.setIsActive(false);
		
		try {
			adminDAO.save(administrator);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Admin Controller DELETE (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Admin Controller DELETE (ex2), contact admins!";
		}

		return "OK, Administrator deleted!";
	}

}
