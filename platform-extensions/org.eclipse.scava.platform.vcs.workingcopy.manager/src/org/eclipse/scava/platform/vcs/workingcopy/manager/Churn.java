/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.vcs.workingcopy.manager;

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
