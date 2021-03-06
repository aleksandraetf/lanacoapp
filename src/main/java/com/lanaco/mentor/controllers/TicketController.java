package com.lanaco.mentor.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanaco.mentor.dao.TicketDAO;
import com.lanaco.mentor.dao.UserDAO;
import com.lanaco.mentor.model.Ticket;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.TicketService;
import com.lanaco.mentor.service.UserService;


@RestController
@RequestMapping(path = "/api/ticket/")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketDAO ticketDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	
	@GetMapping(path="/", produces = "application/json")
	public ResponseEntity<ArrayList<Ticket>> getAll(){
		return new ResponseEntity<ArrayList<Ticket>>(ticketService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(path="/user/",produces="application/json")
	public ResponseEntity<ArrayList<Ticket>> getAllByUser(HttpServletRequest request){
		User user = userDAO.findOneByEmailAndIsActive((String)request.getSession().getAttribute("email"),true);
		
		if(user == null) //ako ne postoji korisnik u bazi vraca se BAD_REQUEST
			return new ResponseEntity<ArrayList<Ticket>>(new ArrayList<Ticket>(), HttpStatus.BAD_REQUEST);

		ArrayList<Ticket> tickets=ticketDAO.findAllByUser_Email((String)request.getSession().getAttribute("email"));
		return new ResponseEntity<ArrayList<Ticket>>(tickets, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(path="/",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Ticket recObjTicket, HttpServletRequest request){
		recObjTicket.setUser(new User((String)request.getSession().getAttribute("email")));
		String response=ticketService.save(recObjTicket);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
}
