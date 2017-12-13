/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.logging;

import org.apache.log4j.Logger;

public class OssmeterLogger extends Logger {

	public static final String DEFAULT_PATTERN = "%-5p [%c] (%d{HH:mm:ss}): %m%n";
	public static final String MONGODB_APPENDER_NAME = "MongoDB";
	
	protected OssmeterLogger(String name) {
		super(name);
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name, OssmeterLoggerFactory.getInstance());
	}
}
