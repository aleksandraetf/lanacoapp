package com.lanaco.mentor.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/flight")
public class FlightController {
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<String>> getAll(){
		try {
			return new ResponseEntity<ArrayList<String>>(SteekillerTestUtil.readFromFile("test\\flight.txt"), HttpStatus.OK);
		}catch(IOException ex) {
			return null;
		}
	}
	
}
