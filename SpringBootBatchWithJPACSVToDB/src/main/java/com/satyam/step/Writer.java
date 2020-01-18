package com.satyam.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.satyam.dao.UserDao;
import com.satyam.model.User;

public class Writer implements ItemWriter<User> {

	@Autowired
	private UserDao userRepository;

	@Override
	public void write(List<? extends User> users) throws Exception {
		System.out.println("Data Saved for Users: " + users);
		userRepository.saveAll(users);
	}
}