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
package org.eclipse.crossmeter.platform.vcs.workingcopy.manager;

public class Churn {
	private final String churnItemPath;
	private final int linesAdded;
	private final int linesDeleted;
	
	public Churn(String itemPath, int linesAdded, int linesDeleted) {
		churnItemPath = itemPath;
		this.linesAdded = linesAdded;
		this.linesDeleted = linesDeleted;
	}
	
	public String getPath() {
		return churnItemPath;
	}
	
	public int getLinesAdded() {
		return linesAdded;
	}
	
	public int getLinesDeleted() {
		return linesDeleted;
	}
}
