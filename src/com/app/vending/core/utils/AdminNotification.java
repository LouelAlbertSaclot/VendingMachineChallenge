package com.app.vending.core.utils;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

/**
 * This is just a mock implementation of an email functionality for the
 * application on specific events such as an out of stock event. This is an
 * implementation of the Observer pattern.
 * 
 * @author Saclot, Louel Albert D.
 * 
 */
public class AdminNotification implements Observer {
	
	private final String email;

	public AdminNotification(String email) {
		this.email = email;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		JOptionPane.showMessageDialog(null, 
				"Product out of stock! Email notification was sent to " + email + "."
				+ "\nThis normally done quietly but to show it works, I am showing a dialog box.", 
				"Mock Email being Sent", JOptionPane.ERROR_MESSAGE);
	}

}
