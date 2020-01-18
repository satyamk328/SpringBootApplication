package com.geo.dao;

import java.util.List;

import com.geo.model.Author;

public interface AuthorDao {

	public Author getAuthorById(Long id);
    
	public void save(Author author);
     
	public Boolean deleteAuthor(Long id);
     
	public List<Author> getAllAuthors();
    
}
