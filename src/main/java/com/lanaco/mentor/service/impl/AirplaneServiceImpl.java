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
		return (ArrayList<Airplane>)airplaneDAO.findAllByIsActive(true);
	}
	
	@Override
	public ArrayList<Airplane> findAllBySeatsGreaterThanEqual(int seats) {
		return (ArrayList<Airplane>)airplaneDAO.findAllBySeatsGreaterThanEqualAndIsActive(seats,true);
	}


	@Override
	public ArrayList<Airplane> findAllByBrand(String brand) {
		return (ArrayList<Airplane>)airplaneDAO.findAllByBrandAndIsActive(brand,true);
	}


	@Override
	public Airplane getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Airplane recObj) {
		if (recObj.getBrand() == null || recObj.getBrand().equals("") || recObj.getSeats()==null
				) {
			return "Fail, data missing";
		}
		Airplane plane = airplaneDAO.findOneByBrandAndSeatsAndIsActive(recObj.getBrand(),recObj.getSeats(),true);
		if (plane != null) {
			return "Fail, plane with provided brand and seats already exists but brand and seats must be unique!";
		}

		plane = new Airplane(recObj.getBrand(), recObj.getSeats(),true);

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
		Airplane plane = airplaneDAO.findOneByIdAndIsActive(recObj.getId(),true);
		if (plane == null) {
			return "Fail, airplane not found!";
		}
		plane.setIsActive(recObj.getIsActive());
		
		try {
			airplaneDAO.save(plane);
		} catch (IllegalArgumentException ex1) {
			return "Exception in airplane Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in airplane Controller PUT (ex2), contact admins!";
		}
		return "OK, plane edited!";
	}

	@Override
	public String flagNotActive(String brand) {
		if (brand == null || brand.equals("")) {
			return "Fail, data missing!";
		}

		Airplane airplane = airplaneDAO.findOneByBrandAndIsActive(brand,true);
		if (airplane == null) {
			return "Fail, airplane with provided brand not found!";
		}

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
	
	@Override
	public Airplane findOneByBrandAndSeats(String brand, int seats) {
		return airplaneDAO.findOneByBrandAndSeatsAndIsActive(brand, seats,true);
	}


}
