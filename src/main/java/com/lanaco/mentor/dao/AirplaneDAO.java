package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Airplane;

public interface AirplaneDAO extends CrudRepository<Airplane,Long>{
	
	public ArrayList<Airplane> findAll();
	
	public ArrayList<Airplane> findAllBySeatsGreaterThanEqual(int seats);
	
	public ArrayList<Airplane> findAllByBrand(String brand);
	

	public Airplane findOneByBrandAndSeats(String brand,int seats);
	
	public Airplane findOneByBrand(String brand);
	
}
