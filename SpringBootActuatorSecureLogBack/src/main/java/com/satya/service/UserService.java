package com.satya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.dao.UserRepository;
import com.satya.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User stock) {
        return userRepository.save(stock);
    }

    public void deleteById(Long id) {
    	userRepository.deleteById(id);
    }
}