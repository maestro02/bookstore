package org.books.presentation.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.books.application.exception.CustomerAlreadyExistsException;
import org.books.application.exception.CustomerNotFoundException;
import org.books.application.service.CustomerService;
import org.books.persistence.entity.Customer;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("customers")
public class CustomersResource {

	@EJB
	private CustomerService customerService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(TEXT_PLAIN)
	public Long registerCustomer(Customer customer) {
		try {
			return customerService.registerCustomer(customer);
		} catch (CustomerAlreadyExistsException ex) {
			throw new WebApplicationException(Status.CONFLICT);
		}
	}

	@GET
	@Path("{number}")
	@Produces(APPLICATION_JSON)
	public Customer findCustomer(@PathParam("number") Long number) {
		try {
			Customer customer = customerService.findCustomer(number);
			customer.setPassword(null);
			return customer;
		} catch (CustomerNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@GET
	@Produces(APPLICATION_JSON)
	public Customer findCustomer(@QueryParam("email") String email) {
		try {
			Customer customer = customerService.findCustomer(email);
			customer.setPassword(null);
			return customer;
		} catch (CustomerNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@PUT
	@Path("{number}")
	@Consumes(APPLICATION_JSON)
	public void updateCustomer(@PathParam("number") Long number, Customer customer) {
		if (customer.getNumber() == null) {
			customer.setNumber(number);
		} else if (!customer.getNumber().equals(number)) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		try {
			customerService.updateCustomer(customer);
		} catch (CustomerNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (CustomerAlreadyExistsException ex) {
			throw new WebApplicationException(Status.CONFLICT);
		}
	}
}
