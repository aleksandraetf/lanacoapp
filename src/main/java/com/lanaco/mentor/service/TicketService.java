package com.lanaco.mentor.service;

import java.util.ArrayList;

import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.model.Ticket;
import com.lanaco.mentor.model.User;

public interface TicketService extends GenericServiceInterface<Ticket>{

	public ArrayList<Ticket> findAllByFlight(Flight flight);
	
	public ArrayList<Ticket> findAllByUser(User user);

	public String flagNotActive(String recObjName);
}
