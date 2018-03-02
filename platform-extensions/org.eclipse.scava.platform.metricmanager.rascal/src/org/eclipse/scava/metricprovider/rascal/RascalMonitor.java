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

import org.eclipse.scava.platform.logging.OssmeterLogger;
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
