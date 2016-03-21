package com.app.vending.controller.transactions;

/**
 * The interface of all transaction objects of the system.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public interface ITransaction
{
	/**
	 * The code that will perform the execution of the transaction being requested.
	 */
	void execute();
}
