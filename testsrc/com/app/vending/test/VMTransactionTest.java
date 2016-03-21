package com.app.vending.test;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.app.vending.controller.VendingMachineFacadeImpl;
import com.app.vending.controller.exceptions.NotEnoughBalanceException;
import com.app.vending.controller.exceptions.OutOfStockException;

/**
 * The JUNIT class that will test the basic transaction functionalities of the Vending Machine Application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class VMTransactionTest extends TestCase {
	
	private VendingMachineFacadeImpl vendingMachineFacade;

	/**
	 * Instantiating the target object.
	 */
	@Before
	public void setUp() throws Exception {
		vendingMachineFacade = new VendingMachineFacadeImpl();
	}

	/**
	 * Tests if the application's balance is updated corrected on transaction request.
	 */
	@Test
	public void testUpdateBalance() {
		assertEquals("Initial Balance must be 0.", 0, vendingMachineFacade.getCurrentBalance().intValue());
		vendingMachineFacade.addToBalance(new BigDecimal(20));
		assertEquals("Balance failed to update correctly.", 20.0d, vendingMachineFacade.getCurrentBalance().doubleValue());
	}
	
	/**
	 * Tests if the application can dispense the minimum # of coins. 
	 */
	@Test
	public void testDispenseChange() {
		vendingMachineFacade.addToBalance(new BigDecimal("1.40"));
		assertEquals("The # of expected coins do not match.", 7, vendingMachineFacade.dispenseChange().size());
		assertEquals("Balance should now be 0.", 0, vendingMachineFacade.getCurrentBalance().intValue());
	}
	
	/**
	 * Tests a successful product dispense transaction.
	 */
	@Test
	public void testDispenseProductSuccess() {
		
		try {
			String code = vendingMachineFacade.getProductCodesList().iterator().next();
			vendingMachineFacade.addToBalance(vendingMachineFacade.getProductPrice(code));
			vendingMachineFacade.addToBalance(BigDecimal.ONE);
		
			assertEquals("The Product Code did not match.", code, vendingMachineFacade.dispenseProduct(code).getCode());
			assertEquals("The balance did not match.", 1, vendingMachineFacade.getCurrentBalance().intValue());
		} catch (Exception e) { assertTrue("No Exception should have occurred.", e == null); }
	}
	
	/**
	 * Tests a unsuccessful product dispense due to insufficient funds.
	 */
	@Test(expected=NotEnoughBalanceException.class)
	public void testNotEnoughBalance() {
		try {
			String code = vendingMachineFacade.getProductCodesList().iterator().next();
			vendingMachineFacade.dispenseProduct(code);
		} catch (Exception ex) {
			assertTrue("Expected Exception is NotEnoughBalanceException", ex instanceof NotEnoughBalanceException);
		}
	}
	
	/**
	 * Tests a unsuccessful product dispense due to out of stock.
	 */
	@Test(expected=OutOfStockException.class)
	public void testOutOfStock() {
		
		try {
			String code = vendingMachineFacade.getProductCodesList().iterator().next();
			vendingMachineFacade.addToBalance(new BigDecimal("100"));
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code); // This on should yield the exception
		} catch (Exception ex) {
			assertTrue("Expected Exception is OutOfStockException", ex instanceof OutOfStockException);
		}
	}

}
