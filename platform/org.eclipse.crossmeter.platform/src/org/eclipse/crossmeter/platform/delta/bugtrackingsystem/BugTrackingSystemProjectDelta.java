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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.delta.NoManagerFoundException;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;
import org.eclipse.crossmeter.repository.model.Project;

import com.mongodb.DB;


public class BugTrackingSystemProjectDelta {
	
	protected List<BugTrackingSystemDelta> bugTrackingSystemDeltas = 
			new ArrayList<BugTrackingSystemDelta>();
	
	public BugTrackingSystemProjectDelta(DB db, Project project, Date date, 
			IBugTrackingSystemManager bugTrackingSystemManager) throws Exception {
		for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
			try {
				bugTrackingSystemDeltas.add(bugTrackingSystemManager.getDelta(db, bugTrackingSystem, date));
			} catch (NoManagerFoundException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public List<BugTrackingSystemDelta> getBugTrackingSystemDeltas() {
		return bugTrackingSystemDeltas;
	}
	
	public void setRepoDeltas(List<BugTrackingSystemDelta> bugTrackingSystemDeltas) {
		this.bugTrackingSystemDeltas = bugTrackingSystemDeltas;
	}
}
