/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.cache.bugtrackingsystem;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public interface IBugTrackingSystemContentsCache {
	
	public String getCachedContents(BugTrackingSystemBug bug);
	
	public String getCachedContents(BugTrackingSystemComment comment);
	
	public void putContents(BugTrackingSystemBug bug, String contents);

	public void putContents(BugTrackingSystemComment comment, String contents);

}
