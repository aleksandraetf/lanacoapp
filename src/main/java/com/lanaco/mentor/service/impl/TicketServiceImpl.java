package com.lanaco.mentor.service.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.AdministratorDAO;
import com.lanaco.mentor.dao.FlightDAO;
import com.lanaco.mentor.dao.TicketDAO;
import com.lanaco.mentor.dao.UserDAO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.model.Ticket;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	public TicketDAO ticketDAO;
	
	@Autowired
	public FlightDAO flightDAO;
	
	@Autowired UserDAO userDAO;


	@Override
	public ArrayList<Ticket> getAll() {
		return (ArrayList<Ticket>)ticketDAO.findAll();
	}

	@Override
	public Ticket getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Ticket recObj) {
		if (recObj.getFlight().getId()==null||recObj.getNumberOfTickets()==null || recObj.getNumberOfTickets()<=0
				|| recObj.getUser()==null || "".equals(recObj.getUser().getUsername())) {
			return "Fail, data missing";
		}

		Flight flight=flightDAO.findOneById(recObj.getFlight().getId());
		User user=userDAO.findOneByUsername(recObj.getUser().getUsername());
		
		if(flight == null)
			return "Fail, Ne postoji let sa referenciranim id-em("+recObj.getFlight().getId()+"), karta nije sacuvana!";
		
		if(user == null)
			return "Fail, Ne postoji referencirani korisnicki nalog("+recObj.getUser().getUsername()+"), karta nije sacuvana!";
		
		if((flight.getSeatsReserved()+recObj.getNumberOfTickets())>flight.getAirplane().getSeats())
			return "Fail, Ne postoji dovoljno sjedista za rezervisanje trazenog broja karata!"
				+"Slobodan broj sjedista je : "+(flight.getAirplane().getSeats()-flight.getSeatsReserved());
		
		Ticket ticket = new Ticket(flight,user,recObj.getNumberOfTickets());
		
		try {
			ticketDAO.save(ticket);
		} catch (IllegalArgumentException ex1) {
			//log.error("[User Controller exception in POST: ]", ex1);
			return "Exception in Ticket Controller POST (ex1), contact admins!";
		} catch (Exception ex2) {
			//log.error("[User Controller exception in POST: ]", ex2);
			return "Exception in Ticket Controller POST (ex2), contact admins!";
		}
		return "OK, Ticket saved";
	}

	
	@Override
	public String edit(Ticket recObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Ticket> findAllByFlight(Flight flight) {
		return (ArrayList<Ticket>)ticketDAO.findAllByFlight(flight);
	}

	@Override
	public ArrayList<Ticket> findAllByUser(User user) {
		return (ArrayList<Ticket>)ticketDAO.findAllByUser(user);
	}

	@Override
	public ArrayList<Ticket> findAllByUsername(String username){
		return (ArrayList<Ticket>)ticketDAO.findAllByUser_Username(username);
	}

}
