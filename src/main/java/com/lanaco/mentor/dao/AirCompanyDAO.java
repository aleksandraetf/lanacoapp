package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import com.lanaco.mentor.model.AirCompany;



public interface AirCompanyDAO extends CrudRepository<AirCompany, Long> {

	public AirCompany findOneByName(String name);

	public ArrayList<AirCompany> findAllByIsActive(boolean idActive); 

}