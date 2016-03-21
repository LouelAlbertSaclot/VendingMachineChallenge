package com.app.vending.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

/**
 * The main container of products with the same code. If the dispense product
 * does encounter an EmptyStackException, it will notify the {@link AdminNotification}
 * of this event.
 * 
 * @author Saclot, Louel Albert D.
 * 
 */
public class ProductContainer extends Observable {
	
	private final Stack<Product> products = new Stack<Product>();
	private final int RESTOCK_SIZE;

	private String code;
	private String name;
	private String details;
	private BigDecimal price;
	
	public ProductContainer(final int restockSize) {
		RESTOCK_SIZE = restockSize;
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

	public BigDecimal getProductPrice() {
		return price;
	}

	public List<Product> getProducts() {
		List<Product> list = new ArrayList<Product>();
		list.addAll(products);
		return list;
	}
	
	public Product addProduct(final Product product) {
		if (code == null) {
			this.code = product.getCode();
			this.name = product.getName();
			this.details = product.getDetails();
			this.price   = product.getPrice();
		}
		
		return products.push(product);
	}
	
	public Product dispenseProduct() throws EmptyStackException {
		
		Product product = null;
		try {
			product = products.pop();
		} catch (EmptyStackException ex) {
			// logging...
			setChanged();
			notifyObservers();
			throw new EmptyStackException();
		}
		return product;
	}
	
	public void restockProduct() {
		
		for (int i = 0; i < RESTOCK_SIZE; i++) {
			addProduct(new Product(code, name, details, price));
		}
		
	}

	public void increasePrice(final BigDecimal increase) {
		price = price.add(increase);
		for (Product product : products) {
			product.setPrice(product.getPrice().add(increase));
		}
	}

	public void decreasePrice(final BigDecimal decrease) {
		if (price.doubleValue() > 0.00d) {
			price = price.subtract(decrease);
			for (Product product : products) {
				product.setPrice(product.getPrice().subtract(decrease));
			}
		}
	}
	
	public String generateReport() {
		
		String report = "";
		report += "Product Code  : " + code + "\n";
		report += "Product Price : " + price.toString() + "\n";
		report += "Stock Size    : " + products.size() + "\n\n";
		
		return report;
	}
	
}
