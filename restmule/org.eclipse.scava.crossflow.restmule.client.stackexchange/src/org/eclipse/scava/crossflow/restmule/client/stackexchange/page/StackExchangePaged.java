package org.eclipse.scava.crossflow.restmule.client.stackexchange.page;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.crossflow.restmule.core.page.IWrap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Page Wrapper */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackExchangePaged<T> implements IWrap<T> {

	/** Wrapper Properties */
	@JsonProperty("items")
	private List<T> items = new ArrayList<T>();

	@JsonProperty("total")
	private Integer totalCount;

	@JsonProperty("has_more")
	private Boolean incompleteResults;

	@JsonProperty("quota_max")
	private Boolean quotaMax;

	@JsonProperty("quota_remaining")
	private Boolean quotaRemaining;

	/** Wrapper Property Getters */

	@Override
	public Boolean isIncomplete() {
		return incompleteResults;
	}

	@Override
	public List<T> getItems() {
		return items;
	}

	@Override
	public Integer getTotalCount() {
		return totalCount;
	}

}