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
