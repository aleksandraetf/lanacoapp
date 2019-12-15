package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Airplane;

public interface AirplaneDAO extends CrudRepository<Airplane,Long>{
	
	public ArrayList<Airplane> findAllByIsActive(boolean isActive);
	
	public ArrayList<Airplane> findAllBySeatsGreaterThanEqualAndIsActive(int seats,boolean isActive);
	
	public ArrayList<Airplane> findAllByBrandAndIsActive(String brand,boolean isActive);
	

	public Airplane findOneByBrandAndSeatsAndIsActive(String brand,int seats,boolean isActive);
	
	public Airplane findOneByBrandAndIsActive(String brand,boolean isActive);
	
	public Airplane findOneByIdAndIsActive(Long Id,boolean isActive);
	
}
