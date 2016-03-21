package com.app.vending.controller.exceptions;

/**
 * The custom exception to indicate that there is insufficient fund.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class NotEnoughBalanceException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotEnoughBalanceException() {
		super("Current Balance is not enough to continue transaction.");
	}
}
