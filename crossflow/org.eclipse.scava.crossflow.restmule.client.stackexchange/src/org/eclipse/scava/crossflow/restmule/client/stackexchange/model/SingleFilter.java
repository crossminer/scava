package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleFilter {

	public SingleFilter(){}

	@JsonProperty("filter") 
	private String filter;
	
	@JsonProperty("filter_type") 
	private String filterType;
	
	@JsonProperty("included_fields") 
	private List<Object> includedFields = new ArrayList<Object>();
	
	public String getFilter() {
		return this.filter;
	}
	
	public String getFilterType() {
		return this.filterType;
	}
	
	public List<Object> getIncludedFields() {
		return this.includedFields;
	}
	
	@Override
	public String toString() {
		return "SingleFilter [ "
			+ "filter = " + this.filter + ", "
			+ "filterType = " + this.filterType + ", "
			+ "includedFields = " + this.includedFields + ", "
			+ "]"; 
	}	
}	
