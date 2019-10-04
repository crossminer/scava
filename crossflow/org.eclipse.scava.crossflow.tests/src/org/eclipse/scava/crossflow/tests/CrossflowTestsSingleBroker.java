package org.eclipse.scava.crossflow.tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CrossflowTestsSingleBroker extends CrossflowTests{

	@BeforeClass
	public static void setUp() {
		WorkflowTests.createBroker = false;
			try {
				new WorkflowTests().startBroker();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@AfterClass
	public static void tearDown() {
			try {
				new WorkflowTests().stopBroker();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
