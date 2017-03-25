package main.java.org.books.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by maestroSurface on 10.03.2017.
 */
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private long id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ISBN")
	private Book book;
	@Column(name = "ITEM_QUANTITY")
	private Integer quantity;
	@Column(name = "ITEM_PRICE", precision = 7, scale = 2)
	private BigDecimal price;

	public OrderItem() {
	}

	public OrderItem(Book book, Integer quantity, BigDecimal price) {
		this.book = book;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
