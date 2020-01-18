package com.geo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geo.model.Author;
import com.geo.service.AuthorService;
import com.geo.spring.model.RestResponse;
import com.geo.spring.model.RestStatus;

@RestController
@RequestMapping(value = "/api/v0/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping(value = "/")
	public ResponseEntity<RestResponse<List<Author>>> getAuthors() {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "All Records Fetched Successfully");
		List<Author> authors = authorService.getAllAuthors();
		return new ResponseEntity<>(new RestResponse<>(authors, status), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RestResponse<Author>> getAuthorById(@PathVariable(name = "id", required = true) Long id) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record Fetched Successfully");
		Author author = authorService.getAuthorById(id);
		if (author == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot fetch Successfully");
			return new ResponseEntity<>(new RestResponse<>(author, status), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new RestResponse<>(author, status), HttpStatus.OK);
	}
	

	@PostMapping(value = "/addAuthor")
	public ResponseEntity<RestResponse<Void>> addAuthor(@RequestBody(required = true) Author author) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record added Successfully");
		authorService.saveAuthor(author);
		return new ResponseEntity<>(new RestResponse<>(null, status), HttpStatus.OK);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<RestResponse<Author>> updateUser(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) Author author) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record updated Successfully");
		Author tempAuthor = authorService.updateAuther(author);
		if (tempAuthor == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot update Successfully");
			return new ResponseEntity<>(new RestResponse<>(tempAuthor, status), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new RestResponse<>(tempAuthor, status), HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<RestResponse<Object>> deleteAuther(@PathVariable(name = "id", required = true) Long id) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "All Records Fetched Successfully");
		Boolean isDeleted = authorService.deleteAuthorById(id);
		if (!isDeleted) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record can't delete Successfully");
			return new ResponseEntity<>(new RestResponse<>(isDeleted, status), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new RestResponse<>(isDeleted, status), HttpStatus.OK);
	}

}
