package com.lanaco.mentor.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.http.*;

import com.lanaco.mentor.model.Destination;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.AirplaneService;
import com.lanaco.mentor.service.SupervisorService;
import com.lanaco.mentor.service.UserService;
import com.lanaco.mentor.testutil.SteekillerTestUtil;

@RestController
@RequestMapping(path = "/supervisor")
public class SupervisorController {
	
	@Autowired
	private SupervisorService supervisorService;
	
	
	
	
}
