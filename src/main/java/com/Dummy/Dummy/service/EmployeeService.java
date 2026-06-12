package com.Dummy.Dummy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dummy.Dummy.Repository.EmployeeRepository;
import com.Dummy.Dummy.beans.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public Employee save(Employee employee) {
		return repository.save(employee);
	}

	public List<Employee> getAll() {
		return repository.findAll();
	}

	public Employee getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Employee update(Long id, Employee employee) {

		Employee dbEmployee = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee Not Found"));

		dbEmployee.setName(employee.getName());
		dbEmployee.setEmail(employee.getEmail());

		return repository.save(dbEmployee);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
