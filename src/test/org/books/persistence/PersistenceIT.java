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

	private final String rootPath = "java:global/org.books.persistence.repository";
	private final BookRepository bookRepository = new BookRepository();
	private final CustomerRepository customerRepository = new CustomerRepository();
	private final OrderRepository orderRepository = new OrderRepository();

	private List<Book> books;
	private Customer customer;
	private Order order;

	@BeforeClass
	public void lookupRepositories() throws NamingException{
		Context jndiContext = new InitialContext();
		//jndiContext.lookup(rootPath);

		NamingEnumeration<NameClassPair> list = jndiContext.list("");
		System.out.println("Start scan for JNDI.");
		while (list.hasMore()) {
			System.out.println(list.next().getName());
		}
		System.out.println("Finished scan for JNDI.");

		//Hashtable env = jndiContext.getEnvironment();
		//System.out.println("Table: "+env.toString());

		System.out.println("Name in Space: " +jndiContext.getNameInNamespace());;

		BookRepositoryRemote bookRemote = (BookRepositoryRemote) jndiContext.lookup(rootPath + "!BookRepositoryRemote");
		CustomerRepositoryRemote customerRemote = (CustomerRepositoryRemote) jndiContext.lookup(rootPath + "!CustomerRepositoryRemote");
		OrderRepositoryRemote orderRemote = (OrderRepositoryRemote) jndiContext.lookup(rootPath + "!OrderRepositoryRemote");

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
		customerRepository.persist(customer);
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
		orderRepository.persist(order);
		assertNotNull(orderRepository.find(order.getNumber()));
	}

	@Test(dependsOnMethods = "persistOrder")
	public void searchOrders() {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		assertEquals(orderRepository.search(customer, year - 1).size(), 0);
		assertEquals(orderRepository.search(customer, year).size(), 1);
		assertEquals(orderRepository.search(customer, year + 1).size(), 0);
	}
}
