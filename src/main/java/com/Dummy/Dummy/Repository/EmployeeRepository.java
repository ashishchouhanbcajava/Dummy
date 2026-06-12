package com.Dummy.Dummy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dummy.Dummy.beans.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
