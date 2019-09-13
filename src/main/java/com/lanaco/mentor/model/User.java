package com.lanaco.mentor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class User {
	
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("username")
	@Column(nullable = false, unique=true)
	private String username;
	
	@JsonProperty("password")
	@Column(nullable = false)
	private String password;
	
	@JsonProperty("email")
	@Column(nullable = false)
	private String email;
	
	@JsonProperty("isActive")
	@Column(nullable = false)
	private Boolean isActive;
	
	public User() {
		
	}

	public User(String username, String password, String email, boolean b) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isActive = b;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
