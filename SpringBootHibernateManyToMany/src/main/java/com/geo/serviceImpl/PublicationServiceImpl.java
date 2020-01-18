package com.geo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geo.dao.PublicationDao;
import com.geo.model.Publication;
import com.geo.service.PublicationService;

@Service
public class PublicationServiceImpl implements PublicationService {

	@Autowired
	private PublicationDao publicationDao;

	@Override
	public Publication getPublicationById(Long id) {
		return publicationDao.getPublicationById(id);
	}

	@Override
	public void savePublication(Publication book) {
		publicationDao.savePublicationRecord(book);

	}

	@Override
	public Publication updatePublication(Publication book) {
		Publication entity = publicationDao.getPublicationById(book.getId());
		if (entity != null) {
			entity.setTitle(book.getTitle());
			entity.setYear(book.getYear());
		}
		return book;
	}

	@Override
	public Boolean deletePublicationById(Long id) {
		return publicationDao.deletePublication(id);

	}

	@Override
	public List<Publication> getAllPublication() {
		return publicationDao.getAllPublications();
	}

	@Override
	public List<Publication> searchByYear(String year) {
		return publicationDao.searchByYear(year);
	}

	@Override
	public List<Publication> searchByName(String name) {
		return publicationDao.searchByName(name);
	}

	@Override
	public List<Publication> searchYearAndName(String searchType) {
		return publicationDao.searchYearAndName(searchType);
	}

	@Override
	public List<Publication> search(String searchType) {
		return publicationDao.search(searchType);
	}

}
