package de.iteratec.schnitzel.server.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import de.iteratec.schnitzel.common.model.BaseEntity;
import de.iteratec.schnitzel.common.model.IEntity;
import de.iteratec.schnitzel.common.utils.ListUtils;

public class BaseDAO<T extends BaseEntity> implements IBaseDAO<T> {
	
	private static final Logger LOGGER = Logger.getLogger(BaseDAO.class);
	
	private Class<T> type;

	@PersistenceContext(unitName = "pu")
	protected EntityManager entityManager;
	
	public BaseDAO(Class<T> type) {
        this.type = type;
    }
	
	public Class<T> getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(String[] lazyProperties) {
		Query query = entityManager.createQuery("from " + type.getSimpleName());
		List<T> resultList = (List<T>) query.getResultList();
		initializeLazyProperties(resultList, lazyProperties);
		return resultList;
	}

	@Override
	public T findById(long id, String[] lazyProperties) {
		T entity = entityManager.find(getType(), id);
		initializeLazyProperties(entity, lazyProperties);
		return entity;
	}
	
	public Query createQuery(String qlString) {
		return entityManager.createQuery(qlString);
	}

	public List<T> findEntityListByQuery(Query query, String[] lazyProperties) {
		@SuppressWarnings("unchecked")
		List<T> resultList = (List<T>) query.getResultList();
		initializeLazyProperties(resultList, lazyProperties);
		return resultList;
	}
	
	public T findSingleEntityByQuery(Query query, String[] lazyProperties) {
		List<T> resultList = findEntityListByQuery(query, lazyProperties);
		if (resultList.size() == 0) {
			return null;
		} else if (resultList.size() == 1) {
			return resultList.get(0);
		} else {
			throw new RuntimeException("Expected only 1 result but got " + resultList.size());
		}
	}

	@Override
	public T insert(T entity) {
		LOGGER.debug("insert entity: [" + entity.getClass().getSimpleName() + ": " + entity.getId() + "]");
		entityManager.persist(entity);
		entityManager.flush();
		return (T) entity;
	}

	@Override
	public T update(T entity) {
		LOGGER.debug("update entity: [" + entity.getClass().getSimpleName() + ": " + entity.getId() + "]");
		T mergedEntity = (T) entityManager.merge(entity);
		entityManager.flush();
		return mergedEntity;
	}

	@Override
	public void delete(T entity) {
		LOGGER.debug("delete entity: [" + entity.getClass().getSimpleName() + ": " + entity.getId() + "]");
		IEntity entityToRemove = entityManager.merge(entity);
		entityManager.remove(entityToRemove);
		entityManager.flush();
	}

	public void deleteAll() {
		Query query = entityManager.createQuery("delete from " + type.getSimpleName());
		query.executeUpdate();
		entityManager.flush();
	}

	public void initializeLazyProperties(List<T> resultList, String[] lazyProperties) {
		if (lazyProperties == null || ListUtils.isNullOrEmpty(resultList)) {
			return;
		}
		
		for (T entity : resultList) {
			initializeLazyProperties(entity, lazyProperties);
		}
	}

	public void initializeLazyProperties(T entity, String[] lazyProperties) {
		if (lazyProperties == null || entity == null) {
			return;
		}
		
		for (String lazyProperty : lazyProperties) {
			initializeLazyProperty(entity, lazyProperty.split("\\."), 0);
		}
	}

	private void initializeLazyProperty(Object entity, String[] propertyPath, int depth) {
		if (entity == null) {
			return;
		}
		
		try {
			Object property = PropertyUtils.getProperty(entity, propertyPath[depth]);
			Hibernate.initialize(property);
			if ((depth + 1) < propertyPath.length) {
				if (property instanceof Collection<?>) {
					Collection<?> collection = (Collection<?>) property;
					for (Object obj : collection) {
						initializeLazyProperty(obj, propertyPath, depth + 1);
					}
				} else {
					initializeLazyProperty(property, propertyPath, depth + 1);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
