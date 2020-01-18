package com.satya.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 
}
