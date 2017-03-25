package org.books.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by maestroSurface on 10.03.2017.
 */
// @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(:name) OR LOWER(c.lastName) LIKE LOWER(:name) "),
@NamedQueries({@NamedQuery(name = "Customer.findByMail", query =
				"SELECT c FROM Customer c WHERE LOWER(c.email) LIKE LOWER(:mail)"),
		@NamedQuery(name = "Customer.findByName", query =
				"SELECT NEW org.books.persistence.dto.CustomerInfo(c.number, c.firstName, c.lastName, c.email) from Customer c where LOWER(c.firstName) LIKE LOWER(:name) OR LOWER(c.lastName) LIKE LOWER(:name)")})

@Entity
public class Customer implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "CUSTOMER_ID")
	private Long number;
	@Column(name = "CUSTOMER_FIRST_NAME")
	private String firstName;
	@Column(name = "CUSTOMER_LAST_NAME")
	private String lastName;
	@Column(name = "CUSTOMER_EMAIL", unique = true, nullable = false)
	private String email;
	@Column(name = "CUSTOMER_PASSWORD")
	private String password;
	@Embedded
	private Address address;
	@Embedded
	private CreditCard creditCard;

	public Customer() {
	}

	public Customer(String firstName, String lastName, String email, String password,
					Address address, CreditCard creditCard) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.creditCard = creditCard;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		if (creditCard == null) {
			creditCard = new CreditCard();
		}
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
}
