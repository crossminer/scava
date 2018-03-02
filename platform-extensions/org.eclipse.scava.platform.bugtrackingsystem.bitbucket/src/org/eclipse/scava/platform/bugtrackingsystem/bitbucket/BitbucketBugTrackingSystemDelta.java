/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api.BitbucketPullRequest;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;


public class BitbucketBugTrackingSystemDelta extends BugTrackingSystemDelta {

	private static final long serialVersionUID = 1L;
	
	private List<BitbucketPullRequest> pullRequests = new ArrayList<BitbucketPullRequest>();

	public List<BitbucketPullRequest> getPullRequests() {
		return pullRequests;
	}
	
}
