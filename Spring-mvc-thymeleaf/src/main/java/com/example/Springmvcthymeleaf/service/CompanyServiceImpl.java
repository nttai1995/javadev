package com.example.Springmvcthymeleaf.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Springmvcthymeleaf.entity.Company;
import com.example.Springmvcthymeleaf.repository.CompanyRepository;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	
	
	@Override
	public List<Company> findAll() {
		
		return (List<Company>) companyRepository.findAll();
	}

	@Override
	public Company save(Company company) {
		return companyRepository.save(company);
		
	}
	
	@Override
	public List<Company> paging(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Company> page = companyRepository.findAll(pageable);
		
		List<Company> listProducts = page.getContent();
		
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		System.out.println("totalItems: "+ totalItems);
		System.out.println("totalPages: "+totalPages);
		return listProducts;
	}

	@Override
	public List<Company> searchByName(String name) {
		
		return companyRepository.searchByName(name);
	}




}
