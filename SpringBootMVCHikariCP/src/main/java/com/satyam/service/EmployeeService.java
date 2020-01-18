package com.satyam.service;

import java.util.Collection;

import com.satyam.model.Employee;

/**
 * 
 * @author satyam.kumar
 *
 */
public interface EmployeeService {

	public Employee saveEmployee(Employee emp);
	public Boolean deleteEmployee(String empId);
	public Employee editEmployee(Employee emp);
	public Employee findEmployee(String empId);
	public Collection<Employee> getAllEmployees();
}
