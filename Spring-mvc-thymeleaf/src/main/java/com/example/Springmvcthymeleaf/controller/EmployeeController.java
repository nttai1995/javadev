package com.example.Springmvcthymeleaf.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Springmvcthymeleaf.DTO.EmployeeDTO;
import com.example.Springmvcthymeleaf.entity.Company;
import com.example.Springmvcthymeleaf.entity.Employee;
import com.example.Springmvcthymeleaf.service.CompanyService;
import com.example.Springmvcthymeleaf.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	final EmployeeService employeeService;
	final CompanyService companyService;
	
	public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
		this.employeeService = employeeService;
		this.companyService = companyService;
	}
	
	@GetMapping("")
	public String getListEmployee(
			@RequestParam(name = "sortBy", required = false, defaultValue = "email") String sortBy,
			@RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			Model model) 
	{
		Page<EmployeeDTO> employees = employeeService.getAllEmployees(sortBy, pageNo - 1, pageSize);
		model.addAttribute("employees", employees);
		List<Company> companies = companyService.findAll().stream().distinct().collect(Collectors.toList());
		model.addAttribute("companies", companies);
		List<String> fisrtNameList = employees.stream().map(emp -> emp.getFirstName()).distinct().collect(Collectors.toList());
		model.addAttribute("fisrtNameList", fisrtNameList);
		int totalPages = employees.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers",pageNumbers);
		}
		model.addAttribute("employee", new Employee());
		return "employee/index";
	}
	
	@GetMapping("/search")
	public String findEmployeeByFirstName(
			@RequestParam(name = "firstName", required = true) String firstName,
			Model model) 
	{
		Pageable paging = PageRequest.of(0, 10);
		List<EmployeeDTO> emps =  employeeService.findByFirstName(firstName);
		Page<EmployeeDTO> employees = new PageImpl<>(emps, paging, emps.size());

		model.addAttribute("employees", employees);
		int totalPages = employees.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers",pageNumbers);
		}
		model.addAttribute("employee", new Employee());
		return "employee/index";
	}
	
	@GetMapping("/detail")
	public String findOneEmployee(
			@RequestParam(name = "empId", required = true) Integer empId,
			Model model) 
	{
		Employee employee = employeeService.findOne(empId);
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	@PostMapping("")
	public String addEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
		List<Company> companies = companyService.findAll().stream().distinct().collect(Collectors.toList());
		if (bindingResult.hasErrors()) {
			model.addAttribute("companies", companies);
			return "employee/add";
		}
		else {
			try {
				employeeService.saveEmployee(employee);
			} catch (DataIntegrityViolationException e) {
				if(e.getMessage().contains("employees.email")) {
					bindingResult.rejectValue("email", "error.user", "An account already exists for this email.");
					model.addAttribute("companies", companies);
					return "employee/add";
				}else {
					throw e;
				}
			}
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/edit")
	public String updateEmployee(
			@RequestParam(name = "empId", required = true) Integer empId,
			Model model) 
	{
		Employee employee = employeeService.findOne(empId);
		model.addAttribute("employee", employee);
		List<Company> companies = companyService.findAll().stream().distinct().collect(Collectors.toList());
		model.addAttribute("companies", companies);
		return "employee/edit";
	}
	
	@PostMapping("/delete")
	public String deleteEmployee(@RequestParam(name = "empId", required = true) Integer empId, Model model) {
		employeeService.deleteEmployee(empId);
		return "redirect:/employee";
	}
	
	
}
