package com.geo.dao;

import java.util.List;

import com.geo.model.Publication;

public interface PublicationDao {

	public Publication getPublicationById(Long id);

	public void savePublicationRecord(Publication book);

	public Boolean deletePublication(Long id);

	public List<Publication> getAllPublications();

	List<Publication> searchByYear(String year);
	
	List<Publication> searchByName(String year);
	
	public List<Publication> searchYearAndName(String searchType);
	
	List<Publication> search(String searchType);
}
