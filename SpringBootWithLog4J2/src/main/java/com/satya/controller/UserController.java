package com.satya.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.User;
import com.satya.service.UserService;

@RestController
@RequestMapping(value = "/api/v0")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
		return ResponseEntity.ok( userService.findById(id));
	}

	@PostMapping(value = "/add-user")
	public ResponseEntity addUser(@RequestBody User userRecord) {
		return ResponseEntity.ok(userService.save(userRecord));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity delete(@PathVariable(name = "id") Long id) {
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}