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

import com.lanaco.mentor.model.Ticket;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.TicketService;
import com.lanaco.mentor.service.UserService;


@RestController
@RequestMapping(path = "/api/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserService userService;
	
	
	//iako nije trazeno ostavicemo mogucnost da supervizor pogleda sve karte u bazi
	//dobro je i za testiranje
	//takodje vrlo lako se moze filtrirati da vraca samo one karte ciji su letovi aktivni
	//mozda smo mogli dodati podatak clan u flight da li je zavrsen pa filtrirati prema tome
	//ili da prema vremenu slijetanja npr ako je null da let jos nije zavrsen
	//zavisi dal nam u bazi vrijeme slijetanja predstavlja planirano vrijeme slijetanja ili stvarno vrijeme slijetanja
	//o tom po tom
	@GetMapping(path="/", produces = "application/json")
	public ResponseEntity<ArrayList<Ticket>> getAll(){
		return new ResponseEntity<ArrayList<Ticket>>(ticketService.getAll(), HttpStatus.OK);
	}
	
	//istorija za jednog usera
	//ostavljamo mogucnost da i supervisor filtrira preko username sa istog servisa
	//nadamo se da ce se autentikacija rijesavati preko nekog @Auth fazona :D
	//pa nece bilo ko moci vidjeti istorijat karata bilo kog drugog korisnika
	@PostMapping(path="/all",produces="application/json")
	public ResponseEntity<ArrayList<Ticket>> getAllByUser(@RequestBody String username){
		System.out.println(username+" username");
		User user = userService.findOneByUsername(username);
		System.out.println(user);
		
		if(user == null) //ako ne postoji korisnik u bazi vraca se BAD_REQUEST
			return new ResponseEntity<ArrayList<Ticket>>(new ArrayList<Ticket>(), HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<ArrayList<Ticket>>(ticketService.findAllByUsername(username), HttpStatus.OK);
	}
	
	@PostMapping(path="/",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Ticket recObjTicket, HttpServletRequest request){
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
