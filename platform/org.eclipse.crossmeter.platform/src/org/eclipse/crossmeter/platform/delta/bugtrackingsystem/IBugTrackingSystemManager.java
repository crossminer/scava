/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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
