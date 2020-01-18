package com.geo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geo.dao.AuthorDao;
import com.geo.model.Author;
import com.geo.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorDao authorDao;

	@Override
	public Author getAuthorById(Long id) {
		return authorDao.getAuthorById(id);
	}

	@Override
	public void saveAuthor(Author author) {
		authorDao.save(author);

	}

	@Override
	public Author updateAuther(Author author) {
		Author entity = authorDao.getAuthorById(author.getId());
		if (entity != null) {
			entity.setName(author.getName());
		}
		return entity;
	}

	@Override
	public Boolean deleteAuthorById(Long id) {
		return authorDao.deleteAuthor(id);
	}

	@Override
	public List<Author> getAllAuthors() {
		return authorDao.getAllAuthors();
	}

}
