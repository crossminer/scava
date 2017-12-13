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

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitbucketRepositorySummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("full_name")
	private String fullName;
	private String name;
	private BitbucketLinks links;

	public String getFullName() {
		return fullName;
	}

	public String getName() {
		return name;
	}

	public BitbucketLinks getLinks() {
		return links;
	}
}
