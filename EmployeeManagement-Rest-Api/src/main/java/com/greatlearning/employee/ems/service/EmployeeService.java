package com.greatlearning.employee.ems.service;

import java.util.List;

import com.greatlearning.employee.ems.model.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Employee getEmployeeById(long id);

	Employee updateEmployee(Employee employee, long id);

	void deleteEmployee(long id);

	List<Employee> findByFirstName(String firstName);

	List<Employee> getAllEmployeesSorted(String order);

}
