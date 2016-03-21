package com.app.vending.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.app.vending.controller.VendingMachineFacadeImpl;
import com.app.vending.controller.exceptions.NotEnoughBalanceException;
import com.app.vending.controller.exceptions.OutOfStockException;
import com.app.vending.core.model.Product;
import com.app.vending.core.model.ProductContainer;

/**
 * The main dialog for the vending machine application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class VendingMachineView extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final String DOLLAR_VALUE  = "1.00";
	private static final String QUARTER_VALUE = "0.25";
	private static final String DIME_VALUE    = "0.10";
	private static final String NICKEL_VALUE  = "0.05";
	
	private static final String DIALOG_TITLE = "Simple Vending Application";
	private static final String DIALOG_LABEL = "Balance : ";
	
	private static final String ERROR_MSG_INSUFFICIENT_FUNDS = "Sorry but your balance is not sufficient. Please add more money.";
	private static final String ERROR_MSG_OUT_OF_STOCK = "Sorry but product is out of stock. Please choose a different product.";
	
	private JPanel panel = new JPanel();
	
	private JLabel label = new JLabel(DIALOG_LABEL);
	private JLabel balance = new JLabel();
	private JLabel notificationBar = new JLabel();
	
	private JButton addDollarBtn  = new JButton();
	private JButton addQuarterBtn = new JButton();
	private JButton addDimeBtn    = new JButton();
	private JButton addNickelBtn  = new JButton();
	
	private JButton product1 = new JButton();
	private JButton product2 = new JButton();
	private JButton product3 = new JButton();
	private JButton product4 = new JButton();
	
	private JButton coinReturnBtn  = new JButton();
	private JButton maintenanceBtn = new JButton();
	private JButton exitBtn        = new JButton();
	
	private VendingMachineFacadeImpl vmf = new VendingMachineFacadeImpl();
	private List<ProductContainer> inventory = new ArrayList<ProductContainer>();
	
	private VendingMachineView thisFrame;
	
	private JLabel prod1Lbl = new JLabel();
	private JLabel prod2Lbl = new JLabel();
	private JLabel prod3Lbl = new JLabel();
	private JLabel prod4Lbl = new JLabel();
	
	public VendingMachineView() {
		
		thisFrame = this;
		
		setTitle(DIALOG_TITLE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setSize(600, 325);
		setVisible(false);
		setResizable(false);
		
		panel.setLayout(null);
		panel.setBounds(0, 0, 580, 309);
		getContentPane().add(BorderLayout.CENTER, panel);
		
		inventory = vmf.getInventory();
		product1.setBounds(24, 50, 110, 180);
		product1.setText(inventory.get(0).getName());
		product2.setBounds(134, 50, 110, 180);
		product2.setText(inventory.get(1).getName());
		product3.setBounds(244, 50, 110, 180);
		product3.setText(inventory.get(2).getName());
		product4.setBounds(354, 50, 110, 180);
		product4.setText(inventory.get(3).getName());
		
		panel.add(product1);
		panel.add(product2);
		panel.add(product3);
		panel.add(product4);
		
		prod1Lbl.setBounds(60, 210, 96, 50);
		prod1Lbl.setText(formatUSDCurrency(inventory.get(0).getProductPrice().doubleValue()));
		prod2Lbl.setBounds(170, 210, 96, 50);
		prod2Lbl.setText(formatUSDCurrency(inventory.get(1).getProductPrice().doubleValue()));
		prod3Lbl.setBounds(280, 210, 96, 50);
		prod3Lbl.setText(formatUSDCurrency(inventory.get(2).getProductPrice().doubleValue()));
		prod4Lbl.setBounds(390, 210, 96, 50);
		prod4Lbl.setText(formatUSDCurrency(inventory.get(3).getProductPrice().doubleValue()));
		
		panel.add(prod1Lbl);
		panel.add(prod2Lbl);
		panel.add(prod3Lbl);
		panel.add(prod4Lbl);
		
		label.setBounds(24, 10, 192, 33);
		label.setFont(label.getFont().deriveFont(20.0f));
		balance.setBounds(120, 10, 192, 33);
		balance.setText(vmf.getFormattedBalance());
		balance.setFont(label.getFont().deriveFont(20.0f));
		notificationBar.setBounds(24, 250, 430, 33);
		notificationBar.setFont(notificationBar.getFont().deriveFont(12.0f));
		
		panel.add(label);
		panel.add(balance);
		panel.add(notificationBar);
		
		addDollarBtn.setBounds(488, 10, 96, 31);
		addDollarBtn.setText("Add $1.00");
		addQuarterBtn.setBounds(488, 40, 96, 31);
		addQuarterBtn.setText("Add $0.25");
		addDimeBtn.setBounds(488, 70, 96, 31);
		addDimeBtn.setText("Add $0.10");
		addNickelBtn.setBounds(488, 100, 96, 31);
		addNickelBtn.setText("Add $0.05");
		
		panel.add(addDollarBtn);
		panel.add(addQuarterBtn);
		panel.add(addDimeBtn);
		panel.add(addNickelBtn);
		
		coinReturnBtn.setText("Coin Return");
		coinReturnBtn.setBounds(488, 150, 96, 31);
		
		maintenanceBtn.setText("Maintenance");
		maintenanceBtn.setBounds(488, 180, 96, 31);
		
		panel.add(coinReturnBtn);
		panel.add(maintenanceBtn);		
		
		exitBtn.setText("Exit");
		exitBtn.setBounds(488, 248, 96, 31);
		panel.add(exitBtn);

		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		
		SymAction lSymAction = new SymAction();
		exitBtn.addActionListener(lSymAction);
		addDollarBtn.addActionListener(lSymAction);
		addQuarterBtn.addActionListener(lSymAction);
		addDimeBtn.addActionListener(lSymAction);
		addNickelBtn.addActionListener(lSymAction);
		coinReturnBtn.addActionListener(lSymAction);
		maintenanceBtn.addActionListener(lSymAction);
		product1.addActionListener(lSymAction);
		product2.addActionListener(lSymAction);
		product3.addActionListener(lSymAction);
		product4.addActionListener(lSymAction);
	}

	private void updateBalance(ActionEvent event, final String amt) {
		vmf.addToBalance(new BigDecimal(amt));
		balance.setText(vmf.getFormattedBalance());
		notificationBar.setText("");
	}
    
    private void dispenseCoin(ActionEvent event) {
    	String denomination = "";
    	
		List<BigDecimal> coins = vmf.dispenseChange();
		int counter = coins.size();
		for(BigDecimal coin : coins) {
			denomination += formatUSDCurrency(coin.doubleValue());
			if (counter > 1) {
				counter--; denomination += ", ";
			}
		}
		
		balance.setText(vmf.getFormattedBalance());
		notificationBar.setText("Coins Returned : " + coins.size() + " -> [" + denomination + "]");
	}
    
    private void dispenseProduct(ActionEvent event, final String code) {
    	
		try {
			Product product = vmf.dispenseProduct(code);
			notificationBar.setText("Product Dispensed : " + product.getName() + " | " + product.getDetails() + " | " + formatUSDCurrency(product.getPrice().doubleValue()));
		} catch (NotEnoughBalanceException e) {
			notificationBar.setText(ERROR_MSG_INSUFFICIENT_FUNDS);
		} catch (OutOfStockException e) {
			notificationBar.setText(ERROR_MSG_OUT_OF_STOCK);
		}
		
		balance.setText(vmf.getFormattedBalance());
	}
    
    private void showMaintenanceDialog(ActionEvent event) {
    	
    	String password = JOptionPane.showInputDialog(this, "Enter Admin Password (try -> 'password') : ", "Maintenance Login", JOptionPane.QUESTION_MESSAGE);
    	if (password != null) {
        	if (password.equalsIgnoreCase("password")) {
        		MaintenanceDialog maint = new MaintenanceDialog(thisFrame, vmf);
        		maint.setBounds(300, 20, 300, 380);
        		maint.setVisible(true);
        	} else {
        		JOptionPane.showMessageDialog(null, 
        				"Password was incorrect. Login Failed."
        				+ "\n[...psst...might want to try 'password'... :) ]", 
        				"Maintenance Login Failed", JOptionPane.ERROR_MESSAGE);
        	}
    	}
	}
	
	private void exitApplication() {
		try {
			this.setVisible(false);    // hide the Frame
		    this.dispose();            // free the system resources
		    System.exit(0);            // close the application
		} catch (Exception e) { }
	}
    
	private void vendingView_windowClosing(WindowEvent event) {
		windowClosingInteraction(event);
	}

	private void windowClosingInteraction(WindowEvent event) {
		try {
			this.exitApplication();
		} catch (Exception e) { }
	}
	
    private void exitActionPerformed(ActionEvent event) {
		System.exit(0);
	}
    
    private String formatUSDCurrency(final double amount) {
		Locale locale = new Locale("en", "US", "USD");
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		
		return fmt.format(amount);
	}
    
	public void updateProductDisplay() {
		inventory = vmf.getInventory();
		prod1Lbl.setText(formatUSDCurrency(inventory.get(0).getProductPrice().doubleValue()));
		prod2Lbl.setText(formatUSDCurrency(inventory.get(1).getProductPrice().doubleValue()));
		prod3Lbl.setText(formatUSDCurrency(inventory.get(2).getProductPrice().doubleValue()));
		prod4Lbl.setText(formatUSDCurrency(inventory.get(3).getProductPrice().doubleValue()));
	}
	
	class SymWindow extends WindowAdapter {
		public void windowClosing(WindowEvent event)
		{
			Object object = event.getSource();
			if (object == VendingMachineView.this) {
				vendingView_windowClosing(event);
			} 
		}
	}
	
	class SymAction implements ActionListener {
		public void actionPerformed(ActionEvent event)
		{
			Object object = event.getSource();
			if (object == addDollarBtn) {
				updateBalance(event, DOLLAR_VALUE);
			} else if (object == addQuarterBtn) {
				updateBalance(event, QUARTER_VALUE);
			} else if (object == addDimeBtn) {
				updateBalance(event, DIME_VALUE);
			} else if (object == addNickelBtn) {
				updateBalance(event, NICKEL_VALUE);
			} else if (object == coinReturnBtn) {
				dispenseCoin(event);
			} else if (object == maintenanceBtn) {
				showMaintenanceDialog(event);
			} else if (object == product1) {
				dispenseProduct(event, inventory.get(0).getCode());
			} else if (object == product2) {
				dispenseProduct(event, inventory.get(1).getCode());
			} else if (object == product3) {
				dispenseProduct(event, inventory.get(2).getCode());
			} else if (object == product4) {
				dispenseProduct(event, inventory.get(3).getCode());
			} else {
				exitActionPerformed(event);
			}
		}
	}
}
