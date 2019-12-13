package com.lanaco.mentor.service;

import com.lanaco.mentor.model.Destination;

public interface DestinationService extends GenericServiceInterface<Destination>{

	String flagNotActive(String recObj);

}
