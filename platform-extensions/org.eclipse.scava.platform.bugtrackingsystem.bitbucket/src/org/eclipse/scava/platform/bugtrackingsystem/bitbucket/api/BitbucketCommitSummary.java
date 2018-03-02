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

import java.io.Serializable;

public class BitbucketCommitSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String hash;
	private BitbucketLinks links;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public BitbucketLinks getLinks() {
		return links;
	}
	
	public void setLinks(BitbucketLinks links) {
		this.links = links;
	}
}
