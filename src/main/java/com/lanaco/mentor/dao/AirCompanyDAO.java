package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Aircompany;



public interface AirCompanyDAO extends CrudRepository<Aircompany, Long> {

	public Aircompany findOneByName(String name);
	public Aircompany findOneById(Long Id);
	
	public ArrayList<Aircompany> findAll();

}