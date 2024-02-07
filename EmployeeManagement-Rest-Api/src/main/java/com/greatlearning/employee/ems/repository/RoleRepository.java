package com.greatlearning.employee.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.employee.ems.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
