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


public class RedmineBasicSearchResult extends RedmineSearchResult {
	private List<Integer> issueIds = new ArrayList<Integer>();

	public List<Integer> getIssueIds() {
		return issueIds;
	}
}
