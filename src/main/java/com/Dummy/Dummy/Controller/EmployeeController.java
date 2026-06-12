package com.Dummy.Dummy.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dummy.Dummy.beans.Employee;
import com.Dummy.Dummy.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@PostMapping("/save")
	public Employee save(@RequestBody Employee employee) {
		System.out.println("in saving employee.............");

		return service.save(employee);
	}

	@GetMapping("/getAll")
	public List<Employee> getAll() {
		System.out.println("in getAll ");
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Employee getById(@PathVariable Long id) {
		if (id == 1)
			throw new RuntimeException("id is not AVailable");
		return service.getById(id);
	}

	@PutMapping("/{id}")
	public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
		return service.update(id, employee);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "Employee Deleted Successfully";
	}

	

}