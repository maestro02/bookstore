package org.books.persistence.repository;

import org.books.persistence.dto.CustomerInfo;
import org.books.persistence.entity.Customer;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by maestroSurface on 24.03.2017.
 */
@Remote
public interface CustomerRepositoryRemote {

	public Customer find(Long number);
	public Customer find(String email);
	public List<CustomerInfo> search(String name);

}
