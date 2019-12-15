package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Aircompany;



public interface AirCompanyDAO extends CrudRepository<Aircompany, Long> {

	public Aircompany findOneByNameAndIsActive(String name,boolean isActive);
	public Aircompany findOneByIdAndIsActive(Long Id,boolean isActive);
	
	public ArrayList<Aircompany> findAllByIsActive(boolean isActive);

}