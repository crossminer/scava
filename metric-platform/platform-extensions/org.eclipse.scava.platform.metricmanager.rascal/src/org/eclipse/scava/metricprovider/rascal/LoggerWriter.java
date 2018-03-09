/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.eclipse.scava.platform.logging.OssmeterLogger;

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
