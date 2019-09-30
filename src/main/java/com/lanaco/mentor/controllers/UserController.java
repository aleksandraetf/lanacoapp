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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.UserService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<User>> getAll(){
		return new ResponseEntity<ArrayList<User>>(userService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping(headers = { "content-type=application/json" },path = "/add")
	public ResponseEntity<String> save(@RequestBody  User recObjUser, HttpServletRequest request) {
		String response=userService.save(recObjUser);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping(headers = { "content-type=application/json" },path = "/edit")
	public ResponseEntity<String> edit(@RequestBody User newUser, HttpServletRequest request) {
		
		String response=userService.edit(newUser);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(value = "/delete", headers = { "content-type=application/json" })
	public ResponseEntity<String> delete(@RequestBody String username, HttpServletRequest request) {
		String response=userService.flagNotActive(username);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
}
