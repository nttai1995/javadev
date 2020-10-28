package com.example.Springmvcthymeleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Springmvcthymeleaf.DTO.EmployeeDTO;
import com.example.Springmvcthymeleaf.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/list")
	public String getEmployeeSortByParam(@RequestParam(name = "sortBy", required = false, defaultValue = "email") String field, Model model) {
		List<EmployeeDTO> employees = employeeService.getAllEmployees(field);
		model.addAttribute("employees", employees);
		return "employee";
	}
}
