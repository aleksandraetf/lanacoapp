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

import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.service.AdministratorService;
import com.lanaco.mentor.service.DestinationService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/administrator")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<Administrator>> getAll(){
		return new ResponseEntity<ArrayList<Administrator>>(administratorService.getAll(), HttpStatus.OK);
	}
	
	
	@PostMapping(path="/add",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Administrator recObjDestination, HttpServletRequest request){
		String response=administratorService.save(recObjDestination);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping(path="/edit",produces="application/json")
	public ResponseEntity<String> edit(@RequestBody  Administrator recObjDestination, HttpServletRequest request){
		String response=administratorService.edit(recObjDestination);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/delete",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  String username, HttpServletRequest request){
		String response=administratorService.flagNotActive(username);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
}
