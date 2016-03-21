package com.app.vending.controller.exceptions;

/**
 * The custom exception to indicate that the product is out of stock.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class OutOfStockException extends Exception {

	private static final long serialVersionUID = 1L;

	public OutOfStockException() {
		super("Product count is not enough for this transaction.");
	}
}
