package com.app.vending.controller.transactions.impl;

import java.util.EmptyStackException;

import com.app.vending.controller.CoreManager;
import com.app.vending.controller.transactions.ITransaction;
import com.app.vending.core.model.CoreBalance;
import com.app.vending.core.model.Product;

/**
 * The class that performs the product dispense transaction of the application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class DispenseProductImpl implements ITransaction {
	
	private final CoreManager cm;
	private final String code;
	
	public DispenseProductImpl(final CoreManager cm, final String code) {
		this.cm = cm;
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {
		
		try {
			if (cm.getProductPrice(code).compareTo(cm.getCoreBalance().getBalance()) <= 0) {
				Product product = cm.dispenseProduct(code);
				cm.getCoreBalance().setProductToDispense(product);
			} else {
				cm.getCoreBalance().setError(CoreBalance.ErrorType.NOT_ENOUGH_BALANCE);
			}
		} catch(EmptyStackException ex) {
			cm.getCoreBalance().setError(CoreBalance.ErrorType.PRODUCT_OUT_OF_STOCK);
		}
	}

}
