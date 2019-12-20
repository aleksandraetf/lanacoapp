package com.lanaco.mentor.controllers;

import java.io.IOException;
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

import com.lanaco.mentor.dao.FlightDAO;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.service.DestinationService;
import com.lanaco.mentor.service.FlightService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/api/flight")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private FlightDAO flightDAO;
	
	
	@GetMapping(path="/", produces = "application/json")
	public ResponseEntity<ArrayList<Flight>> getAll(){
		return new ResponseEntity<ArrayList<Flight>>(flightService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(path="/admin/",produces="application/json")
	public ResponseEntity<ArrayList<Flight>> getAllByAdministrator(HttpServletRequest request){
		ArrayList<Flight> response=flightService.findAllByAdministratorEmail((String)request.getSession().getAttribute("email"));
		if (response==null) {
			return new ResponseEntity<ArrayList<Flight>>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<ArrayList<Flight>>(response,HttpStatus.ACCEPTED);
	}
	
	@PostMapping(path="/",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Flight recObjFlight, HttpServletRequest request){
		String response=flightService.save(recObjFlight);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	
	
	@PutMapping(path="/",produces="application/json")
	public ResponseEntity<String> edit(@RequestBody  Flight recObjFlight, HttpServletRequest request){
		String response=flightService.edit(recObjFlight);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  Flight recObjId, HttpServletRequest request){
		String response=flightService.flagNotActive(recObjId.getId());
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
}
