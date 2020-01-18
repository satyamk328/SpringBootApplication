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

import com.geo.model.Publication;
import com.geo.service.PublicationService;
import com.geo.spring.model.RestResponse;
import com.geo.spring.model.RestStatus;

@RestController
@RequestMapping(value = "/api/v0/book")
public class PublicationController {

	@Autowired
	private PublicationService publicationService;

	@GetMapping(value = "/")
	public ResponseEntity<RestResponse<List<Publication>>> getAllBooks() {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "All Records Fetched Successfully");
		List<Publication> books = publicationService.getAllPublication();
		return new ResponseEntity<>(new RestResponse<>(books, status), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RestResponse<Publication>> getAuthorById(
			@PathVariable(name = "id", required = true) Long id) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record Fetched Successfully");
		Publication book = publicationService.getPublicationById(id);
		if (book == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot fetch Successfully");
			return new ResponseEntity<>(new RestResponse<>(book, status), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new RestResponse<>(book, status), HttpStatus.OK);
	}

	@GetMapping(value = "/searchByYear")
	public ResponseEntity<RestResponse<List<Publication>>> getSearchByYear(
			@RequestParam(name = "year", required = true) String year) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record Fetched Successfully");
		List<Publication> publications = publicationService.searchByYear(year);
		if (publications == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot fetch Successfully");
			return new ResponseEntity<>(new RestResponse<>(publications, status), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new RestResponse<>(publications, status), HttpStatus.OK);
	}

	@GetMapping(value = "/searchByYear")
	public ResponseEntity<RestResponse<List<Publication>>> getSearchByName(
			@RequestParam(name = "year", required = true) String name) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record Fetched Successfully");
		List<Publication> publications = publicationService.searchByName(name);
		if (publications == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot fetch Successfully");
			return new ResponseEntity<>(new RestResponse<>(publications, status), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new RestResponse<>(publications, status), HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<RestResponse<List<Publication>>> getSearch(
			@RequestParam(name = "year", required = true) String search) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record Fetched Successfully");
		List<Publication> publications = publicationService.search(search);
		if (publications == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot fetch Successfully");
			return new ResponseEntity<>(new RestResponse<>(publications, status), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new RestResponse<>(publications, status), HttpStatus.OK);
	}

	@PostMapping(value = "/addPub")
	public ResponseEntity<RestResponse<Void>> addAuthor(@RequestBody(required = true) Publication book) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record added Successfully");
		publicationService.savePublication(book);
		return new ResponseEntity<>(new RestResponse<>(null, status), HttpStatus.OK);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<RestResponse<Publication>> updateUser(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) Publication book) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record updated Successfully");
		Publication tempBook = publicationService.updatePublication(book);
		if (tempBook == null) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record cannot update Successfully");
			return new ResponseEntity<>(new RestResponse<>(book, status), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new RestResponse<>(book, status), HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<RestResponse<Object>> deleteAuther(@PathVariable(name = "id", required = true) Long id) {
		RestStatus<String> status = new RestStatus<>(HttpStatus.OK.toString(), "Record Deleted Successfully");
		Boolean isDeleted = publicationService.deletePublicationById(id);
		if (!isDeleted) {
			status = new RestStatus<>(HttpStatus.OK.toString(), "Record can't delete Successfully");
			return new ResponseEntity<>(new RestResponse<>(isDeleted, status), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new RestResponse<>(isDeleted, status), HttpStatus.OK);
	}

}
