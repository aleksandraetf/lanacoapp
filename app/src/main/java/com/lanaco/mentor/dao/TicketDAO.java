package com.lanaco.mentor.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.model.Ticket;
import com.lanaco.mentor.model.User;


public interface TicketDAO extends CrudRepository<Ticket, Long> {

	public ArrayList<Ticket> findAllByFlight(Flight flight);
	
	public ArrayList<Ticket> findAllByUser(User user);
	
	public ArrayList<Ticket> findAllByUser_Username(String username);
	
	public Flight findOneByFlight_Id(Long id);
	
}
