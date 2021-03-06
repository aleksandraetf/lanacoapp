package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.AdministratorDAO;
import com.lanaco.mentor.dao.AirCompanyDAO;
import com.lanaco.mentor.dao.AirplaneDAO;
import com.lanaco.mentor.dao.DestinationDAO;
import com.lanaco.mentor.dao.FlightDAO;
import com.lanaco.mentor.dao.UserDAO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.model.Airplane;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {
	
	@Autowired
	public FlightDAO flightDAO;
	
	@Autowired
	public AirCompanyDAO airCompanyDAO;
	
	@Autowired
	public DestinationDAO destinationDAO;
	
	@Autowired
	public AirplaneDAO airplaneDAO;
	
	@Autowired
	public AdministratorDAO administratorDAO;
	

	@Override
	public ArrayList<Flight> getAll() {
		return (ArrayList<Flight>)flightDAO.findAllByIsActive(true);
	}

	
	//
	@Override
	public Flight getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Flight recObj) {
		if (recObj.getFlightDate()==null || recObj.getAirCompany()==null
				|| recObj.getAirplane()==null ||
				recObj.getDestination()==null 
				|| recObj.getPrice()==null) {
			return "Fail, data missing";
		}
		
		if(airCompanyDAO.findOneByIdAndIsActive(recObj.getAirCompany().getId(),true)==null)
			return "Fail, ne postoji referencirana Aviokompanija("+recObj.getAirCompany().getId()+"), let nije sacuvan!";
		
		if(destinationDAO.findOneByIdAndIsActive(recObj.getDestination().getId(),true)==null)
			return "Fail, ne postoji referencirana Destinacija("+recObj.getDestination().getName()+"), let nije sacuvan!";
		
		if(airplaneDAO.findOneByIdAndIsActive(recObj.getAirplane().getId(),true)==null)
			return "Fail, ne postoji referencirani Brend Aviona("+recObj.getAirplane().getBrand()+"), let nije sacuvan!";
		
		Airplane airplane=airplaneDAO.findOneByIdAndIsActive(recObj.getAirplane().getId(),true);
		Aircompany airCompany=airCompanyDAO.findOneByIdAndIsActive(recObj.getAirCompany().getId(),true);
		Destination destination=destinationDAO.findOneByIdAndIsActive(recObj.getDestination().getId(),true);
		
		
		try {
			Flight flight = new Flight(recObj.getPrice(),airCompany,destination,
					airplane,recObj.getFlightDate(),true);
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
		
		Flight flight = flightDAO.findOneByIdAndIsActive(recObj.getId(),true);
		if(flight == null) {
			return "Fail, flight not found";
		}
		
		flight.setFlightDate(recObj.getFlightDate());
		flight.setDestination(recObj.getDestination());
		flight.setAirplane(recObj.getAirplane());
		flight.setAirCompany(recObj.getAirCompany());
		flight.setIsActive(recObj.getIsActive());
		flight.setSeatsReserved(recObj.getSeatsReserved());
		flight.setPrice(recObj.getPrice());

		try {
			flightDAO.save(flight);
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
		return (ArrayList<Flight>)flightDAO.findAllByFlightDateAndIsActive(flightDate,true);
	}

	@Override
	public ArrayList<Flight> findAllByFlightDateAndDestination(Date flightDate, Destination destination) {
		return (ArrayList<Flight>)flightDAO.findAllByFlightDateAndDestinationAndIsActive(flightDate, destination,true);

	}

	@Override
	public ArrayList<Flight> findAllByFlightDateBetweenAndDestination(Date flightDate1, Date flightDate2,
			Destination destination) {
		return (ArrayList<Flight>)flightDAO.findAllByFlightDateBetweenAndDestinationAndIsActive(flightDate1, flightDate2, destination,true);

	}

	@Override
	public ArrayList<Flight> findAllByAirCompanyAndFlightDate(Aircompany aircompany, Date flightDate) {
	return (ArrayList<Flight>)flightDAO.findAllByAirCompanyAndFlightDateAndIsActive(aircompany, flightDate,true);
	}


	@Override
	public String flagNotActive(Long recObjId) {
		//moze se prosiriti da recimo ako administrator oznaci Flight sa nije aktivan da se automatski
		//sve karte ciji je let izbrisan vise ne prikazuju
		//ili da se prikazuju a da se naznaci da je let otkazan/obrisan ili kako vec
		
		Flight flight = flightDAO.findOneByIdAndIsActive(recObjId,true);
		if (recObjId == null) {
			return "Fail, data missing!";
		}
		if (flight == null) {
			return "Fail, Flight with provided id not found!";
		}
		flight.setIsActive(false);
		
		try {
			flightDAO.save(flight);
		} catch (IllegalArgumentException ex1) {
			return "Exception in Destination Controller DELETE (ex1), contact admins!";
		} catch (Exception ex2) {
			return "Exception in Destination Controller DELETE (ex2), contact admins!";
		}

		return "OK, Flight deleted!";
	}
	
	@Override
	public ArrayList<Flight> findAllByAdministratorUsername(String username){
		Administrator administrator=administratorDAO.findOneByUsernameAndIsActive(username, true);
		if(administrator==null)
			return null;
		return flightDAO.
				findAllByAirCompany_NameAndIsActive(administrator.getAirCompany().getName(),true);
	}
	public ArrayList<Flight> findAllByAdministratorEmail(String email){
		Administrator administrator=administratorDAO.findOneByEmailAndIsActive(email, true);
		if(administrator==null)
			return null;
		return flightDAO.
				findAllByAirCompany_NameAndIsActive(administrator.getAirCompany().getName(),true);
	}

}
