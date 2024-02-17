package com.greatlearning.employee.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.employee.ems.model.Role;
import com.greatlearning.employee.ems.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository repository;

	@Override
	public Role createRole(Role role) {
		return repository.save(role);
	}

}
