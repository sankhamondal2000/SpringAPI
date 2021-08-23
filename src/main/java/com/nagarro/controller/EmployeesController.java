package com.nagarro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.entities.Employee;
import com.nagarro.repository.EmployeeRepository;

@RestController
@RequestMapping("/employee-api")
public class EmployeesController {

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getEmployees()
	{
		return employeeRepository.findAll();
	}
	
	@GetMapping(path="/employees/{id}")
	public ResponseEntity<? extends Object> getEmployeeById(@PathVariable(name="id") Long id)
	{
		Optional<Employee> employee = null;
		
		try {
			employee = employeeRepository.findById(id);
			System.out.print("Done");
		} catch (Exception e) {
			return ResponseEntity.of(Optional.of(employee));
		}
		if(employee!=null)
		{
			return ResponseEntity.ok().body(employee);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/employees/{id}")
	public Employee updateEmployee(@PathVariable(name="id") Long id, @RequestBody Employee employeeDetails)
	{
		Employee existingEmployee=employeeRepository.getById(id);
		existingEmployee.setEmployeeName(employeeDetails.getEmployeeName());
		existingEmployee.setLocation(employeeDetails.getLocation());
		existingEmployee.setEmail(employeeDetails.getEmail());
		existingEmployee.setDob(employeeDetails.getDob());
		return employeeRepository.save(existingEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public boolean deleteEmployee(@PathVariable(name="id") Long id)
	{
		employeeRepository.deleteById(id);
		return true;
	}
	
}
