package com.lanaco.mentor.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.net.HttpURLConnection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.http.*;
import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/supervisor")
public class SupervisorController {
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<String>> getAll(){
		try {
			return new ResponseEntity<ArrayList<String>>(SteekillerTestUtil.readFromFile("test\\supervisor.txt"), HttpStatus.OK);
		}catch(IOException ex) {
			return null;
		}
	}
	
	
}
