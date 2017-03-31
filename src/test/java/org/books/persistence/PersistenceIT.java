package org.books.persistence;

import org.books.persistence.entity.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.books.persistence.repository.*;

import javax.naming.*;
import java.math.BigDecimal;
import java.util.*;

import static org.books.persistence.enumeration.BookBinding.*;
import static org.books.persistence.enumeration.CreditCardType.*;
import static org.books.persistence.enumeration.OrderStatus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PersistenceIT {

	private final String JNDI_BASE_NAME = "java:global/bookstore/";
	private BookRepositoryRemote bookRepository;
	private CustomerRepositoryRemote customerRepository;
	private OrderRepositoryRemote orderRepository;

	private List<Book> books;
	private Customer customer;
	private Order order;

	@BeforeClass
	public void lookupRepositories() throws NamingException{
		Context jndiContext = new InitialContext();
		bookRepository = (BookRepositoryRemote) jndiContext.lookup(JNDI_BASE_NAME + "BookRepository");
		customerRepository = (CustomerRepositoryRemote) jndiContext.lookup(JNDI_BASE_NAME + "CustomerRepository");
		orderRepository = (OrderRepositoryRemote) jndiContext.lookup(JNDI_BASE_NAME + "OrderRepository");

	}

	@BeforeClass
	public void initData() {
		books = Arrays.asList(
				new Book("143024626X", "Beginning Java EE 7", "Antonio Goncalves", "Apress", 2013,
						PAPERBACK, 608, new BigDecimal("49.99")),
				new Book("1449370179", "Java EE 7 Essentials", "Arun Gupta", "O'Reilly Media", 2013,
						PAPERBACK, 362, new BigDecimal("49.99")));
		customer = new Customer("Alice", "Smith", "alice@example.org", "alice",
				new Address("123 Maple Street", "Mill Valley", "CA-90952", "US"),
				new CreditCard(MASTERCARD, "5400000000000005", 01, 2020));
	}

	@Test
	public void persistBooks() {
		for (Book book : books) {
			bookRepository.persist(book);
			assertNotNull(bookRepository.find(book.getIsbn()));
		}
	}

	@Test(dependsOnMethods = "persistBooks")
	public void searchBooks() {
		assertEquals(bookRepository.search("java ee").size(), 2);
		assertEquals(bookRepository.search("java ee goncalves").size(), 1);
		assertEquals(bookRepository.search("java ee reilly").size(), 1);
		assertEquals(bookRepository.search("java ee goncalves reilly").size(), 0);
	}

	@Test
	public void persistCustomer() {
		customer = customerRepository.persist(customer);
		assertNotNull(customerRepository.find(customer.getNumber()));
	}

	@Test(dependsOnMethods = "persistCustomer")
	public void searchCustomers() {
		assertEquals(customerRepository.search("alice").size(), 1);
		assertEquals(customerRepository.search("smith").size(), 1);
	}

	@Test(dependsOnMethods = {"persistBooks", "persistCustomer"})
	public void persistOrder() {
		List<OrderItem> items = new ArrayList<>();
		BigDecimal amount = BigDecimal.ZERO;
		for (Book book : books) {
			amount = amount.add(book.getPrice());
			items.add(new OrderItem(book, 1, book.getPrice()));
		}
		order = new Order(new Date(), amount, ACCEPTED, customer, customer.getAddress(), customer.getCreditCard(), items);
		this.order = orderRepository.persist(order);
		assertNotNull(orderRepository.find(this.order.getNumber()));
	}

	@Test(dependsOnMethods = "persistOrder")
	public void searchOrders() {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		assertEquals(orderRepository.search(customer, year - 1).size(), 0);
		assertEquals(orderRepository.search(customer, year).size(), 1);
		assertEquals(orderRepository.search(customer, year + 1).size(), 0);
	}
}
