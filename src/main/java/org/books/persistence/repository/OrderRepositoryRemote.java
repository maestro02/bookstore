package org.books.persistence.repository;

import org.books.persistence.entity.Customer;
import org.books.persistence.entity.Order;
import org.books.persistence.dto.OrderInfo;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by maestroSurface on 24.03.2017.
 */
@Remote
public interface OrderRepositoryRemote extends RepositoryRemote<Order> {

	public Order find(Long number);
	public List<OrderInfo> search(Customer customer, Integer year);
}
