package com.geo.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.geo.dao.PublicationDao;
import com.geo.model.Publication;

@Repository
public class PublicationDaoImpl extends AbstractDao<Long, Publication> implements PublicationDao {

	@Override
	@Transactional(readOnly = true)
	public Publication getPublicationById(Long id) {
		Publication book = getEntityByKey(id);
		if (book != null) {
			Hibernate.initialize(book.getAuthors());
		}
		return book;
	}

	@Override
	@Transactional
	public void savePublicationRecord(Publication book) {
		persist(book);

	}

	@Override
	@Transactional
	public Boolean deletePublication(Long id) {
		Criteria crit = createEntity();
		crit.add(Restrictions.eq("id", id));
		Publication book = (Publication) crit.uniqueResult();
		delete(book);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Publication> getAllPublications() {
		Criteria criteria = createEntity();
		List<Publication> publications = (List<Publication>) criteria.list();
		for (Publication pub : publications) {
			Hibernate.initialize(pub.getAuthors());
		}
		return publications;
	}

	@Override
	public List<Publication> searchYearAndName(String searchType) {
		Query query = getSession().createQuery(
				"select pub.* from Publication pub join pub.authors auth WHERE pub.year like :year or auth.name like :name")
				.setParameter("year", "%" + searchType + "%")
				.setParameter("name", "%" + searchType + "%");
		List<Publication> publications = (List<Publication>) query.list();
		for (Publication pub : publications) {
			Hibernate.initialize(pub.getAuthors());
		}
		return publications;
	}
	
	@Override
	public List<Publication> search(String searchType) {
		Query query = getSession().createQuery(
				"select pub.* from Publication pub join pub.authors auth WHERE pub.title like :title or pub.year like :year or auth.name like :name")
				.setParameter("title", "%" + searchType + "%").setParameter("year", "%" + searchType + "%")
				.setParameter("name", "%" + searchType + "%");
		List<Publication> publications = (List<Publication>) query.list();
		for (Publication pub : publications) {
			Hibernate.initialize(pub.getAuthors());
		}
		return publications;
	}

	@Override
	public List<Publication> searchByYear(String year) {
		Criteria criteria = createEntity();
		criteria.add(Restrictions.like("year", year));
		List<Publication> publications = (List<Publication>) criteria.list();
		for (Publication pub : publications) {
			Hibernate.initialize(pub.getAuthors());
		}
		return publications;
	}

	@Override
	public List<Publication> searchByName(String name) {
		Criteria criteria = createEntity();
		Criteria criteria2 = criteria.createCriteria("authors");
		criteria2.add(Restrictions.like("name", "%" + name + "%"));
		List<Publication> publications = (List<Publication>) criteria.list();
		for (Publication pub : publications) {
			Hibernate.initialize(pub.getAuthors());
		}
		return publications;
	}

}
