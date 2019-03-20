package org.eclipse.scava.crossflow.runtime.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	CrossflowLogger.SEVERITY severity;
	long creationTimeStamp;
	String message;
	String context;

	public LogMessage(CrossflowLogger.SEVERITY level, String msg, String context) {
		severity = level;
		message = msg;
		creationTimeStamp = System.currentTimeMillis();
		this.context = context;
	}

	public CrossflowLogger.SEVERITY getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	@Override
	public String toString() {
		Date d = new Date(creationTimeStamp);
		String f = new SimpleDateFormat().format(d);
		return "Workflow" + (context != null ? ": " + context : "") + " | " + f + "\r\n"
		+ String.format("%" + 10 + "s", severity + ": ") + message;
	}

}
