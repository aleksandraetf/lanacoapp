package com.lanaco.mentor.service;

import com.lanaco.mentor.model.Aircompany;

public interface AirCompanyService extends GenericServiceInterface<Aircompany>{

	public String flagNotActive(String name);
	
}
