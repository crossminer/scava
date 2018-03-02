/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.bugtrackingsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.repository.model.BugTrackingSystem;


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
