package com.lanaco.mentor.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;



public interface FlightDAO extends CrudRepository<Flight, Long> {
	

	public ArrayList<Flight> findAllByIsActive(boolean isActive);

	public ArrayList<Flight> findAllByFlightDateAndIsActive(Date flightDate,boolean isActive);
	
	public ArrayList<Flight> 
		findAllByFlightDateAndDestinationAndIsActive(Date flightDate,Destination destination,
														boolean isActive);
	
	public ArrayList<Flight> findAllByFlightDateBetweenAndDestinationAndIsActive(Date flightDate1,
				Date flightDate2,Destination destination,boolean isActive);
	
	public ArrayList<Flight> findAllByAirCompanyAndFlightDateAndIsActive(Aircompany aircompany,
			Date flightDate,boolean isActive);
	
	public Flight findOneByIdAndIsActive(Long id,boolean isActive);
	
}
