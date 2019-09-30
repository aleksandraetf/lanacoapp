package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.AirplaneDAO;
import com.lanaco.mentor.model.Airplane;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.service.AirplaneService;

@Service
public class AirplaneServiceImpl implements AirplaneService{
	
	@Autowired
	public AirplaneDAO airplaneDAO;

	@Override
	public ArrayList<Airplane> getAll() {
		return (ArrayList<Airplane>)airplaneDAO.findAll().stream().filter(airplane->airplane.getIsActive())
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	@Override
	public ArrayList<Airplane> findAllBySeatsGreaterThanEqual(int seats) {
		return (ArrayList<Airplane>)airplaneDAO.findAllBySeatsGreaterThanEqual(seats);
	}


	@Override
	public ArrayList<Airplane> findAllByBrand(String brand) {
		return (ArrayList<Airplane>)airplaneDAO.findAllByBrand(brand);
	}


	//nema smisla implementirati
	@Override
	public Airplane getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Airplane recObj) {
		if (recObj.getBrand() == null || recObj.getBrand().equals("") || recObj.getIsActive()==null) {
			return "Fail, data missing";
		}
		Airplane plane = airplaneDAO.findOneByBrandAndSeats(recObj.getBrand(),recObj.getSeats());
		if (plane != null) {
			return "Fail, plane with provided brand and seats already exists but brand and seats must be unique!";
		}

		plane = new Airplane(recObj.getBrand(), recObj.getSeats(),recObj.getIsActive());

		try {
			airplaneDAO.save(plane);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			return "Exception in airplane Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in POST: ]", ex2);
			return "Exception in airplane Controller POST (ex2), contact admins!";
		}
		return "OK, airplane saved";
	}

	 
	@Override
	public String edit(Airplane recObj) {
		if (recObj.getBrand() == null || recObj.getBrand().equals("")) {
			return "Fail, data missing!";
		}
		Airplane plane = airplaneDAO.findOneById(recObj.getId());
		if (plane == null) {
			return "Fail, airplane not found!";
		}
		plane.setIsActive(recObj.getIsActive());
		//nema smisla da se moze promijeniti broj sjedista i brend modela aviona
		
		try {
			airplaneDAO.save(plane);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in PUT: ]", ex1);
			return "Exception in airplane Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in PUT: ]", ex2);
			return "Exception in airplane Controller PUT (ex2), contact admins!";
		}
		return "OK, plane edited!";
	}

	@Override
	public String flagNotActive(String brand) {
		if (brand == null || brand.equals("")) {
			return "Fail, data missing!";
		}

		Airplane airplane = airplaneDAO.findOneByBrand(brand);
		if (airplane == null) {
			return "Fail, airplane with provided brand not found!";
		}

		//replace with code mark as inactive
		airplane.setIsActive(false);
		try {
			airplaneDAO.save(airplane);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Airplane Controller DELETE (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Airplane Controller DELETE (ex2), contact admins!";
		}

		return "OK, Airplane deleted!";
	}
	
	//potrebno za save
	@Override
	public Airplane findOneByBrandAndSeats(String brand, int seats) {
		return airplaneDAO.findOneByBrandAndSeats(brand, seats);
	}


}
