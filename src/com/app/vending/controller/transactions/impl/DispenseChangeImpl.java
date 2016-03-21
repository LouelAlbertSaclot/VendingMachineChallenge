package com.app.vending.controller.transactions.impl;

import java.math.BigDecimal;

import com.app.vending.controller.transactions.ITransaction;
import com.app.vending.core.model.CoreBalance;

/**
 * The class that performs the change dispense transaction of the application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class DispenseChangeImpl implements ITransaction {
	
	private CoreBalance core;
	
	public DispenseChangeImpl(final CoreBalance core) {
		this.core = core;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This is an attempt to the Greedy algorithm approach as well.
	 */
	@Override
	public void execute() {
		
		String[] denom = core.getDenomination();
		BigDecimal changeInCent = core.getBalance();
		for (int i = 0; i < denom.length; ++i) {
			
			BigDecimal nb = changeInCent.divide(new BigDecimal(denom[i]));
			if (!nb.equals(BigDecimal.ZERO)) {
				for (int j = nb.intValue(); j > 0; j--) {
					core.addCoinChange(new BigDecimal(denom[i]));
				}
			}
			
			changeInCent = changeInCent.remainder(new BigDecimal(denom[i]));
		}
	}

}
