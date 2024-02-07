package com.greatlearning.employee.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greatlearning.employee.ems.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select u from User u where u.username = ?1")
	public User getUserByUsername(@Param("username") String userName);

}