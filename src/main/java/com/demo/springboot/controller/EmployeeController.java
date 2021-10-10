package com.demo.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springboot.exception.ResourceNotFoundException;
import com.demo.springboot.model.Employee;
import com.demo.springboot.repository.EmployeeRepository;
import com.demo.springboot.services.EmployeeServices;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeServices services;

	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees(){
		try{
			return ResponseEntity.ok(services.getAllEmployees());
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	//create employee rest api
	@PostMapping("/employees")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		try{
			return ResponseEntity.ok(services.createEmployee(employee));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(services.getById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		try {
			return ResponseEntity.ok(services.updateEmployee(id, employeeDetails));
		} catch (Exception e) {
			//TODO: handle exception
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	//delete employee rest API
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
}
