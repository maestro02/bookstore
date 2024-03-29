package org.books.persistence.repository;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static javax.ejb.TransactionAttributeType.MANDATORY;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.persistence.PersistenceContextType.TRANSACTION;

public class Repository<T> {

	private static final String PERSISTENCE_UNIT = "bookstore";

	@PersistenceContext(unitName = "bookstore", type = TRANSACTION)
	protected EntityManager entityManager;

	@TransactionAttribute(MANDATORY)
	public T persist(T entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	@TransactionAttribute(SUPPORTS)
	public T find(Class<T> type, Object id) {
		return entityManager.find(type, id);
	}

	@TransactionAttribute(MANDATORY)
	public T update(T entity) {
		return entityManager.merge(entity);
	}

	@TransactionAttribute(MANDATORY)
	public boolean delete(Class<T> type, Object id) {
		T entity = entityManager.find(type, id);
		if (entity == null) {
			return false;
		}
		entityManager.remove(entity);
		return true;
	}
}