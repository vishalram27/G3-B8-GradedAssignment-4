package com.greatlearning.employee.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.employee.ems.model.User;
import com.greatlearning.employee.ems.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;

	@Override
	public User createUser(User user) {
		return repository.save(user);
	}

}
