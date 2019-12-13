package com.lanaco.mentor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
public class Administrator {


	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("username")
	@Column(nullable = false)
	private String username;
	
	@JsonProperty("password")
	@Column(nullable = false)
	private String password;
	
	@JsonProperty("isActive")
	@Column(nullable = false)
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(referencedColumnName="id", nullable=false)
	private Aircompany airCompany;
	
	public Administrator() {
		
	}
	
	public Administrator(String username, String password, Aircompany aircompany, boolean b) {
		this.username = username;
		this.password = password;
		this.isActive = b;
		this.airCompany = aircompany;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Aircompany getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(Aircompany airCompany) {
		this.airCompany = airCompany;
	}
}
