package com.satyam.adminlte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.satyam.adminlte.model.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Long> {

}
