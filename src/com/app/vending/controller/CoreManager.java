package com.app.vending.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.vending.core.model.CoreBalance;
import com.app.vending.core.model.Product;
import com.app.vending.core.model.ProductContainer;
import com.app.vending.core.utils.AdminNotification;

/**
 * Manages any activity related to the data/models of the application. This is
 * the entry point for all maintenance related functionalities.
 * 
 * @author Saclot, Louel Albert D.
 * 
 */
public class CoreManager 
{
	private final static int RESTOCK_SIZE = 4;
	private Map<String, ProductContainer> productGrid = new HashMap<String, ProductContainer>();
	private CoreBalance balance = new CoreBalance();
	private AdminNotification adminNotification = new AdminNotification("admin@test.com");
	
	public CoreManager() { }
	
	public void setAdminNotification(final String email) {
		adminNotification = new AdminNotification(email);
	}
	
	public AdminNotification getAdminNotification() {
		return adminNotification;
	}

	/**
	 * Adds the product to the application. It will be stored based on the code of the product.
	 * 
	 * @param product The item to be stored and made available for the vending application.
	 */
	public void addSellableProduct(final Product product) {
		
		String key = product.getCode();
		if (productGrid.containsKey(key)) {
			productGrid.get(key).addProduct(product);
		} else {
			ProductContainer container = new ProductContainer(RESTOCK_SIZE);
			container.addProduct(product);
			container.addObserver(adminNotification);
			productGrid.put(key, container);
		}
	}

	/**
	 * Returns the object that contains the general status of the application.
	 * 
	 * @return The CoreBalance
	 */
	public CoreBalance getCoreBalance() {
		return balance;
	}

	/**
	 * The actual code that returns the product codes.
	 * 
	 * @return The codes of the products available in the vending application.
	 */
	public Set<String> getAllProductCodes() {
		return productGrid.keySet();
	}
	
	/**
	 * The actual code that returns all the product containers of the application.
	 * 
	 * @return The list of product containers.
	 */
	public List<ProductContainer> getContainers() {
		List<ProductContainer> list = new ArrayList<ProductContainer>();
		for (String code : getAllProductCodes()) {
			list.add(productGrid.get(code));
		}
		
		return list;
	}
	
	/**
	 * Returns the price the product being requested.
	 * 
	 * @param code The code of the product.
	 * @return The price of the product.
	 */
	public BigDecimal getProductPrice(final String code) {
		return productGrid.get(code).getProductPrice();
	}
	
	/**
	 * Calls the dispense function of the container that holds the product being requested.
	 * 
	 * @param code The product requested to be dispensed.
	 * @return The product
	 * @throws EmptyStackException
	 */
	public Product dispenseProduct(final String code) throws EmptyStackException {
		return productGrid.get(code).dispenseProduct();
	}
	
	/**
	 * Calls the re-stock functionality for all products.
	 */
	public void performRestockAll() {
		for (String code : getAllProductCodes()) {
			performRestockContainer(code);
		}
	}
	
	/**
	 * Calls the increase functionality for all products.
	 * 
	 * @param increase The amount to increase the price with.
	 */
	public void increaseAllPrices(final BigDecimal increase) {
		for (String code : getAllProductCodes()) {
			increasePrice(code, increase);
		}
	}
	
	/**
	 * Calls the decrease functionality for all products.
	 * 
	 * @param decrease The amount to decrease the price with.
	 */
	public void decreaseAllPrices(BigDecimal decrease) {
		for (String code : getAllProductCodes()) {
			decreasePrice(code, decrease);
		}
	}
	
	/**
	 * Calls the re-stock functionality for the specific product.
	 * 
	 * @param code The product to re-stock.
	 */
	public void performRestockContainer(final String code) {
		productGrid.get(code).restockProduct();
	}

	/**
	 * Calls the increase functionality for the specific product.
	 * 
	 * @param code The product to increase the price.
	 * @param increase The amount to increase the price with.
	 */
	public void increasePrice(final String code, final BigDecimal increase) {
		productGrid.get(code).increasePrice(increase);
	}

	/**
	 * Calls the decrease functionality for the specific product.
	 * 
	 * @param code The product to decrease the price.
	 * @param decrease The amount to decrease the price with.
	 */
	public void decreasePrice(String code, BigDecimal decrease) {
		productGrid.get(code).decreasePrice(decrease);
	}
	
	/**
	 * Consolidates all the reports of the product containers into one report
	 * file. The approach done was more of a delegation method. This will append
	 * the result as well to the report.txt file.
	 * 
	 * @return The current report of the day.
	 */
	public String generateReport() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		String report = "";
		report += "==========================================================================\n";
		report += "Report Date  : " + dateFormat.format(cal.getTime()) + "\n\n";
		for (String key : getAllProductCodes()) {
			report += productGrid.get(key).generateReport();
		}
		report += "==========================================================================\n\n";
		
		PrintWriter reportFile = null;
		try
		{
			reportFile = new PrintWriter(new BufferedWriter(new FileWriter("report/report.txt", true)));
			reportFile.println(report);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reportFile != null) { reportFile.close(); }
		}

		return report;
	}

}
