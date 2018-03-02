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

import org.apache.log4j.Logger;
import org.eclipse.scava.platform.logging.OssmeterLoggerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.rascalmpl.interpreter.StackTrace;

public class Rasctivator implements BundleActivator {
	private static final Logger LOGGER = OssmeterLoggerFactory.getInstance().makeNewLoggerInstance("rascalLogger");
	private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Rasctivator.context = bundleContext;
	}

	public static void logException(Object message, Throwable cause) {
//		LOGGER.log(Priority.ERROR, message, cause);
		System.err.println(message);
		cause.printStackTrace();
	}
	
	public static void printRascalTrace(StackTrace trace) {
		System.err.println("Rascal stack trace:\n" + trace);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Rasctivator.context = null;
	}

	
}
