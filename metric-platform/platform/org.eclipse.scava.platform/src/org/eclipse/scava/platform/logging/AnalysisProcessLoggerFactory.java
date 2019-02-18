package org.eclipse.scava.platform.logging;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggerFactory;
import org.eclipse.scava.platform.Configuration;

public class AnalysisProcessLoggerFactory implements LoggerFactory{
	
	private static AnalysisProcessLoggerFactory instance;
	
	private final static String LOGS_DIRCETORY = "analysisLogs";
	
	private static String affectedProjectStoragePath;
	private static String affectedTaskName;
	
	public static AnalysisProcessLoggerFactory getInstance() {
		if(instance == null) {
			instance = new AnalysisProcessLoggerFactory();
		}
		return instance;
	}
	
	public static String getAffectedProjectStoragePath() {
		return affectedProjectStoragePath;
	}

	public static void setAffectedProjectStoragePath(String affectedProjectName) {
		AnalysisProcessLoggerFactory.affectedProjectStoragePath = affectedProjectName;
	}
	
	public static String getAffectedTaskName() {
		return affectedTaskName;
	}

	public static void setAffectedTaskName(String affectedTaskName) {
		AnalysisProcessLoggerFactory.affectedTaskName = affectedTaskName;
	}

	private AnalysisProcessLoggerFactory() {
		Appender appender = null;
		boolean valid = true;
		String logType = Configuration.getInstance().getProperty("log.type", "file");
		switch (logType) {
			case "console":
				appender = new ConsoleAppender(new PatternLayout(AnalysisProcessLogger.DEFAULT_PATTERN), ConsoleAppender.SYSTEM_OUT);
				break;
				
			case "file":
				try {
					if (!getAffectedProjectStoragePath().equals("") || getAffectedProjectStoragePath() != null) {
						File ld = new File(getAffectedProjectStoragePath(), LOGS_DIRCETORY);
						if (!ld.exists()) {
							ld.mkdirs();
						}
						File logFile = new File(ld +  "/" + getAffectedTaskName() + "-" + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()) + ".log");
						if (!logFile.exists()) {
							logFile.createNewFile();
						} else {
							System.err.println("The file already exists");
						}
						appender = new FileAppender(new PatternLayout(AnalysisProcessLogger.DEFAULT_PATTERN), logFile.getAbsolutePath());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			default:
				break;
		}
		if (!valid) {
			System.err.println("Invalid appender specification. Adding console appender as default.");
			appender = new ConsoleAppender(new PatternLayout(OssmeterLogger.DEFAULT_PATTERN), ConsoleAppender.SYSTEM_OUT);
		}
		
		if (appender != null)
			Logger.getRootLogger().addAppender(appender);
	}
	
	@Override
	public Logger makeNewLoggerInstance(String loggerName) {
		return new AnalysisProcessLogger(loggerName);
	}

}
