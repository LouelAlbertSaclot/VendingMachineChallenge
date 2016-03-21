package com.app.vending.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VendingMachineTestSuite extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(VendingMachineTestSuite.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(VMMaintenanceTest.class);
		suite.addTestSuite(VMTransactionTest.class);
		//$JUnit-END$
		return suite;
	}

}
