package com.example.Springmvcthymeleaf.DTO;

import javax.validation.constraints.*;

public class CompanyDTO {

	private Integer id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String location;
	
	@Min(value = 10)
	private String employeeNumber;
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	
}
