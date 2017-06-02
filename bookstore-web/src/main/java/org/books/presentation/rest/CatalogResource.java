package org.books.presentation.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.books.application.exception.BookNotFoundException;
import org.books.application.service.CatalogService;
import org.books.persistence.entity.Book;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("books")
public class CatalogResource {

	@EJB
	private CatalogService catalogService;

	@GET
	@Path("{isbn}")
	@Produces(APPLICATION_JSON)
	public Book findBook(@PathParam("isbn") String isbn) {
		try {
			return catalogService.findBook(isbn);
		} catch (BookNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@GET
	@Produces(APPLICATION_JSON)
	public List<Book> searchBooks(@QueryParam("keywords") String keywords) {
		return catalogService.searchBooks(keywords);
	}
}
