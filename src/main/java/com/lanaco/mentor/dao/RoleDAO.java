package com.lanaco.mentor.dao;

import org.springframework.data.repository.CrudRepository;

import com.lanaco.mentor.model.Role;


public interface RoleDAO extends CrudRepository<Role, Long> {

	public Role findByRole(String role);

}
