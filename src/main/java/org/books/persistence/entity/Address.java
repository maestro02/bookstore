package org.books.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by maestroSurface on 10.03.2017.
 */
@Embeddable
public class Address implements Serializable {

	@Column(name = "ADDR_STREET")
	private String street;
	@Column(name = "ADDR_CITY")
	private String city;
	@Column(name = "ADDR_ZIP", nullable = false)
	private String postalCode;
	@Column(name = "ADDR_COUNTRY")
	private String country;

	public Address() {
	}

	public Address(String street, String city, String postalCode, String country) {
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
