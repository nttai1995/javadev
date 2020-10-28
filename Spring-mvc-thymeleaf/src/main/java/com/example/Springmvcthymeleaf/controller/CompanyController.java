package com.example.Springmvcthymeleaf.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Springmvcthymeleaf.DTO.CompanyDTO;
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
	    return viewCompanyPage(model, 0, "id", "asc");
	}

	
	@RequestMapping("/page/{pageNum}")
	public String viewCompanyPage(Model model, 
			@PathVariable(name = "pageNum") int pageNum,
			@Param("sortField") String sortField,
			@Param("sortDir") String sortDir) {
		// Chuc nang list
		/*List<Company> companies = companyService.findAll();
		model.addAttribute("companies", companies); */
		
		// Chuc nang paging and sorting
		if(sortField == null) {
			sortField = "id";
		}
		if(sortDir == null) {
			sortDir = "asc";
		}
		Page<Company> page = companyService.pagingAndSorting(pageNum, sortField, sortDir);
		List<CompanyDTO> companies1 = page.getContent().stream()
		        .map(this::convertToCompanyDTO)
		        .collect(Collectors.toList());;
		
		model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPages", page.getTotalPages() - 1);
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    //
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    
	    model.addAttribute("companies", companies1);
		
		return "company/index";
	}
	
	private CompanyDTO convertToCompanyDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		companyDTO.setLocation(company.getLocation());
		
		return companyDTO;
	}

	
	
}
