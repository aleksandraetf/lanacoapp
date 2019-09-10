package com.lanaco.mentor.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import com.lanaco.mentor.model.AirCompany;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;



public interface FlightDAO extends CrudRepository<Flight, Long> {

	public ArrayList<Flight> findAllByFlightDate(Date flightDate);
	
	public ArrayList<Flight> findAllByFlightDateAndDestination(Date flightDate,Destination destination);
	
	public ArrayList<Flight> findAllByFlightDateBetweenAndDestination(Date flightDate1, Date flightDate2,Destination destination);
	
	public ArrayList<Flight> findAllByAirCompanyAndFlightDate(AirCompany aircompany,Date flightDate);
	
}
