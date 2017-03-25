package main.java.org.books.persistence.entity;

import main.java.org.books.persistence.enumeration.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maestroSurface on 10.03.2017.
 */

@NamedQueries({@NamedQuery(name = "Order.findByCustomerYear", query = "SELECT o FROM Order o WHERE o.customer = :customer AND o.date BETWEEN :year AND :endYear"), // MY RESULT
	@NamedQuery(name = "Order.findByYear", query = "SELECT NEW org.books.persistence.dto.OrderInfo(o.number, o.date, o.amount, o.status) FROM Order o WHERE o.customer = :customer AND o.date BETWEEN :startDate AND :endDate")}) // SOLUTION


@Entity
@Table(name = "BOOK_ORDER")
public class Order implements Serializable{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long number;
	@Temporal(TemporalType.DATE)
	@Column(name = "ORDER_DATE", nullable = false)
	private Date date;
	@Column(name = "ORDER_AMOUNT", nullable = false)
	private BigDecimal amount;
	@Column(name = "ORDER_STATUS", nullable = false)
	private OrderStatus status;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CUSTOMER_NR")
	private Customer customer;
	@Embedded private Address address;
	@Embedded private CreditCard creditCard;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_NR")
	private List<OrderItem> items = new ArrayList<>();

	public Order() {
	}

	public Order(Date date, BigDecimal amount, OrderStatus status,
				 Customer customer, Address address, CreditCard creditCard, List<OrderItem> items) {
		this.date = date;
		this.amount = amount;
		this.status = status;
		this.customer = customer;
		this.address = address;
		this.creditCard = creditCard;
		this.items = items;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard card) {
		this.creditCard = card;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
}
