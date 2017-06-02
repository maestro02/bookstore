package org.books.presentation.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.books.persistence.entity.Book;

@Named("shoppingCart")
@SessionScoped
public class ShoppingCartBean implements Serializable {

	private final List<CartItem> items = new ArrayList();
	private Integer totalQuantity;
	private BigDecimal totalPrice;

	public List<CartItem> getItems() {
		return items;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void addBook(Book book) {
		CartItem item = findItem(book.getIsbn());
		if (item != null) {
			item.setQuantity(item.getQuantity() + 1);
		} else {
			items.add(new CartItem(book, 1));
		}
		updateTotalPrice();
	}

	public void removeBook(Book book) {
		CartItem item = findItem(book.getIsbn());
		if (item != null) {
			items.remove(item);
		}
		updateTotalPrice();
	}

	public void updateTotalPrice() {
		totalQuantity = 0;
		totalPrice = BigDecimal.ZERO;
		for (CartItem item : items) {
			totalQuantity += item.getQuantity();
			totalPrice = totalPrice.add(item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		}
	}

	public void clear() {
		items.clear();
		updateTotalPrice();
	}

	private CartItem findItem(String isbn) {
		for (CartItem item : items) {
			if (item.getBook().getIsbn().equals(isbn)) {
				return item;
			}
		}
		return null;
	}
}
