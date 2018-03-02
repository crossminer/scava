/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.tests.logging;

import static org.junit.Assert.assertEquals;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.Configuration;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.junit.Before;
import org.junit.Test;

public class OssmeterLoggerTest {

	@Before
	public void reset() {
		
	}
	
	@Test
	public void testNoConfiguration() {
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("scava.logger.test");
		
		logger.warn("I'm warning you.");
		logger.debug("The bus has hit the house.");
	}
	
	@Test
	public void testConsole() {
		
		Properties props = new Properties();
		props.setProperty("log.type", "console");
		
		Configuration.getInstance().setConfigurationProperties(props);
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("scava.logger.test");
		
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
		
		Logger logger = OssmeterLogger.getLogger("scava.logger.test");
		
		logger.warn("I'm warning you.");
		logger.debug("The bus has hit the house.");
	}
	
	@Test
	public void testRolling() {
		Properties props = new Properties();
		props.setProperty("log.type", "rolling");
		props.setProperty("log.rolling.path", "/tmp/rollinglog.log");
		
		Configuration.getInstance().setConfigurationProperties(props);
		
		OssmeterLogger logger = (OssmeterLogger) OssmeterLogger.getLogger("scava.logger.one");
		
		OssmeterLogger logger2 = (OssmeterLogger) OssmeterLogger.getLogger("scava.logger.one.two");
		
		
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
