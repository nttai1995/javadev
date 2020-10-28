package com.example.Springmvcthymeleaf.service;

import java.util.*;

import org.springframework.data.domain.Page;

import com.example.Springmvcthymeleaf.entity.Company;

public interface CompanyService {

	public List<Company> findAll();
	
	public Company save(Company company);
	
	public Company find(int id);
	
	public void delete(int id);
	
	public Page<Company> pagingAndSorting(int pageNumber, String sortField, String sortDir);
	
	public List<Company> searchByName(String name);
	

}
