package com.lanaco.mentor.controllers;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lanaco.mentor.dao.RoleDAO;
import com.lanaco.mentor.dao.UserDAO;
import com.lanaco.mentor.model.Role;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.UserService;

import jdk.jfr.ContentType;


@RestController
@RequestMapping(path = "")
public class LoginController {
	@Autowired
	UserService userService;

	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value="/login/",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> userLogIn(@RequestBody User reqUser, HttpServletRequest request) {
		System.out.println("Uslo vuj"+reqUser.getEmail()+reqUser.getPassword());
		if (reqUser == null || reqUser.getEmail() == null || reqUser.getEmail().trim().equals("")
				|| reqUser.getPassword() == null || reqUser.getPassword().trim().equals("")) {
			return new ResponseEntity<String>("Fail, Login Data incomplete", HttpStatus.BAD_REQUEST);
		}
		try {
			request.login(reqUser.getEmail(), reqUser.getPassword());
		}
		catch(javax.servlet.ServletException ex) {
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Already logged!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@GetMapping(value = "/isAuthenticated/")
	public ResponseEntity<String> userLogIn(HttpServletRequest request) {
		System.out.println("Uslo u is auth");
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				request.getUserPrincipal()!=null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() == true) {
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("NO", HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(value = "/logout/")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		try {
			System.out.println("Izloguj mee:"+request.getUserPrincipal());
			request.logout();
			
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("FAIL", HttpStatus.FORBIDDEN);
		}

	}

	@PostMapping(value = "/register/")
	public ResponseEntity<String> userRegister(@RequestBody User reqUser, HttpServletRequest request) {
		if (reqUser == null || reqUser.getEmail() == null || reqUser.getPassword() == null
				|| reqUser.getUsername() == null) {
			return new ResponseEntity<String>("Fail, Registration Data incomplete", HttpStatus.BAD_REQUEST);
		}
		//provjeriti dal vec postoji
		User user = new User();
		user.setPassword(bCryptPasswordEncoder.encode(reqUser.getPassword()));
		user.setUsername(reqUser.getUsername());
		user.setEmail(reqUser.getEmail());
		user.setIsActive(true);
		Role userRole = roleDAO.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		try {
			userDAO.save(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("User could not be saved!", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("OK", HttpStatus.OK);

	}

}
