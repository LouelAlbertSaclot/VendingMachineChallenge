package com.app.vending.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.app.vending.controller.exceptions.NotEnoughBalanceException;
import com.app.vending.controller.exceptions.OutOfStockException;
import com.app.vending.core.model.Product;
import com.app.vending.core.model.ProductContainer;

public interface VendingMachineFacade {
	
	/**
	 * Entry point for updating the balance of the vending machine.
	 * 
	 * @param amount The amount to add to the system's balance.
	 */
	public void addToBalance(final BigDecimal amount);
	
	/**
	 * Entry point for dispensing the product being requested.
	 * 
	 * @param code The product code of the item being requested.
	 * @return The product to dispense.
	 * @throws NotEnoughBalanceException
	 * @throws OutOfStockException
	 */
	public Product dispenseProduct(final String code) 
			throws NotEnoughBalanceException, OutOfStockException;
	
	/**
	 * Entry point for dispensing the remaining balance back to the user.
	 * 
	 * @return The coin denominations
	 */
	public List<BigDecimal> dispenseChange();
	
	/**
	 * Entry point for getting the current balance of the vending application.
	 * 
	 * @return The balance
	 */
	public BigDecimal getCurrentBalance();
	
	/**
	 * Entry point for retrieving the overall inventory of the vending application.
	 * 
	 * @return A list of ProductContainer that has the stock of products.
	 */
	public List<ProductContainer> getInventory();
	
	/**
	 * This is just to have a currency formatted value of the balance.
	 * 
	 * @return The formatted balance.
	 */
	public String getFormattedBalance();
	
	/**
	 * The list of product codes being sold by the application.
	 * 
	 * @return The list of product codes.
	 */
	public Set<String> getProductCodesList();
	
	/**
	 * Queries the price of the product based on it's code.
	 * 
	 * @param code The product code
	 * @return The price
	 */
	public BigDecimal getProductPrice(final String code);
	
	/**
	 * Adds or re-stocks all the containers with their appropriate products.
	 */
	public void performRestockAll();
	
	/**
	 * Re-stocks the container of the specified product.
	 *  
	 * @param code The product code to perform re-stocking.
	 */
	public void performRestockContainer(final String code);
	
	/**
	 * Increases all prices of the products in the application.
	 * 
	 * @param increase The amount to be added to the prices.
	 */
	public void increaseAllPrices(final BigDecimal increase);
	
	/**
	 * Increases the price of the specific product.
	 * 
	 * @param code The product to be increased in price.
	 * @param increase The amount added to the price.
	 */
	public void increaseProductPrice(final String code, final BigDecimal increase);
	
	/**
	 * Decreases all prices of the products in the application.
	 * 
	 * @param decrease The amount to be subtracted to the prices.
	 */
	public void decreaseAllPrices(final BigDecimal decrease);
	
	/**
	 * Decreases the price of the specific product.
	 * 
	 * @param code The product to be decreased in price.
	 * @param decrease The amount subtracted to the price.
	 */
	public void decreaseProductPrice(final String code, final BigDecimal decrease);

	/**
	 * Entry point for calling the report generation. The result will be
	 * appended to the report.txt file.
	 * 
	 * @return The current report of the day.
	 */
	public String generateStockReport();

}
