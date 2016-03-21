package com.app.vending.test;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.app.vending.controller.VendingMachineFacadeImpl;
import com.app.vending.controller.exceptions.OutOfStockException;

/**
 * The JUNIT class that will test the basic maintenance functionalities of the Vending Machine Application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class VMMaintenanceTest extends TestCase {
	
	private VendingMachineFacadeImpl vendingMachineFacade;

	/**
	 * Instantiating the target object.
	 */
	@Before
	public void setUp() throws Exception {
		vendingMachineFacade = new VendingMachineFacadeImpl();
	}

	/**
	 * Tests the re-stock functionality.
	 */
	@Test
	public void testRestockFunction() {
		
		String code = vendingMachineFacade.getProductCodesList().iterator().next();
		vendingMachineFacade.addToBalance(new BigDecimal("100"));
		
		try {
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code);
			vendingMachineFacade.dispenseProduct(code); // This on should yield the exception
		} catch (Exception ex) {
			assertTrue("Expected Exception is OutOfStockException", ex instanceof OutOfStockException);
		} finally {
			vendingMachineFacade.performRestockContainer(code);
		}
		
		try {
			assertEquals("The Product Code did not match.", code, vendingMachineFacade.dispenseProduct(code).getCode());
		} catch (Exception e) { assertTrue("No Exception should have occurred.", e == null); }
	}
	
	/**
	 * Tests the price increase functionality.
	 */
	@Test
	public void testPriceIncreaseFunction() {
		
		String code = vendingMachineFacade.getProductCodesList().iterator().next();
		
		double currPrice = vendingMachineFacade.getProductPrice(code).doubleValue();
		vendingMachineFacade.increaseProductPrice(code, new BigDecimal("0.05"));
		double newPrice = vendingMachineFacade.getProductPrice(code).doubleValue();
		
		assertTrue("New Price should be greater.", newPrice > currPrice);
	}
	
	/**
	 * Tests the price decrease functionality.
	 */
	@Test
	public void testPriceDecreaseFunction() {
		
		String code = vendingMachineFacade.getProductCodesList().iterator().next();
		
		double currPrice = vendingMachineFacade.getProductPrice(code).doubleValue();
		vendingMachineFacade.decreaseProductPrice(code, new BigDecimal("0.05"));
		double newPrice = vendingMachineFacade.getProductPrice(code).doubleValue();
		
		assertTrue("New Price should be lesser.", newPrice < currPrice);
	}
	
	/**
	 * Tests the report generation functionality.
	 */
	@Test
	public void testGenerateReport() {
		
		String report = vendingMachineFacade.generateStockReport();
		assertFalse("Report should not be empty.", (report == null || report.equals("")));
		System.out.println(report);
	}

}
