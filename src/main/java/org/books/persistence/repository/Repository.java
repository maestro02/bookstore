package org.books.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import static javax.persistence.PersistenceContextType.TRANSACTION;

@PersistenceContext(unitName = "bookstore", type = TRANSACTION)
public class Repository<T> {

	private static final String PERSISTENCE_UNIT = "bookstore";
	protected EntityManager entityManager;

	public Repository() {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		entityManager = entityManagerFactory.createEntityManager();
	}

	public void persist(T entity) {
		entityManager.persist(entity);
	}

	public T find(Class<T> type, Object id) {
		return entityManager.find(type, id);
	}

	public T update(T entity) {
		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return entity;
	}

	public boolean delete(Class<T> type, Object id) {
		T entity = entityManager.find(type, id);
		if (entity == null) {
			return false;
		}
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		return true;
	}
}