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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.crossmeter.repository.model.BugTrackingSystem;


public class BugTrackingSystemDelta  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	transient protected BugTrackingSystem bugTrackingSystem;
	protected List<BugTrackingSystemBug> newBugs;
	protected List<BugTrackingSystemBug> updatedBugs;
	protected List<BugTrackingSystemComment> comments;
	protected List<BugTrackingSystemAttachment> attachments;

	
	public BugTrackingSystemDelta() {
		super();
		newBugs = new ArrayList<BugTrackingSystemBug>();
		updatedBugs = new ArrayList<BugTrackingSystemBug>();
		comments = new ArrayList<BugTrackingSystemComment>();
		attachments = new ArrayList<BugTrackingSystemAttachment>();
	}

	public BugTrackingSystem getBugTrackingSystem() {
		return bugTrackingSystem;
	}

	public void setBugTrackingSystem(BugTrackingSystem bugTrackingSystem) {
		this.bugTrackingSystem = bugTrackingSystem;
	}

	public List<BugTrackingSystemBug> getNewBugs() {
		return newBugs;
	}
	
	public List<BugTrackingSystemBug> getUpdatedBugs() {
		return updatedBugs;
	}
	
	public List<BugTrackingSystemComment> getComments() {
		return comments;
	}

	public List<BugTrackingSystemAttachment> getAttachments() {
		return attachments;
	}

}
