package com.geo.service;

import java.util.List;

import com.geo.model.Author;

public interface AuthorService {

	Author getAuthorById(Long id);

	void saveAuthor(Author author);

	Author updateAuther(Author author);

	Boolean deleteAuthorById(Long id);

	List<Author> getAllAuthors();

}
