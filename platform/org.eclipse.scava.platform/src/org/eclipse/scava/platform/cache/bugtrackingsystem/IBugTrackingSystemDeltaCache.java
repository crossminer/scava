/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.cache.bugtrackingsystem;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;

public interface IBugTrackingSystemDeltaCache {
	
	public BugTrackingSystemDelta getCachedDelta(String bugTrackingSystemUrl, Date date);
	
	public void putDelta(String bugTrackingSystemUrl, Date date, BugTrackingSystemDelta delta);
}
