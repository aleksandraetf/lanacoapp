package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.FlightDAO;
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
		// TODO Auto-generated method stub
		return null;
	}

	
	//
	@Override
	public Flight getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Flight recObj) {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public String edit(Flight recObj) {
		// TODO Auto-generated method stub
		return null;
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
