package com.lanaco.mentor.controllers;

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

import com.lanaco.mentor.model.Airplane;
import com.lanaco.mentor.service.AirplaneService;

@RestController
@RequestMapping(path = "/api/airplane")
public class AirplaneController {
	
	@Autowired
	private AirplaneService airplaneService;
	
	
	@GetMapping(path="/", produces = "application/json")
	public ResponseEntity<ArrayList<Airplane>> getAll(){
		return new ResponseEntity<ArrayList<Airplane>>(airplaneService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(path="/{brand}",produces="application/json")
	public ResponseEntity<ArrayList<Airplane>> getAllByBrand(@PathParam(value = "brand") String brand){
		return new ResponseEntity<ArrayList<Airplane>>(airplaneService.findAllByBrand(brand), HttpStatus.OK);
	}
	@PostMapping(path="/",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Airplane recObjAirplane, HttpServletRequest request){
		String response=airplaneService.save(recObjAirplane);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping(path="/",produces="application/json")
	public ResponseEntity<String> edit(@RequestBody  Airplane recObjAirplane, HttpServletRequest request){
		String response=airplaneService.edit(recObjAirplane);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  Airplane airplane, HttpServletRequest request){
		String response=airplaneService.flagNotActive(airplane.getBrand());
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
}
