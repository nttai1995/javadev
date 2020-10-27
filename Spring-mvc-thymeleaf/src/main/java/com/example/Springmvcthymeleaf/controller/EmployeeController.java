package com.example.Springmvcthymeleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Springmvcthymeleaf.entity.Employee;
import com.example.Springmvcthymeleaf.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	final EmployeeRepository employeeRepository;
	
	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@GetMapping()
	public String getEmployee(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
		List<Employee> employees = employeeRepository.findAll();
		model.addAttribute("employees", employees);
		return "greeting";
	}
}
