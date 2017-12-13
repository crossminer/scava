/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.tests.logging;

import static org.junit.Assert.assertEquals;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.crossmeter.platform.Configuration;
import org.eclipse.crossmeter.platform.logging.OssmeterLogger;
import org.junit.Before;
import org.junit.Test;

public class OssmeterLoggerTest {

	@Before
	public void reset() {
		
	}
	
	@Test
	public void testNoConfiguration() {
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("crossmeter.logger.test");
		
		logger.warn("I'm warning you.");
		logger.debug("The bus has hit the house.");
	}
	
	@Test
	public void testConsole() {
		
		Properties props = new Properties();
		props.setProperty("log.type", "console");
		
		Configuration.getInstance().setConfigurationProperties(props);
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("crossmeter.logger.test");
		
		int count = 0;
		Enumeration apps = logger.getAllAppenders();
		while (apps.hasMoreElements()) {
			System.out.println(apps.nextElement());
			count++;
		}
		assertEquals(1, count);
		
		logger.warn("I'm warning you.");
		logger.debug("The bus has hit the house.");
	}
	
	@Test
	public void testFile() {

		Properties props = new Properties();
		props.setProperty("log.type", "file");
		props.setProperty("log.file.path", "/tmp/ossmeterlog.log");
		
		Configuration.getInstance().setConfigurationProperties(props);
		
		Logger logger = OssmeterLogger.getLogger("crossmeter.logger.test");
		
		logger.warn("I'm warning you.");
		logger.debug("The bus has hit the house.");
	}
	
	@Test
	public void testRolling() {
		Properties props = new Properties();
		props.setProperty("log.type", "rolling");
		props.setProperty("log.rolling.path", "/tmp/rollinglog.log");
		
		Configuration.getInstance().setConfigurationProperties(props);
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("crossmeter.logger.one");
		
		OssmeterLogger logger2 = (OssmeterLogger) OssmeterLogger.getLogger("crossmeter.logger.one.two");
		
		
		logger2.warn("I'm warning you hard.");
		logger.warn("I'm warning you.");
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("The bus has hit the house.");
		logger2.warn("I'm warning you really ahrd.");
	}

}
