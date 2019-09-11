package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.FlightDAO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {
	
	@Autowired
	public FlightDAO flightDAO;
	
	

	@Override
	public ArrayList<Flight> getAll() {
		return (ArrayList<Flight>)flightDAO.findAll();
	}

	
	//
	@Override
	public Flight getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Flight recObj) {
		if (recObj.getFlightDate()==null || recObj.getAirCompany()==null || 
				"".equals(recObj.getAirCompany().getName()) || recObj.getAirplane()==null ||
				recObj.getDestination()==null || "".equals(recObj.getDestination().getName()) 
				|| recObj.getPrice()==null) {
			return "Fail, data missing";
		}
		Flight flight;
		/*Administrator admin = adminDAO.findOneByUsername(recObj.getUsername());
		if (admin != null) {
			return "Fail, administrator with provided name already exists but name must be unique!";
		}*/

		flight = new Flight(recObj.getPrice(),recObj.getAirCompany(),recObj.getDestination(),
				recObj.getAirplane(),recObj.getFlightDate(),true);
		try {
			flightDAO.save(flight);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			return "Exception in flight Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in POST: ]", ex2);
			return "Exception in flight Controller POST (ex2), contact admins!";
		}
		return "OK, flight saved";
	}

	@Override
	public String edit(Flight recObj) {
		if (recObj.getFlightDate()==null || recObj.getAirCompany()==null || 
				"".equals(recObj.getAirCompany().getName()) || recObj.getAirplane()==null ||
				recObj.getDestination()==null || "".equals(recObj.getDestination().getName()) 
				|| recObj.getPrice()==null) {
			return "Fail, data missing";
		}
	/*	Aircompany aircompany = airCompanyDAO.findOneByName(recObj.getName());
		if (aircompany == null) {
			return "Fail, air company with provided name not found!";
		}
		
		*
		* mogu postojati 2 aviona u isto vrijeme za istu destinaciju sa istim modelom aviona!?
		* 
		*
		*
		*/
	//	aircompany.setName(recObj.getName());

		try {
			//airCompanyDAO.save(aircompany);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in PUT: ]", ex1);
			return "Exception in flight Controller PUT (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in PUT: ]", ex2);
			return "Exception in flight Controller PUT (ex2), contact admins!";
		}
		return "OK, flight edited!";
	}

	@Override
	public ArrayList<Flight> findAllByFlightDate(Date flightDate) {
		return (ArrayList<Flight>)flightDAO.findAllByFlightDate(flightDate);
	}

	@Override
	public ArrayList<Flight> findAllByFlightDateAndDestination(Date flightDate, Destination destination) {
		return (ArrayList<Flight>)flightDAO.findAllByFlightDateAndDestination(flightDate, destination);

	}

	@Override
	public ArrayList<Flight> findAllByFlightDateBetweenAndDestination(Date flightDate1, Date flightDate2,
			Destination destination) {
		return (ArrayList<Flight>)flightDAO.findAllByFlightDateBetweenAndDestination(flightDate1, flightDate2, destination);

	}

	@Override
	public ArrayList<Flight> findAllByAirCompanyAndFlightDate(Aircompany aircompany, Date flightDate) {
	return (ArrayList<Flight>)flightDAO.findAllByAirCompanyAndFlightDate(aircompany, flightDate);
	}

}
