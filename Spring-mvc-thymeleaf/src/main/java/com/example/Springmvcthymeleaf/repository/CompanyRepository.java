package com.example.Springmvcthymeleaf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.Springmvcthymeleaf.entity.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer>{

	
	@Query("SELECT c FROM Company c WHERE c.name LIKE %?1%")
	public List<Company> searchByName(String name);
}
