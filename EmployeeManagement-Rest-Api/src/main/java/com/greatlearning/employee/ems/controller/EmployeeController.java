package com.greatlearning.employee.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employee.ems.exception.ResourceNotFoundException;
import com.greatlearning.employee.ems.model.Employee;
import com.greatlearning.employee.ems.model.Role;
import com.greatlearning.employee.ems.model.User;
import com.greatlearning.employee.ems.repository.EmployeeRepository;
import com.greatlearning.employee.ems.service.EmployeeService;
import com.greatlearning.employee.ems.service.RoleService;
import com.greatlearning.employee.ems.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@PostMapping("/addRole")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> addRole(@RequestBody Role role) {
		Role savedRole = roleService.createRole(role);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
	}

	@PostMapping("/addUser")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		for (Role role : user.getRoles()) {
			roleService.createRole(role);
		}

		userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@GetMapping("/list")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@PostMapping("/new")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@GetMapping("/view/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
		return ResponseEntity.ok(employee);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>("Employee deleted successfully!.", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Employee>> getEmployeeByFirstName(@RequestParam String firstName) {
		return new ResponseEntity<>(employeeRepository.findByFirstName(firstName), HttpStatus.OK);
	}

	@GetMapping("/sort")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Employee> getAllEmployeesSorted(@RequestParam String order) {
		return employeeService.getAllEmployeesSorted(order);
	}

}