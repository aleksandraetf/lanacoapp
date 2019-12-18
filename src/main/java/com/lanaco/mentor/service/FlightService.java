package com.lanaco.mentor.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.ResponseEntity;

import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;

public interface FlightService extends GenericServiceInterface<Flight>{

public ArrayList<Flight> findAllByFlightDate(Date flightDate);
	
	public ArrayList<Flight> findAllByFlightDateAndDestination(Date flightDate,Destination destination);
	
	public ArrayList<Flight> findAllByFlightDateBetweenAndDestination(Date flightDate1, Date flightDate2,Destination destination);
	
	public ArrayList<Flight> findAllByAirCompanyAndFlightDate(Aircompany aircompany,Date flightDate);

	public String flagNotActive(Long recObjId);
	
	public ArrayList<Flight> findAllByAdministratorUsername(String username);

	public ArrayList<Flight> findAllByAdministratorEmail(String adminEmail);
}
