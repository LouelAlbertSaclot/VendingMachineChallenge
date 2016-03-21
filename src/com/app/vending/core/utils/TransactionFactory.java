package com.app.vending.core.utils;

import java.math.BigDecimal;

import com.app.vending.controller.CoreManager;
import com.app.vending.controller.transactions.ITransaction;
import com.app.vending.controller.transactions.TransactionType;
import com.app.vending.controller.transactions.impl.DispenseChangeImpl;
import com.app.vending.controller.transactions.impl.DispenseProductImpl;
import com.app.vending.controller.transactions.impl.UpdateBalanceImpl;
import com.app.vending.core.model.CoreBalance;

/**
 * The class that will generate the transaction needed based on the request of the user.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class TransactionFactory 
{

	public static ITransaction createTransaction(final CoreManager cm, final TransactionType type)
	{
		ITransaction transaction;
		switch (type.getTransactionType()) {
			case UPDATE_BALANCE   : transaction = createUpdateBalance(cm.getCoreBalance(), type.getParameter());
									break;
			case DISPENSE_CHANGE  : transaction = createDispenseChange(cm.getCoreBalance());
									break;
			case DISPENSE_PRODUCT : transaction = createDispenseProduct(cm, type.getParameter());
									break;
			default : transaction = null;
		}
		
		return transaction;
	}
	
	private static ITransaction createUpdateBalance(final CoreBalance core, final String paramater) {
		return new UpdateBalanceImpl(core, new BigDecimal(paramater));
	}
	
	private static ITransaction createDispenseChange(final CoreBalance core) {
		return new DispenseChangeImpl(core);
	}
	
	private static ITransaction createDispenseProduct(final CoreManager cm, final String code) {
		return new DispenseProductImpl(cm, code);
	}
}


