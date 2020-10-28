package com.example.Springmvcthymeleaf.controller;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String viewCompanyHomePage(Model model) {
	    return viewCompanyPage(model, 0);
	}

	
	@RequestMapping("/page/{pageNum}")
	public String viewCompanyPage(Model model, @PathVariable(name = "pageNum") int pageNum) {
		// Chuc nang list
		/*List<Company> companies = companyService.findAll();
		model.addAttribute("companies", companies); */
		
		// Chuc nang paging
		Page<Company> page = companyService.paging(pageNum);
		List<Company> companies1 = page.getContent();
		
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages() - 1);
	    model.addAttribute("totalItems", page.getTotalElements());
	    model.addAttribute("companies", companies1);
		

		
		return "company/index";
	}
	

	
	
}
