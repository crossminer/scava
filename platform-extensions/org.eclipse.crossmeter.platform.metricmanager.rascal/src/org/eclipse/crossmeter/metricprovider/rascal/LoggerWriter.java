/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.rascal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.eclipse.crossmeter.platform.logging.OssmeterLogger;

public class LoggerWriter extends PrintWriter {

	public LoggerWriter(boolean error , OssmeterLogger log) {
		super(new LogWriter(error, log));
	}

	private static class LogWriter extends Writer {
		private final OssmeterLogger log;
		private final boolean error;

		public LogWriter(boolean error, OssmeterLogger log) {
			this.log = log;
			this.error = error;
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			String msg = new String(cbuf, off, len).trim();
			
			if (error) {
				log.warn(msg);
			}
			else {
				log.info(msg);
			}
		}

		@Override
		public void flush() throws IOException {
		}

		@Override
		public void close() throws IOException {
		}
	}
}
