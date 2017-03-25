package org.books.persistence.repository;

import org.books.persistence.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Stateless
public class BookRepository extends Repository<Book> implements BookRepositoryRemote{

	@Override
	public Book find(String isbn) {
		return super.find(Book.class, isbn);
	}

	@Override
	public List<Book> search(String keywords) {
		/* MY RESULT

		String[] keys = keywords.split(" ");
		LinkedList<Book> result = new LinkedList<>();
		boolean exist = false;

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(Book.class);
		Root<Book> book = cq.from(Book.class);
		cq.select(book);

		for (String key : keys){
			String nkey = "%" + key + "%";
			cq.where(cb.or(cb.like(cb.lower(book.<String>get("title")), nkey),cb.like(cb.lower(book.<String>get("authors")), nkey),cb.like(cb.lower(book.<String>get("publisher")), nkey)));
			List<Book> books = em.createQuery(cq).getResultList();
			for (Book b : books){
				for (int i = 0; i < result.size(); i++){
					if(result.get(i).getTitle().equals(b.getTitle())){
						exist = true;
					}
				}
				if (!exist){
					result.add(b);
				}
			}
		}
		for (String k : keys){
			for (Book b : result){
				if (!(b.getTitle().toLowerCase().contains(k) || b.getAuthors().toLowerCase().contains(k) || b.getPublisher().toLowerCase().contains(k))){
					result.remove(b);
				}
			}
		}
		return result;
		*/

		// SOLUTION

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> book = cq.from(Book.class);

		cq.select(book);
		String[] tokens = keywords.toLowerCase().split("\\s+");
		Predicate[] predicates = new Predicate[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String pattern = "%" + tokens[i] + "%";
			predicates[i] = cb.or(
					cb.like(cb.lower(book.<String>get("title")), pattern),
					cb.like(cb.lower(book.<String>get("authors")), pattern),
					cb.like(cb.lower(book.<String>get("publisher")), pattern));
		}
		cq.where(cb.and(predicates));
		return entityManager.createQuery(cq).getResultList();

	}
}
