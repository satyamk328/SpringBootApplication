package com.satyam.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.satyam.model.Employee;

/**
 * 
 * @author satyam.kumar
 *
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {
}
