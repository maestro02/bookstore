package org.books.presentation.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.books.application.service.CatalogService;
import org.books.persistence.entity.Book;

@Named
@SessionScoped
public class CatalogBean implements Serializable {

	@EJB
	private CatalogService catalogService;

	private String keywords;
	private List<Book> books;
	private Book selectedBook;
	private String errorMessage;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<Book> getBooks() {
		return books;
	}

	public Book getSelectedBook() {
		return selectedBook;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void searchBooks() {
		errorMessage = null;
		selectedBook = null;
		books = catalogService.searchBooks(keywords);
		if (books.isEmpty()) {
			errorMessage = "No matching books found";
		}
	}

	public String selectBook(Book book) {
		selectedBook = book;
        return "bookDetails";
	}
}
