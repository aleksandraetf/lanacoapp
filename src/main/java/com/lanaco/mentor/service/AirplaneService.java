package com.lanaco.mentor.service;

import java.util.ArrayList;

import com.lanaco.mentor.model.Airplane;

public interface AirplaneService extends GenericServiceInterface<Airplane>{

	
	public ArrayList<Airplane> findAllBySeatsGreaterThanEqual(int seats);
	
	public ArrayList<Airplane> findAllByBrand(String brand);
	
	public Airplane findOneByBrandAndSeats(String brand,int seats);

	public String flagNotActive(String brand);
}

