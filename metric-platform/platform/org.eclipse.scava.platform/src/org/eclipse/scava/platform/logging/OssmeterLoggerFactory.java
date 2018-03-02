/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.logging;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggerFactory;
import org.eclipse.scava.platform.Configuration;

public class OssmeterLoggerFactory implements LoggerFactory {

	private static OssmeterLoggerFactory instance;
	
	public static OssmeterLoggerFactory getInstance() {
		if (instance ==null) {
			instance = new OssmeterLoggerFactory();
		} 
		return instance;
	}
	
	private OssmeterLoggerFactory() {
		Appender appender = null;
		boolean valid = true;
		try {
			String logType = Configuration.getInstance().getProperty("log.type", "console");
			
			switch (logType) {
				case "console":
					appender = new ConsoleAppender(new PatternLayout(OssmeterLogger.DEFAULT_PATTERN), ConsoleAppender.SYSTEM_OUT);
					break;
				case "file":
					String out = Configuration.getInstance().getProperty("log.file.path", null);
					if (out == null) {
						valid = false;
						System.err.println("log.file.path not specified.");
						break;
					}
					appender = new FileAppender(new PatternLayout(OssmeterLogger.DEFAULT_PATTERN), out);
					break;
				case "rolling":
					String path = Configuration.getInstance().getProperty("log.rolling.path", null);
					
					if (path == null) {
						valid = false;
						System.err.println("log.rolling.path not specified.");
						break;
					}
					
					DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender(new PatternLayout(OssmeterLogger.DEFAULT_PATTERN), path, "'.'yyyy-MM-dd-a");
					dailyRollingFileAppender.setAppend(true);
					dailyRollingFileAppender.setImmediateFlush(true);
					dailyRollingFileAppender.activateOptions();
					appender = dailyRollingFileAppender;
					break;
				default:
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			valid = false;
		}
		if (!valid) {
			System.err.println("Invalid appender specification. Adding console appender as default.");
			appender = new ConsoleAppender(new PatternLayout(OssmeterLogger.DEFAULT_PATTERN), ConsoleAppender.SYSTEM_OUT);
		}
		
		if (appender != null) Logger.getRootLogger().addAppender(appender);
	}
	
	@Override
	public Logger makeNewLoggerInstance(String loggerName) {
		return new OssmeterLogger(loggerName);
	}
}
