/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api;

import java.util.List;

public class BitbucketPullRequestPage {
	private String next;
	private int page;
	private int size;
	private List<BitbucketPullRequest> values;
	
	public  List<BitbucketPullRequest> getValues() {
		return values;
	}
	
	public String getNext() {
		return next;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getPage() {
		return page;
	}
}
