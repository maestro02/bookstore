package org.books.presentation.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.books.application.dto.PurchaseOrder;
import org.books.application.dto.SalesOrder;
import org.books.application.exception.BookNotFoundException;
import org.books.application.exception.CustomerNotFoundException;
import org.books.application.exception.OrderAlreadyShippedException;
import org.books.application.exception.OrderNotFoundException;
import org.books.application.exception.PaymentFailedException;
import org.books.application.service.OrderService;
import org.books.persistence.dto.OrderInfo;
import org.books.persistence.entity.OrderItem;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Path("orders")
public class OrdersResource {

	@EJB
	private OrderService orderService;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	public SalesOrder placeOrder(PurchaseOrder purchaseOrder) {
		try {
			SalesOrder order = orderService.placeOrder(purchaseOrder);
			for (OrderItem item : order.getItems()) {
				item.setId(null);
			}
			return order;
		} catch (CustomerNotFoundException | BookNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (PaymentFailedException ex) {
			throw new WebApplicationException(Status.PAYMENT_REQUIRED);
		}
	}

	@GET
	@Path("{number}")
	@Produces(APPLICATION_JSON)
	public SalesOrder findOrder(@PathParam("number") Long number) {
		try {
			SalesOrder salesOrder = orderService.findOrder(number);
			for (OrderItem item : salesOrder.getItems()) {
				item.setId(null);
			}
			return salesOrder;
		} catch (OrderNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@GET
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	public List<OrderInfo> searchOrders(@QueryParam("customerNr") Long customerNr, @QueryParam("year") Integer year) {
		try {
			return orderService.searchOrders(customerNr, year);
		} catch (CustomerNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	@DELETE
	@Path("{number}")
	public void cancelOrder(@PathParam("number") Long number) {
		try {
			orderService.cancelOrder(number);
		} catch (OrderNotFoundException ex) {
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (OrderAlreadyShippedException ex) {
			throw new WebApplicationException(Status.CONFLICT);
		}
	}
}
