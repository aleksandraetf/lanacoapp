package com.lanaco.mentor.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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

import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	
	
	@GetMapping(path="/all", produces = "application/json")
	public ResponseEntity<ArrayList<String>> getAll(){
		try {
			return new ResponseEntity<ArrayList<String>>(SteekillerTestUtil.readFromFile("test\\user.txt"), HttpStatus.OK);
		}catch(IOException ex) {
			return null;
		}
	}
	
	@PostMapping(headers = { "content-type=application/json" },path = "/add")
	public ResponseEntity<String> save(@RequestBody  String newUser, HttpServletRequest request) {
		try {
			ArrayList<String> fileLines=SteekillerTestUtil.readFromFile("test\\user.txt");
			fileLines.add(newUser);
			SteekillerTestUtil.writeToFile("test\\user.txt",fileLines);
			return new ResponseEntity<String>("Uspjesno dodat user :)", HttpStatus.OK);
		}catch(IOException ex) {
			return new ResponseEntity<String>("Desila se neka greska! Neuspjesno dodavanje)", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(headers = { "content-type=application/json" },path = "/edit")
	public ResponseEntity<String> edit(@RequestBody String editedUser, HttpServletRequest request) {
		try {
			//mrsko mi praviti pravi testni edit pa neka bude isto kao dodavanje :)
			ArrayList<String> fileLines=SteekillerTestUtil.readFromFile("test\\user.txt");
			fileLines.add(editedUser);
			SteekillerTestUtil.writeToFile("test\\user.txt",fileLines);
			return new ResponseEntity<String>("Uspjesno modifikovan user :)", HttpStatus.OK);
		}catch(IOException ex) {
			return new ResponseEntity<String>("Desila se neka greska! Neuspjesno modifikovanje)", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//brise se preko url-a cisto da i to testiram :)
	@DeleteMapping(value = "/delete/{username}", headers = { "content-type=application/json" })
	public ResponseEntity<String> delete(@PathVariable("username") String username, HttpServletRequest request) {
		try {
			//ne sudite kodu za testiranje :))))
			ArrayList<String> fileLines=SteekillerTestUtil.readFromFile("test\\user.txt");
			fileLines.remove(username);
			SteekillerTestUtil.writeToFile("test\\user.txt",fileLines);
			return new ResponseEntity<String>("Uspjesno obrisan user :)", HttpStatus.OK);
		}catch(IOException ex) {
			return new ResponseEntity<String>("Desila se neka greska! Neuspjesno brisanje)", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
