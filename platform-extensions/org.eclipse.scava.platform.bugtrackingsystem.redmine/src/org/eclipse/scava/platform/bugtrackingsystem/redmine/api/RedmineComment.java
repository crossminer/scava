/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.redmine.api;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class RedmineComment extends BugTrackingSystemComment {

	private static final long serialVersionUID = 1L;

	private Integer creatorId;
	private List<RedmineCommentDetails> details = new ArrayList<RedmineCommentDetails>();

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public List<RedmineCommentDetails> getDetails() {
		return details;
	}

}
