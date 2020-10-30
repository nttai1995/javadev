package com.example.Springmvcthymeleaf.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Springmvcthymeleaf.DTO.EmployeeDTO;
import com.example.Springmvcthymeleaf.entity.Employee;
import com.example.Springmvcthymeleaf.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	final EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public Page<EmployeeDTO> getAllEmployees(String sortBy, Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<EmployeeDTO> pageResult = ( employeeRepository
				.findAll(paging))
				.map(this::convertToEmployeeDTO);
		
		return pageResult;
	}
	
	@Override
	public List<EmployeeDTO> findByFirstName(String firstName) {
		List<EmployeeDTO> pageResult = ( employeeRepository
				.findByFirstName(firstName))
				.stream()
				.map(this::convertToEmployeeDTO)
				.collect(Collectors.toList());
		
		return pageResult;
	}
	
	private EmployeeDTO convertToEmployeeDTO(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmployeeNumber(employee.getEmployeeNumber());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setFirstName(employee.getFirstName());
		employeeDTO.setLastName(employee.getLastName());
		employeeDTO.setCompany(employee.getCompany());
		return employeeDTO;
	}
	
	@Override
	public Employee findOne(Integer empId) {
		
		return employeeRepository.findById(empId).get();
	}

	@Override
	public void saveEmployee(Employee emp) {
		try {
			employeeRepository.save(emp);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteEmployee(Integer empId) {
		try {
			employeeRepository.deleteById(empId);
		} catch (Exception e) {
			throw e;
		}
	}

}
