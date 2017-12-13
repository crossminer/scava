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
package org.eclipse.crossmeter.platform.cache.bugtrackingsystem;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public interface IBugTrackingSystemContentsCache {
	
	public String getCachedContents(BugTrackingSystemBug bug);
	
	public String getCachedContents(BugTrackingSystemComment comment);
	
	public void putContents(BugTrackingSystemBug bug, String contents);

	public void putContents(BugTrackingSystemComment comment, String contents);

}
