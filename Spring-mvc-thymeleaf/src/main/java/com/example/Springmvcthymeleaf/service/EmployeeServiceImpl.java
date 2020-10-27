package com.example.Springmvcthymeleaf.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Springmvcthymeleaf.DTO.EmployeeDTO;
import com.example.Springmvcthymeleaf.entity.Employee;
import com.example.Springmvcthymeleaf.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<EmployeeDTO> getAllEmployees(String sortBy) {
		return ((List<Employee>) employeeRepository
				.findAll(Sort.by(sortBy)))
				.stream()
				.map(this::convertToEmployeeDTO)
				.collect(Collectors.toList());
	}
	
	private EmployeeDTO convertToEmployeeDTO(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeNumber(employee.getEmployeeNumber());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setFirstName(employee.getFirstName());
		employeeDTO.setLastName(employee.getLastName());
		return employeeDTO;
	}

}
