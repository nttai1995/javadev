package com.example.Springmvcthymeleaf.service;

import java.util.*;

import org.springframework.data.domain.Page;

import com.example.Springmvcthymeleaf.entity.Company;

public interface CompanyService {

	public List<Company> findAll();
	
	public Company save(Company company);
	
	public Page<Company> paging(int pageNumber);
	
	public List<Company> searchByName(String name);
	

}
