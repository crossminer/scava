/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.redmine.api;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

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
