package com.satyam.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satyam.model.Employee;
import com.satyam.repository.EmployeeRepository;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee emp) {
        return employeeRepository.save(emp);
    }

    @Override
    public Boolean deleteEmployee(String empId) {
        Employee temp = employeeRepository.findById(empId).get();
        if (temp != null) {
            employeeRepository.delete(temp);
            return true;
        }
        return false;
    }

    @Override
    public Employee editEmployee(Employee emp) {
        return employeeRepository.save(emp);
    }

    @Override
    public Collection<Employee> getAllEmployees() {
        Iterable<Employee> itr = employeeRepository.findAll();
        return (Collection<Employee>) itr;
    }

    @Override
    public Employee findEmployee(String empId) {
        return employeeRepository.findById(empId).get();
    }


}
