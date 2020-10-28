package com.example.Springmvcthymeleaf.controller;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Springmvcthymeleaf.entity.Company;
import com.example.Springmvcthymeleaf.service.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	
	final CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping()
	public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
		List<Company> companies = companyService.findAll();
		model.addAttribute("companies", companies);
		
		// Chuc nang paging
		List<Company> companies1 = companyService.paging(0, 1);
		
		
		// Chuc nang search
		List<Company> companies2 = companyService.searchByName("A");
		for(Company co : companies2) {
			System.out.println("===================== : ID = "+co.getId()+"========== Name: "+co.getName());
		}
		
		return "company/index";
	}
	

	
	
}
