package com.example.Springmvcthymeleaf.service;

import java.util.*;

import com.example.Springmvcthymeleaf.entity.Company;

public interface CompanyService {

	public List<Company> findAll();
	
	public Company save(Company company);
	
	public List<Company> paging(int pageNumber, int pageSize);
	
	public List<Company> searchByName(String name);
	

}
