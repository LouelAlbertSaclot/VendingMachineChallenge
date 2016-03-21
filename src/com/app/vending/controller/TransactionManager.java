package com.app.vending.controller;

import java.util.Stack;

import com.app.vending.controller.transactions.ITransaction;

/**
 * Handles all transactions and stores it. Command pattern was used here as to
 * have the option to cancel the last transaction sent by the system (not yet
 * supported as of now).
 * 
 * @author Saclot, Louel Albert D.
 * 
 */
public class TransactionManager 
{
	private Stack<ITransaction> executedTrans = new Stack<ITransaction>();
	
	/**
	 * Performs the call to execute the transaction being requested and stores it.
	 * 
	 * @param transaction
	 */
	public void sentTransaction(ITransaction transaction)
	{
		// logging...
		transaction.execute();
		executedTrans.add(transaction);
	}
	
	/**
	 * Performs the the undo or reverting the last transaction requested.
	 */
	public void undoLastTransaction() {
		throw new UnsupportedOperationException("Operation currently not yet supported.");
	}
}
