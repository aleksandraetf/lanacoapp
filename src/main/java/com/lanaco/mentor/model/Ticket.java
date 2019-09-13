package com.lanaco.mentor.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Ticket {

	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("numberOfTickets")
	@Column(nullable = false)
	private Integer numberOfTickets;


	@ManyToOne
	@JoinColumn(name="user", referencedColumnName="id", nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="flight", referencedColumnName="id", nullable=false)
	private Flight flight;
	
	
	public Ticket() {
		
	}
	
	public Ticket(Flight flight, User user, Integer numberOfTickets) {
		this.flight = flight;
		this.user=user;
		this.numberOfTickets=numberOfTickets;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

}
