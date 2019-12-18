package com.lanaco.mentor.controllers;

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

import com.lanaco.mentor.dto.LoginDTO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Aircompany;
import com.lanaco.mentor.service.AdministratorService;

@RestController
@RequestMapping(path = "/api/administrator")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	
	@GetMapping(path="/", produces = "application/json")
	public ResponseEntity<ArrayList<Administrator>> getAll(){
		return new ResponseEntity<ArrayList<Administrator>>(administratorService.getAll(), HttpStatus.OK);
	}
	@GetMapping(path="/aircompany/",produces="application/json")
	public ResponseEntity<Aircompany> getAircompany(HttpServletRequest request){
		System.out.println("Provjeravam kompaniju za administratora:"+request.getSession().getAttribute("email"));
		Aircompany aircompany=(administratorService.findAircompanyByAdministrator((String)request.getSession().getAttribute("email")));
		System.out.println("Njegova kompanije je : "+aircompany.getId()+":"+aircompany.getName());
		return new ResponseEntity<Aircompany>(aircompany,HttpStatus.OK);
	}
	
	
	@PostMapping(path="/",produces="application/json")
	public ResponseEntity<String> save(@RequestBody  Administrator recObjAdmin, HttpServletRequest request){
		String response=administratorService.save(recObjAdmin);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@PutMapping(path="/",produces="application/json")
	public ResponseEntity<String> edit(@RequestBody  Administrator recObjAdmin, HttpServletRequest request){
		String response=administratorService.edit(recObjAdmin);
		if (response.contains("Fail")) {
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		} else if (response.contains("Exception")) {
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		}
	}
	
	@DeleteMapping(path="/",produces="application/json")
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
