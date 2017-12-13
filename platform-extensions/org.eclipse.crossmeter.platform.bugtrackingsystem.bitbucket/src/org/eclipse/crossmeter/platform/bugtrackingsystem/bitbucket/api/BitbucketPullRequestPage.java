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
package org.eclipse.crossmeter.platform.bugtrackingsystem.bitbucket.api;

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
