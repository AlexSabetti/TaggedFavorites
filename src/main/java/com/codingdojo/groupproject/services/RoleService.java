package com.codingdojo.groupproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.groupproject.models.Role;
import com.codingdojo.groupproject.repositories.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public void initRoles() {
		roleRepository.save(new Role("ROLE_ADMIN"));
		roleRepository.save(new Role("ROLE_USER"));
	}
}
