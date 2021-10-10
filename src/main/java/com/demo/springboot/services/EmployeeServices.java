package com.demo.springboot.services;
import java.util.List;

import javax.transaction.Transactional;

import com.demo.springboot.model.Employee;
import com.demo.springboot.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeServices {
    @Autowired
    private EmployeeRepository repo;

    public List<Employee> getAllEmployees(){
		return repo.findAll();
	}

    public Employee createEmployee(Employee employee) {
		return repo.save(employee);
	}

    public Employee getById(Long id){
        return repo.findById(id).get();
    }

    public Employee updateEmployee(Long id, Employee employeeDetails){
        Employee employee = repo.findById(id).get();
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = repo.save(employee);
        return updatedEmployee;
    }

}
