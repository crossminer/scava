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
