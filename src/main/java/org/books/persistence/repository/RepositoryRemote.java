package org.books.persistence.repository;

/**
 * Created by maestroSurface on 31.03.2017.
 */
public interface RepositoryRemote<T> {

	public T persist(T entity);

	public T find(Class<T> type, Object id);

	public T update(T entity);

	public boolean delete(Class<T> type, Object id);

}
