package org.eclipse.scava.crossflow.runtime.utils;

import org.eclipse.scava.crossflow.runtime.BuiltinStreamConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Log Consumer that prints to console.
 * 
 * @author Jon Co <jc9596@york.ac.uk>
 *
 */
public class DefaultLogConsumer implements BuiltinStreamConsumer<LogMessage> {

	private static final Logger logger = LoggerFactory.getLogger(DefaultLogConsumer.class);

	@Override
	public void consume(LogMessage t) {
		switch (t.getLevel()) {
		case INFO:
			logger.info(t.toString());
			break;
		case WARNING:
			logger.warn(t.toString());
			break;
		case ERROR:
			logger.error(t.toString());
			break;
		case DEBUG:
			logger.debug(t.toString());
			break;
		}
	}

}
