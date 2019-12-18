package com.lanaco.mentor.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

import com.lanaco.mentor.dao.AdministratorDAO;
import com.lanaco.mentor.dao.RoleDAO;
import com.lanaco.mentor.dao.SupervisorDAO;
import com.lanaco.mentor.dao.UserDAO;
import com.lanaco.mentor.dto.LoginDTO;
import com.lanaco.mentor.model.Administrator;
import com.lanaco.mentor.model.Role;
import com.lanaco.mentor.model.Supervisor;
import com.lanaco.mentor.model.User;
import com.lanaco.mentor.service.UserService;


@RestController
@RequestMapping(path = "")
public class LoginController {
	@Autowired
	UserService userService;

	@Autowired
	UserDAO userDAO;
	@Autowired
	AdministratorDAO adminDAO;
	@Autowired
	SupervisorDAO supervisorDAO;
	@Autowired
	RoleDAO roleDAO;
	

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@RequestMapping(value="/login/user/",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> userLogIn(@RequestBody LoginDTO reqUser, HttpServletRequest request) {
		System.out.println("Stize post na login !!! :"+reqUser.getEmail()+reqUser.getPassword()+reqUser.getRequestedRole());
		if (reqUser == null || reqUser.getEmail() == null || reqUser.getEmail().trim().equals("")
				|| reqUser.getPassword() == null || reqUser.getPassword().trim().equals("")
				|| reqUser.getRequestedRole()==null) {
			return new ResponseEntity<String>("Fail, Login Data incomplete", HttpStatus.BAD_REQUEST);
		}
		try {
			request.login(reqUser.getEmail(), reqUser.getPassword());
			request.getSession().setAttribute("email",reqUser.getEmail());
			User user=userDAO.findOneByEmailAndIsActive(reqUser.getEmail(), true);
			System.out.println("Role:"+reqUser.getRequestedRole());
			return new ResponseEntity<String>("OK", HttpStatus.OK);
				
		}
		catch(javax.servlet.ServletException ex) {
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Already logged!", HttpStatus.BAD_REQUEST);
		}
		
	}
	@RequestMapping(value="/login/administrator/",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> administratorLogIn(@RequestBody LoginDTO reqUser, HttpServletRequest request) {
		System.out.println("Stize post na login !!! :"+reqUser.getEmail()+reqUser.getPassword()+reqUser.getRequestedRole());
		if (reqUser == null || reqUser.getEmail() == null || reqUser.getEmail().trim().equals("")
				|| reqUser.getPassword() == null || reqUser.getPassword().trim().equals("")
				|| reqUser.getRequestedRole()==null) {
			System.out.println("0");
			return new ResponseEntity<String>("Fail, Login Data incomplete",HttpStatus.ACCEPTED);
		}
		try {
			Administrator admin=adminDAO.findOneByEmailAndIsActive(reqUser.getEmail(),true);
			 if(admin == null)
				return new ResponseEntity<String>("Ne postoji dati administrator!", HttpStatus.FORBIDDEN);

			request.login(reqUser.getEmail(), reqUser.getPassword());
			request.getSession().setAttribute("email",reqUser.getEmail());
			return new ResponseEntity<String>("Ok",HttpStatus.OK);
		}
		catch(javax.servlet.ServletException ex) {
			System.out.println("1");
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			System.out.println("2");
			e.printStackTrace();
			return new ResponseEntity<String>("Already logged!", HttpStatus.BAD_REQUEST);
		}

	}
	@RequestMapping(value="/login/supervisor/",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> supervisorLogIn(@RequestBody LoginDTO reqUser, HttpServletRequest request) {
		System.out.println("Stize post na login !!! :"+reqUser.getEmail()+reqUser.getPassword()+reqUser.getRequestedRole());
		if (reqUser == null || reqUser.getEmail() == null || reqUser.getEmail().trim().equals("")
				|| reqUser.getPassword() == null || reqUser.getPassword().trim().equals("")
				|| reqUser.getRequestedRole()==null) {
			return new ResponseEntity<String>("Fail, Login Data incomplete", HttpStatus.BAD_REQUEST);
		}
		try {
			Supervisor supervisor=supervisorDAO.findOneByEmail(reqUser.getEmail());
			if(supervisor==null)
				return new ResponseEntity<String>("NO", HttpStatus.BAD_REQUEST);
			request.login(reqUser.getEmail(),reqUser.getPassword());
			request.getSession().setAttribute("email",reqUser.getEmail());
		}
		catch(javax.servlet.ServletException ex) {
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Already logged!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Not valid role.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/isAuthenticated/")
	public ResponseEntity<String> userLogIn(HttpServletRequest request) {
		System.out.println("Uslo u is auth");
		System.out.println("Gospodine : "+request.getSession().getAttribute("email"));
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
		if (reqUser == null || reqUser.getEmail() == null || reqUser.getPassword() == null) {
			return new ResponseEntity<String>("Fail, Registration Data incomplete", HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("Register : "+reqUser.getEmail()+","+reqUser.getPassword());
		User user = userDAO.findOneByEmailAndIsActive(reqUser.getEmail(),true);
		
		if(user!=null)
			return new ResponseEntity<String>("Fail, Email already in use!",HttpStatus.BAD_REQUEST);
		
		user=new User();
		user.setUsername("NeTrebaNamVise");
		user.setPassword(bCryptPasswordEncoder.encode(reqUser.getPassword()));
		user.setEmail(reqUser.getEmail());
		user.setIsActive(true);
		
		
		Role userRole = roleDAO.findByRole("USER");
		user.setRole(userRole);
		try {
			userDAO.save(user);
		} catch (Exception e) {
			return new ResponseEntity<String>("User could not be saved!", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("OK", HttpStatus.ACCEPTED);
	}

}
