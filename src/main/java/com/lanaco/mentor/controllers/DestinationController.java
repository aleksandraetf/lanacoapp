package com.lanaco.mentor.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanaco.mentor.dao.DestinationDAO;
import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.service.DestinationService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;


@RestController
@RequestMapping(path = "/destination")
public class DestinationController {

	@Autowired
	DestinationService destinationService;
	
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<Destination>> getAll(){
		return new ResponseEntity<ArrayList<Destination>>(destinationService.getAll(), HttpStatus.OK);
	}
	
}
