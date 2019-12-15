package com.lanaco.mentor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {

	@JsonProperty("email")
	private String email;
	

	@JsonProperty("password")
	private String password;
	

	@JsonProperty("role")
	private String requestedRole;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRequestedRole() {
		return requestedRole;
	}


	public void setRequestedRole(String requestedRole) {
		this.requestedRole = requestedRole;
	}


	public LoginDTO(String email, String password, String requestedRole) {
		super();
		this.email = email;
		this.password = password;
		this.requestedRole = requestedRole;
	}
	
	
}
