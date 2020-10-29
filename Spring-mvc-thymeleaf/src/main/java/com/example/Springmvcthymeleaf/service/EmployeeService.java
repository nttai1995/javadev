package com.example.Springmvcthymeleaf.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.Springmvcthymeleaf.DTO.EmployeeDTO;
import com.example.Springmvcthymeleaf.entity.Employee;

public interface EmployeeService {
	public Page<EmployeeDTO> getAllEmployees(String sortBy, Integer pageNo, Integer pageSize);
	
	public Employee findOne(Integer empId);
	
	public List<EmployeeDTO> findByFirstName(String firstName);
	
	public void saveEmployee(Employee emp);
	
	public void deleteEmployee(Integer empId);
}
