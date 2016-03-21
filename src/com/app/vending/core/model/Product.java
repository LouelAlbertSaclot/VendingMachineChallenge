package com.app.vending.core.model;

import java.math.BigDecimal;

/**
 * Hold the main details of the product.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class Product {

	private final String code;
	private final String name;
	private final String details;
	private BigDecimal price = BigDecimal.ZERO;
	
	public Product(final String code, final String name, final String details, final BigDecimal price) {
		this.code = code;
		this.name = name;
		this.details = details;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDetails() {
		return details;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
