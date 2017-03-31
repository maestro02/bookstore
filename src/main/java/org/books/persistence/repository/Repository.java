package org.books.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import static javax.persistence.PersistenceContextType.TRANSACTION;

public class Repository<T> implements RepositoryRemote<T> {

	private static final String PERSISTENCE_UNIT = "bookstore";
@PersistenceContext(unitName = "bookstore", type = TRANSACTION)
	protected EntityManager entityManager;

	public Repository() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		entityManager = entityManagerFactory.createEntityManager();
	}

	public T persist(T entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	public T find(Class<T> type, Object id) {
		return entityManager.find(type, id);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}

	public boolean delete(Class<T> type, Object id) {
		T entity = entityManager.find(type, id);
		if (entity == null) {
			return false;
		}
		entityManager.remove(entity);
		return true;
	}
}