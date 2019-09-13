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

import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.Flight;
import com.lanaco.mentor.service.DestinationService;
import com.lanaco.mentor.service.FlightService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/flight")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<Flight>> getAll(){
		return new ResponseEntity<ArrayList<Flight>>(flightService.getAll(), HttpStatus.OK);
	}
	
	
	@PostMapping(path="/add",produces="application/json")
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
	
	@PutMapping(path="/edit",produces="application/json")
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
	
	@DeleteMapping(path="/delete",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  Long recObjId, HttpServletRequest request){
		String response=flightService.flagNotActive(recObjId);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
}
