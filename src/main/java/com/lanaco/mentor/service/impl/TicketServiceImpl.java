package com.lanaco.mentor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanaco.mentor.dao.AdministratorDAO;
import com.lanaco.mentor.dao.TicketDAO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.model.Ticket;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	public TicketDAO ticketDAO;


	@Override
	public ArrayList<Ticket> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket getOne(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(Ticket recObj) {
		if (recObj.getFlight()==null||recObj.getNumberOfTickets()==null || recObj.getNumberOfTickets()<=0
				|| recObj.getUser()==null || "".equals(recObj.getUser().getUsername())) {
			return "Fail, data missing";
		}
	
		Ticket ticket = new Ticket(recObj.getFlight(),recObj.getUser(),recObj.getNumberOfTickets());

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
	public String flagNotActive(String recObjName) {
		//TO DO
		return null;
	}

}
