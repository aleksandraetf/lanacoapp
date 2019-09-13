package com.lanaco.mentor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Airplane {
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("brand")
	@Column(nullable = false)
	private String brand;
	
	
	@JsonProperty("seats")
	@Column(nullable = false)
	private Integer seats;
	
	@JsonProperty("isActive")
	@Column(nullable=false)
	private Boolean isActive;
	
	
	public Airplane() {
		
	}
	
	
	public Airplane(String brand, Integer seats,Boolean active) {
		this.brand = brand;
		this.seats = seats;
		this.isActive=active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	
	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
