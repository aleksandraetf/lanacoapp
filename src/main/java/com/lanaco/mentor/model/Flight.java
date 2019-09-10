package com.lanaco.mentor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Integer seatsReserved;
	
	@Column(nullable = false)
	private Date flightDate;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name="airCompany", referencedColumnName="id", nullable=false)
	private AirCompany airCompany;
	
	@ManyToOne
	@JoinColumn(name="destination", referencedColumnName="id", nullable=false)
	private Destination destination;
	
	@ManyToOne
	@JoinColumn(name="airplane", referencedColumnName="id", nullable=false)
	private Airplane airplane;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSeatsReserved() {
		return seatsReserved;
	}

	public void setSeatsReserved(Integer seatsReserved) {
		this.seatsReserved = seatsReserved;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public AirCompany getAirCompany() {
		return airCompany;
	}

	public void setAirCompany(AirCompany airCompany) {
		this.airCompany = airCompany;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
}
