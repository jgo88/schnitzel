package de.iteratec.schnitzel.server.persistence;

import java.util.List;

import javax.persistence.Query;

public interface IBaseDAO<T> {
	
	List<T> findAll(String[] lazyProperties);

	T findById(long id, String[] lazyProperties);
	
	List<T> findEntityListByQuery(Query query, String[] lazyProperties);
	
	T findSingleEntityByQuery(Query query, String[] lazyProperties);

	T insert(T entity);

	T update(T entity);
	
	void delete(T entity);
	
	void deleteAll();

}