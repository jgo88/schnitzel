package de.iteratec.schnitzel.server.business;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.iteratec.schnitzel.common.model.BaseEntity;
import de.iteratec.schnitzel.server.persistence.IBaseDAO;

@Service
@Transactional
public abstract class BaseBA<T extends BaseEntity> {
	
	public abstract IBaseDAO<T> getDao();

	@Transactional
	public List<T> findAll(String[] lazyProperties) {
		return getDao().findAll(lazyProperties);
	}

	@Transactional
	public T findById(long id, String[] lazyProperties) {
		return getDao().findById(id, lazyProperties);
	}
	
	@Transactional
	protected List<T> findEntityListByQuery(Query query, String[] lazyProperties) {
		List<T> resultList = getDao().findEntityListByQuery(query, lazyProperties);
		return resultList;
	}
	
	@Transactional
	protected T findSingleEntityByQuery(Query query, String[] lazyProperties) {
		T result = getDao().findSingleEntityByQuery(query, lazyProperties);
		return result;
	}

	@Transactional
	public T save(T entity) {
		if (entity.getId() == null) {
			return insert(entity);
		} else {
			return update(entity);
		}
	}
	
	@Transactional
	public T insert(T entity) {
		return getDao().insert(entity);
	}

	@Transactional
	public T update(T entity) {
		return getDao().update(entity);
	}
	
	@Transactional
	public void delete(T entity) {
		getDao().delete(entity);
	}
	
	@Transactional
	public void deleteAll() {
		getDao().deleteAll();
	}

}
