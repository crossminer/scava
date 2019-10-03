package org.eclipse.scava.crossflow.runtime.utils;

import org.eclipse.scava.crossflow.runtime.Task;
import org.eclipse.scava.crossflow.runtime.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossflowLogger {

	private final Logger logger = LoggerFactory.getLogger(CrossflowLogger.class);
	private boolean prePrint = true;
	private Workflow<?> workflow;

	public CrossflowLogger(Workflow<?> workflow) {
		this.workflow = workflow;
	}

	public void setPrePrint(boolean prePrint) {
		this.prePrint = prePrint;
	}
	
	public boolean isPrePrint() {
		return prePrint;
	}

	public void log(LogLevel level, Task task, String message) {
		final LogMessage m = new LogMessage(workflow)
				.task(task.getClass().getSimpleName())
				.level(level)
				.message(message);
		log(m, task.getWorkflow());
	}

	public void log(LogLevel level, String message) {
		final LogMessage m = new LogMessage(workflow)
				.level(level)
				.message(message);
		log(m, workflow);
	}

	private void log(LogMessage m, Workflow<?> workflow) {
		if (prePrint) {
			switch (m.level) {
			case DEBUG:
				logger.debug(m.toExternalLoggerString());
				break;
			case ERROR:
				logger.error(m.toExternalLoggerString());
				break;
			case INFO:
				logger.info(m.toExternalLoggerString());
				break;
			case WARNING:
				logger.warn(m.toExternalLoggerString());
				break;
			}
		}

		try {
			workflow.getLogTopic().send(m);
		} catch (Exception e) {
			logger.error("Failed to send LogMessage {} to LogTopic", m, e);
		}
	}

}
