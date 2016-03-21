package com.app.vending.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.app.vending.controller.VendingMachineFacadeImpl;

/**
 * The dialog box for the maintenance functionalities of the vending machine application.
 * 
 * @author Saclot, Louel Albert D.
 *
 */
public class MaintenanceDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel = new JPanel();
	private JLabel notificationBar = new JLabel();
	
	private JButton restock = new JButton();
	private JButton increase = new JButton();
	private JButton decrease = new JButton();
	private JButton generateReport = new JButton();
	private JButton close = new JButton();
	
	private VendingMachineFacadeImpl vmf;
	private VendingMachineView parent;

	public MaintenanceDialog(final VendingMachineView parent, final VendingMachineFacadeImpl vmf)
	{
		super(parent);
		this.parent = parent;
		this.vmf = vmf;
		
		setTitle("Maintenance Options");
		getContentPane().setLayout(null);
		setSize(250, 367);
		setVisible(false);
		setResizable(false);
		
		panel.setLayout(null);
		panel.setBounds(0, 0, 300, 380);
		getContentPane().add(BorderLayout.CENTER, panel);
		
		notificationBar.setBounds(20, 20, 280, 15);
		notificationBar.setText("Maintenance");
		notificationBar.setFont(notificationBar.getFont().deriveFont(12.0f));
		panel.add(notificationBar);
		
		restock.setText("Restock All Inventory");
		restock.setBounds(10, 50, 280, 50);
		panel.add(restock);
		
		increase.setText("Increase All Prices by $0.05");
		increase.setBounds(10, 100, 280, 50);
		panel.add(increase);
		
		decrease.setText("Decrease All Prices by $0.05");
		decrease.setBounds(10, 150, 280, 50);
		panel.add(decrease);
		
		generateReport.setText("Generate Report");
		generateReport.setBounds(10, 200, 280, 50);
		panel.add(generateReport);
		
		close.setText("Close");
		close.setActionCommand("OK");
		close.setBounds(10, 280, 280, 50);
		panel.add(close);
		
		SymAction lSymAction = new SymAction();
		restock.addActionListener(lSymAction);
		increase.addActionListener(lSymAction);
		decrease.addActionListener(lSymAction);
		generateReport.addActionListener(lSymAction);
		close.addActionListener(lSymAction);
	}
	
	void restockAllInventory(ActionEvent event) {
		vmf.performRestockAll();
		notificationBar.setText("Restock Process has been completed.");
	}
	
	void increaseAllPrices(ActionEvent event) {
		vmf.increaseAllPrices(new BigDecimal("0.05"));
		notificationBar.setText("Price Increase Process has been completed.");
		parent.updateProductDisplay();
	}
	
	void decreaseAllPrices(ActionEvent event) {
		vmf.decreaseAllPrices(new BigDecimal("0.05"));
		notificationBar.setText("Price Decrease Process has been completed.");
		parent.updateProductDisplay();
	}
	
	void generateReport(ActionEvent event) {
		String report = vmf.generateStockReport();
		notificationBar.setText("Report Generation is complete.");
		JOptionPane.showMessageDialog(this, report, "Today's Report - This will be appended to the REPORT.TXT file.", JOptionPane.INFORMATION_MESSAGE);
	}

	void closeActionPerformed(ActionEvent event) {
		dispose();
	}

	class SymAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object object = event.getSource();
			if (object == restock) {
				restockAllInventory(event);
			} else if (object == increase) {
				increaseAllPrices(event);
			} else if (object == decrease) {
				decreaseAllPrices(event);
			} else if (object == generateReport) {
				generateReport(event);
			} else {
				closeActionPerformed(event);
			}
		}
	}
	
}
