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

import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.service.AdministratorService;
import com.lanaco.mentor.service.AirCompanyService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;


@RestController
@RequestMapping(path = "/aircompany")
public class AirCompanyController {

	@Autowired
	private AirCompanyService airCompanyService;
	
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<Aircompany>> getAll(){
		return new ResponseEntity<ArrayList<Aircompany>>(airCompanyService.getAll(), HttpStatus.OK);
	}
	
	
	@PostMapping(path="/new",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Aircompany recObjDestination, HttpServletRequest request){
		String response=airCompanyService.save(recObjDestination);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping(path="/edit/{id}",produces="application/json")
	public ResponseEntity<String> edit(@PathParam(value = "id") Integer id,
			@RequestBody  Aircompany recObjAirCompany, HttpServletRequest request){
		String response=airCompanyService.edit(recObjAirCompany);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/delete",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  String name, HttpServletRequest request){
		String response=airCompanyService.flagNotActive(name);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
}
