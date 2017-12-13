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
package org.eclipse.crossmeter.platform.delta.bugtrackingsystem;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;

import com.mongodb.DB;

public interface IBugTrackingSystemManager<B extends BugTrackingSystem> {
	
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem);
	
	public BugTrackingSystemDelta getDelta(DB db, B bugTrackingSystem, Date date) throws Exception;

	public Date getFirstDate(DB db, B bugTrackingSystem) throws Exception;
	
	public String getContents(DB db, B bugTrackingSystem, BugTrackingSystemBug bug) throws Exception;
	
	public String getContents(DB db, B bugTrackingSystem, BugTrackingSystemComment comment) throws Exception;

}
