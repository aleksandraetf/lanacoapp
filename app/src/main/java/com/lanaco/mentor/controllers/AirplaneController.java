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
		System.out.println("Eo usao u post.");
		String response=airplaneService.save(recObjAirplane);
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
	
	@PutMapping(path="/",produces="application/json")
	public ResponseEntity<String> edit(@RequestBody  Airplane recObjAirplane, HttpServletRequest request){
		System.out.println(recObjAirplane.getId()+recObjAirplane.getBrand()
			+recObjAirplane.getIsActive()+recObjAirplane.getSeats());
		String response=airplaneService.edit(recObjAirplane);
		if (response.contains("Fail")) {
			System.out.println("1");
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			System.out.println("2");
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			System.out.println("3");
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/",produces="application/json")
	public ResponseEntity<String> flagNotActive(@RequestBody  Airplane airplane, HttpServletRequest request){
		System.out.println(airplane.getBrand());
		String response=airplaneService.flagNotActive(airplane.getBrand());
		if (response.contains("Fail")) {
			System.out.println("1");
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			System.out.println("2");
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			System.out.println("3");
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
}
