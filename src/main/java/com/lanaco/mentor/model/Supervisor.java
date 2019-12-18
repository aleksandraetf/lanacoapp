package com.lanaco.mentor.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Supervisor {
	
	
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
	
	public String getEmail() {
		return email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToOne
	@JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
	private Role role;

	public Supervisor() {
		
	}

	public Supervisor(String username, String password,String email) {
		this.username = username;
		this.password = password;
		this.email=email;
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
}
