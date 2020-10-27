package com.example.Springmvcthymeleaf.service;

import java.util.List;

import com.example.Springmvcthymeleaf.DTO.EmployeeDTO;
import org.springframework.data.domain.Sort;

public interface EmployeeService {
	public List<EmployeeDTO> getAllEmployees(String sortBy);
}
