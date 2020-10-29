package com.example.Springmvcthymeleaf.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public String viewCompanyPage(Model model, @PathVariable(name = "pageNum") int pageNum,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir) {

		// Chuc nang paging and sorting
		if (sortField == null) {
			sortField = "id";
		}
		if (sortDir == null) {
			sortDir = "asc";
		}
		Page<Company> page = companyService.pagingAndSorting(pageNum, sortField, sortDir);
		List<CompanyDTO> companies1 = page.getContent().stream().map(this::convertToCompanyDTO)
				.collect(Collectors.toList());
		;

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

	@RequestMapping("/add")
	public String showNewCompanyPage(Model model) {
		CompanyDTO companyDTO = new CompanyDTO();
		model.addAttribute("companyDTO", companyDTO);

		return "company/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCompany(@Valid @ModelAttribute("companyDTO") CompanyDTO companyDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			if (companyDTO.getId() != null) {
				return "company/edit_product";
			} else {
				return "company/add";
			}
		} else {
			Company company = convertToEntity(companyDTO);
			companyService.save(company);

			return "redirect:/company";
		}

	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditCompanyPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("company/edit_product");
		Company company = companyService.find(id);
		CompanyDTO companyDTO = convertToCompanyDTO(company);
		mav.addObject("companyDTO", companyDTO);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		companyService.delete(id);
		return "redirect:/company";
	}

	@RequestMapping("/detail/{id}")
	public String viewDetailCompanyPage(@PathVariable(name = "id") int id, Model model) {
		Company company = companyService.find(id);
		CompanyDTO companyDTO = convertToCompanyDTO(company);
		model.addAttribute("company", companyDTO);

		return "company/detail";
	}

	// Feature search
	@RequestMapping("/search")
	public String search(Model model, @Param("keyword") String keyword) {
		// 
		List<Company> companies = companyService.searchByName(keyword);
		List<CompanyDTO> companyDTOs = companies.stream().map(this::convertToCompanyDTO)
				.collect(Collectors.toList());
		
		model.addAttribute("companies", companyDTOs);
		model.addAttribute("keyword", keyword);
		model.addAttribute("totalItems", companies.size());
		
		return "company/search";
	}

	private CompanyDTO convertToCompanyDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		companyDTO.setLocation(company.getLocation());
		companyDTO.setEmployeeNumber(company.getEmployeeNumber());

		return companyDTO;
	}

	private Company convertToEntity(CompanyDTO companyDTO) {
		Company company = new Company();
		if (companyDTO.getId() != null) {
			company.setId(companyDTO.getId());
		}
		company.setName(companyDTO.getName());
		company.setLocation(companyDTO.getLocation());
		company.setEmployeeNumber(companyDTO.getEmployeeNumber());

		return company;
	}

}
