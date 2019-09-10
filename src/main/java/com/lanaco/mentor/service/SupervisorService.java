package com.lanaco.mentor.service;

import com.lanaco.mentor.model.Supervisor;

public interface SupervisorService extends GenericServiceInterface<Supervisor>{

	public Supervisor findOneByUsernameAndPassword(String username, String password);

}
