package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.AirCompanyDAO;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.service.AirCompanyService;

@Service
public class AirCompanyServiceImpl implements AirCompanyService {
	
	@Autowired
	AirCompanyDAO airCompanyDAO;

	@Override
	public ArrayList<Aircompany> getAll() {
		return (ArrayList<Aircompany>)airCompanyDAO.findAllByIsActive(true);
	}

	@Override
	public Aircompany getOne(String name) {
		return airCompanyDAO.findOneByNameAndIsActive(name,true);
	}

	@Override
	public String save(Aircompany recObj) {
		if (recObj.getName() == null || "".equals(recObj.getName())) {
			return "Fail, data missing";
		}
		Aircompany aircompany = airCompanyDAO.findOneByNameAndIsActive(recObj.getName(),true);
		if (aircompany != null) {
			return "Fail, air company with provided name already exists but name must be unique!";
		}

		aircompany = new Aircompany(recObj.getName(), true);

		try {
			airCompanyDAO.save(aircompany);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Aircompany Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Aircompany Controller POST (ex2), contact admins!";
		}
		return "OK, Air company saved";
	}

	@Override
	public String edit(Aircompany recObj) {
		if (recObj.getName() == null || "".equals(recObj.getName())) {
			return "Fail, data missing!";
		}
		Aircompany aircompany = airCompanyDAO.findOneByIdAndIsActive(recObj.getId(),true);
		if (aircompany == null) {
			return "Fail, air company do not exist!";
		}
		
		Aircompany aircompanyOther = airCompanyDAO.findOneByNameAndIsActive(recObj.getName(),true);
		if (aircompanyOther != null) {
			return "Fail, air company with provided name already exists but name must be unique!";
			
		}
		aircompany.setName(recObj.getName());
		aircompany.setIsActive(recObj.getIsActive());

		try {
			airCompanyDAO.save(aircompany);
		} catch (IllegalArgumentException ex1) {
			return "Exception in AirCompany Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in AirCompany Controller PUT (ex2), contact admins!";
		}
		return "OK, Air company edited!";
	}
	
	@Override
	public String flagNotActive(String name) {
		Aircompany aircompany = airCompanyDAO.findOneByNameAndIsActive(name,true);
		if (name == null || name.equals("")) {
			return "Fail, data missing!";
		}
		if (aircompany == null) {
			return "Fail, AirCompany with provided name not found!";
		}
		aircompany.setIsActive(false);

		try {
			airCompanyDAO.save(aircompany);
		} catch (IllegalArgumentException ex1) {
			return "Exception in AirCompany Controller DELETE (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in AirCompany Controller DELETE (ex2), contact admins!";
		}

		return "OK, AirCompany deleted!";
	}

}
