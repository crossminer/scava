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
