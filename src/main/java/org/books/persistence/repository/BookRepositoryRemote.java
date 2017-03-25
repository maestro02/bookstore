package org.books.persistence.repository;

import org.books.persistence.entity.Book;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by maestroSurface on 24.03.2017.
 */
@Remote
public interface BookRepositoryRemote {

	public Book find(String isbn);
	public List<Book> search(String keywords);
}
