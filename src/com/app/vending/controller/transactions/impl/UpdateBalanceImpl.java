package com.app.vending.controller.transactions.impl;

import java.math.BigDecimal;

import com.app.vending.controller.transactions.ITransaction;
import com.app.vending.core.model.CoreBalance;

/**
 * The class that performs the update balance transaction of the application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class UpdateBalanceImpl implements ITransaction {
	
	private CoreBalance core;
	private BigDecimal amount;
	
	public UpdateBalanceImpl(final CoreBalance core, final BigDecimal amount) {
		this.core = core;
		this.amount = amount;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		core.updateBalance(amount);
	}

}
