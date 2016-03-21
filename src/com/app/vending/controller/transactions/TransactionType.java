package com.app.vending.controller.transactions;

/**
 * Defines the Types of Transactions available in the system.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class TransactionType {
	
	public enum Type {
	    UPDATE_BALANCE, DISPENSE_PRODUCT, DISPENSE_CHANGE
	}
	
	private Type transactionType;
	private String parameter;
	
	public Type getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Type transactionType) {
		this.transactionType = transactionType;
	}

	public String getParameter() {
		return parameter;
	}
	
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	
}
