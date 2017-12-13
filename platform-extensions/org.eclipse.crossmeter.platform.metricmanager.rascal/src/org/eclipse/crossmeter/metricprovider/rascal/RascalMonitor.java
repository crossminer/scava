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

import org.eclipse.crossmeter.platform.logging.OssmeterLogger;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.rascalmpl.interpreter.IRascalMonitor;

public class RascalMonitor implements IRascalMonitor {
	private final OssmeterLogger log;

	public RascalMonitor(OssmeterLogger log) {
		this.log = log;
	}
	
	@Override
	public void startJob(String name) {
		log.info(name);
	}

	@Override
	public void startJob(String name, int totalWork) {
		log.info(name);
	}

	@Override
	public void startJob(String name, int workShare, int totalWork) {
		log.info(name);
	}

	@Override
	public void event(String name) {
		log.info("\t" + name);

	}

	@Override
	public void event(String name, int inc) {
		log.info("\t" + name);
	}

	@Override
	public void event(int inc) {
		log.info("\tworked");
	}

	@Override
	public int endJob(boolean succeeded) {
		return 1;
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void todo(int work) {
	}

	@Override
	public void warning(String message, ISourceLocation src) {
		log.warn( message + " at " + src);
	}

}
