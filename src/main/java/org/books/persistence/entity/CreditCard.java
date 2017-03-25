package org.books.persistence.entity;

import org.books.persistence.enumeration.CreditCardType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by maestroSurface on 10.03.2017.
 */
@Embeddable
public class CreditCard implements Serializable{

	@Column(name = "CC_TYPE", nullable = false)
	private CreditCardType type;
	@Column(name = "CC_NUMBER", nullable = false)
	private String number;
	@Column(name = "CC_EXP_MONTH", nullable = false)
	private Integer expirationMonth;
	@Column(name = "CC_EXP_YEAR", nullable = false)
	private Integer expirationYear;

	public CreditCard() {
	}

	public CreditCard(CreditCardType type, String number, Integer expirationMonth, Integer expirationYear) {
		this.type = type;
		this.number = number;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
	}

	public CreditCardType getType() {
		return type;
	}

	public void setType(CreditCardType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public Integer getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
}
