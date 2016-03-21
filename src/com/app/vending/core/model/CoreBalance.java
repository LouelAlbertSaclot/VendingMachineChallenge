package com.app.vending.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.app.vending.controller.exceptions.NotEnoughBalanceException;
import com.app.vending.controller.exceptions.OutOfStockException;

/**
 * This class holds the actual status and functionalities related to core of the
 * application. This would be the main target of all {@link ITransaction} objects.
 * 
 * @author Saclot, Louel Albert D.
 * 
 */
public class CoreBalance 
{
	public enum ErrorType {
	    NOT_ENOUGH_BALANCE, PRODUCT_OUT_OF_STOCK
	}
	
	private String[] denomination;
	private BigDecimal balance = BigDecimal.ZERO;
	private List<BigDecimal> changeList = new ArrayList<BigDecimal>();
	private Product product;
	private ErrorType error;
		
	/**
	 * Performs the actual balance update of the application.
	 * 
	 * @param amount The amount to add to the balance.
	 */
	public void updateBalance(BigDecimal amount) {
		balance = balance.add(amount);
	}
	
	/**
	 * Adds the coin denomination to be used for coin release transaction.
	 * 
	 * @param change The coin denomination.
	 */
	public void addCoinChange(final BigDecimal change) {
		balance = balance.subtract(change);
		changeList.add(change);
	}
	
	/**
	 * The actual call that will release the coins to the user.
	 * 
	 * @return The coin denominations.
	 */
	public List<BigDecimal> dispenseChange() {
		
		List<BigDecimal> ch = new ArrayList<BigDecimal>();
		ch.addAll(changeList);
		changeList.clear();
		
		return ch;
	}
	
	/**
	 * This method is evaluates if the product can be dispensed or not as well
	 * as update the balance of the user if the product does get dispensed.
	 * 
	 * @return The product being requested.
	 * @throws NotEnoughBalanceException
	 * @throws OutOfStockException
	 */
	public Product dispenseProduct() 
			throws NotEnoughBalanceException, OutOfStockException {
		
		Product prodHolder = null;
		if (error != null) {
			switch (error) {
			case NOT_ENOUGH_BALANCE   : setError(null); 
										throw new NotEnoughBalanceException();
			case PRODUCT_OUT_OF_STOCK : setError(null); 
										throw new OutOfStockException();
			default : setError(null); throw new OutOfStockException();
		}
			
		} else {
			if (product != null) {
				BigDecimal tmpBalance = balance.subtract(product.getPrice());
				if (tmpBalance.intValue() >= 0) {
					this.balance = tmpBalance;
					prodHolder = this.product;
					this.product = null;
				}
			} 
		}
		
		return prodHolder;
	}
	
	public String[] getDenomination() {
		return denomination;
	}
	
	public void setDenomination(final String[] denomination) {
		this.denomination = denomination;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setProductToDispense(final Product product) {
		this.product = product;
	}
	
	public void setError(ErrorType error) {
		this.error = error;
	}
	
	public ErrorType getError() {
		return this.error;
	}
}
