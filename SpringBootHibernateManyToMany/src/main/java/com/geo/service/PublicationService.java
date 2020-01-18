package com.geo.service;

import java.util.List;

import com.geo.model.Publication;

public interface PublicationService {

	Publication getPublicationById(Long id);

	void savePublication(Publication book);

	Publication updatePublication(Publication book);

	Boolean deletePublicationById(Long id);

	List<Publication> getAllPublication();

	List<Publication> searchByYear(String year);

	List<Publication> searchByName(String year);

	public List<Publication> searchYearAndName(String searchType);

	List<Publication> search(String searchType);
}
