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


import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RedmineSearchResult {
	@JsonProperty("total_count")
	private Integer totalCount;
	private Integer offset;
	private Integer limit;

	public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getOffset() {
		return offset;
	}

	public Integer getLimit() {
		return limit;
	}
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
