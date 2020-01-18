package com.geo.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.geo.dao.AuthorDao;
import com.geo.model.Author;

@Repository
public class AuthorImpl extends AbstractDao<Long, Author> implements AuthorDao {

	@Override
	@Transactional(readOnly = true)
	public Author getAuthorById(Long id) {
		Author author = getEntityByKey(id);
		if (author != null) {
			Hibernate.initialize(author.getPublications());
		}
		return author;
	}

	@Override
	@Transactional
	public void save(Author author) {
		persist(author);

	}

	@Override
	@Transactional
	public Boolean deleteAuthor(Long id) {
		Criteria crit = createEntity();
		crit.add(Restrictions.eq("id", id));
		Author author = (Author) crit.uniqueResult();
		delete(author);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Author> getAllAuthors() {
		Criteria criteria = createEntity();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Author> authors = (List<Author>) criteria.list();
		for (Author author : authors) {
			Hibernate.initialize(author.getPublications());
		}
		return authors;
	}

}
