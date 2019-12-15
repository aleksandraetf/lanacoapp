package com.lanaco.mentor.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

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

import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.service.AirCompanyService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(path = "/api/aircompany")
public class AirCompanyController {

	@Autowired
	private AirCompanyService airCompanyService;
	
	
	@GetMapping(path="/", produces = "application/json")
	public ResponseEntity<ArrayList<Aircompany>> getAll(){
		return new ResponseEntity<ArrayList<Aircompany>>(airCompanyService.getAll(), HttpStatus.OK);
	}
	
	
	@PostMapping(path="/",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Aircompany recObjAircompany, HttpServletRequest request){
		String response=airCompanyService.save(recObjAircompany);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	
	@PutMapping(path="/",produces="application/json")
	public ResponseEntity<String> edit(@RequestBody  Aircompany recObjAircompany, 
			HttpServletRequest request){
		String response=airCompanyService.edit(recObjAircompany);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  Aircompany aircompany, HttpServletRequest request){
		System.out.println(aircompany.getName());
		//log.info("Poruka");
		String response=airCompanyService.flagNotActive(aircompany.getName());
		if (response.contains("Fail")) {
			System.out.println(response);
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			System.out.println(response);
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			System.out.println(response);
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
}
