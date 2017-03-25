package main.java.org.books.persistence.repository;

import java.util.List;

import main.java.org.books.persistence.entity.Customer;
import main.java.org.books.persistence.dto.CustomerInfo;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class CustomerRepository extends Repository<Customer> implements CustomerRepositoryRemote{

	public Customer find(Long number) {
		return super.find(Customer.class, number);
	}

	public Customer find(String email) {
		try {
			javax.persistence.Query q = entityManager.createNamedQuery("Customer.findByMail");
			q.setParameter("mail", "%" + email + "%");
			return (Customer) q.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public List<CustomerInfo> search(String name) {
		TypedQuery<CustomerInfo> q = entityManager.createNamedQuery("Customer.findByName", CustomerInfo.class);
		q.setParameter("name", "%" + name + "%");
		return q.getResultList();
	}
}
