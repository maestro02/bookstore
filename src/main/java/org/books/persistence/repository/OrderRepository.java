package org.books.persistence.repository;

import java.util.GregorianCalendar;
import java.util.List;

import org.books.persistence.entity.Order;
import org.books.persistence.entity.Customer;
import org.books.persistence.dto.OrderInfo;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class OrderRepository extends Repository<Order> implements OrderRepositoryRemote{

	public Order find(Long number) {
		return super.find(Order.class, number);
	}

	@Override
	public List<OrderInfo> search(Customer customer, Integer year) {
		TypedQuery<OrderInfo> q = entityManager.createNamedQuery("Order.findByYear", OrderInfo.class);
		q.setParameter("customer", customer);
		q.setParameter("startDate", new GregorianCalendar(year, 0, 1).getTime());
		q.setParameter("endDate", new GregorianCalendar(year+1, 0,1).getTime());
		return q.getResultList();
		/*3
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		javax.persistence.Query q = em.createNamedQuery("Order.findByCustomerYear");
		q.setParameter("customer", customer);
		try {
			q.setParameter("year", df.parse(year + "-01-01"));
			q.setParameter("endYear", df.parse(year + "-12-31"));
		} catch (Exception e){
		}
		return new ArrayList<>(q.getResultList());
		*/
	}
}
