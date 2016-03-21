package com.app.vending.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import com.app.vending.controller.exceptions.NotEnoughBalanceException;
import com.app.vending.controller.exceptions.OutOfStockException;
import com.app.vending.controller.transactions.ITransaction;
import com.app.vending.controller.transactions.TransactionType;
import com.app.vending.core.model.Product;
import com.app.vending.core.model.ProductContainer;
import com.app.vending.core.utils.TransactionFactory;

/**
 * This is the main entry point for the back-end codes for this vending
 * application. The Facade design pattern was used here as to make it simpler to
 * integrate it to the UI codes. When making a transaction such as adding money
 * to the system, there are several steps to perform here but the user should
 * not need to know about them thus the Facade pattern was appropriate.
 * 
 * Also, the approach generally done here was a test driven approach. Please
 * also see the configuration file for the initial setup of the application.
 * 
 * @author Saclot, Louel Albert D.
 * 
 */
public class VendingMachineFacadeImpl implements VendingMachineFacade {
	
	private static final String DENOMINATION = "denomination";
	private static final String ADMIN_EMAIL  = "adminemail";
	
	private TransactionManager tm = new TransactionManager();
	private CoreManager        cm = new CoreManager();
	private TransactionType    type  = new TransactionType();
	
	/**
	 * Initial setup of data is being read off of the config.properties file.
	 */
	public VendingMachineFacadeImpl() {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("config/config.properties"));
			for (String key : properties.stringPropertyNames()) {
				
				String value = properties.getProperty(key);
				String[] prop = value.split("\\|");
				if (key.equals(DENOMINATION)) {
					cm.getCoreBalance().setDenomination(prop); // setup denomination
				} else if (key.equals(ADMIN_EMAIL)) {
					cm.setAdminNotification(value); // setup observer
				} else {
					cm.addSellableProduct(new Product(prop[0], prop[1], prop[2], new BigDecimal(prop[3])));
				}
			}
		} catch (IOException e) {
			// logging...
			e.printStackTrace();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addToBalance(final BigDecimal amount) {
		
		type.setTransactionType(TransactionType.Type.UPDATE_BALANCE);
		type.setParameter(amount.toString());
		
		ITransaction transaction = TransactionFactory.createTransaction(cm, type);
		tm.sentTransaction(transaction);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product dispenseProduct(final String code) 
			throws NotEnoughBalanceException, OutOfStockException {
		
		type.setTransactionType(TransactionType.Type.DISPENSE_PRODUCT);
		type.setParameter(code);
		
		ITransaction transaction = TransactionFactory.createTransaction(cm, type);
		tm.sentTransaction(transaction);
		
		return cm.getCoreBalance().dispenseProduct();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BigDecimal> dispenseChange() {
		
		type.setTransactionType(TransactionType.Type.DISPENSE_CHANGE);
		ITransaction transaction = TransactionFactory.createTransaction(cm, type);
		tm.sentTransaction(transaction);
		
		return cm.getCoreBalance().dispenseChange();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getCurrentBalance() {
		return cm.getCoreBalance().getBalance();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductContainer> getInventory() {
		return cm.getContainers();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFormattedBalance() {
		Locale locale = new Locale("en", "US", "USD");
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		return fmt.format(getCurrentBalance().doubleValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getProductCodesList() {
		return cm.getAllProductCodes();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getProductPrice(final String code) {
		return cm.getProductPrice(code);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performRestockAll() {
		cm.performRestockAll();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void performRestockContainer(final String code) {
		cm.performRestockContainer(code);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void increaseAllPrices(final BigDecimal increase) {
		cm.increaseAllPrices(increase);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void increaseProductPrice(final String code, final BigDecimal increase) {
		cm.increasePrice(code, increase);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void decreaseAllPrices(final BigDecimal decrease) {
		cm.decreaseAllPrices(decrease);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void decreaseProductPrice(final String code, final BigDecimal decrease) {
		cm.decreasePrice(code, decrease);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateStockReport() {
		return cm.generateReport();
	}
	
}
