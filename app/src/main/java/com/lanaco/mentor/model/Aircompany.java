package com.lanaco.mentor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Aircompany {
	
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty("name")
	@Column(nullable = false, unique=true)
	private String name;
	
	@JsonProperty("isActive")
	@Column(nullable = false)
	private Boolean isActive;
	
	public Aircompany(long id) {
		this.id=id;
	}
	
	public Aircompany(String name) {
		this.name=name;
	}
	
	public Aircompany() {
		
	}
	
	public Aircompany(String name, boolean isActive) {
		super();
		this.name = name;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
