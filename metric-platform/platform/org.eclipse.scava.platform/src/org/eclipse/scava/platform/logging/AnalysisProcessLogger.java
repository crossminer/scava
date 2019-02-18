package org.eclipse.scava.platform.logging;

import org.apache.log4j.Logger;

public class AnalysisProcessLogger extends Logger {

	public static final String DEFAULT_PATTERN = "%-5p [%c] (%d{HH:mm:ss}): %m%n";
	public static final String FILE_APPENDER_NAME = "FILE";
	
	protected AnalysisProcessLogger(String name) {
		super(name);
	}
	
	public static Logger getLogger(String name) {
		return Logger.getLogger(name, AnalysisProcessLoggerFactory.getInstance());
	}

}
