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


public class RedmineExtendedSearchResult extends RedmineSearchResult {
	private List<RedmineIssue> issues = new ArrayList<RedmineIssue>();

	public List<RedmineIssue> getIssues() {
		return issues;
	}
}
