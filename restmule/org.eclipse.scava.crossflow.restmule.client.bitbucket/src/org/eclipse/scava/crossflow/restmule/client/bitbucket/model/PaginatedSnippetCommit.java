package org.eclipse.scava.crossflow.restmule.client.bitbucket.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedSnippetCommit {

	public PaginatedSnippetCommit(){}

	@JsonProperty("next") 
	private String next;
	
	@JsonProperty("previous") 
	private String previous;
	
	@JsonProperty("size") 
	private Integer size;
	
	@JsonProperty("page") 
	private Integer page;
	
	@JsonProperty("pagelen") 
	private Integer pagelen;
	
	@JsonProperty("values") 
	private List<Object> values = new ArrayList<Object>();
	
	public String getNext() {
		return this.next;
	}
	
	public String getPrevious() {
		return this.previous;
	}
	
	public Integer getSize() {
		return this.size;
	}
	
	public Integer getPage() {
		return this.page;
	}
	
	public Integer getPagelen() {
		return this.pagelen;
	}
	
	public List<Object> getValues() {
		return this.values;
	}
	
	@Override
	public String toString() {
		return "PaginatedSnippetCommit [ "
			+ "next = " + this.next + ", "
			+ "previous = " + this.previous + ", "
			+ "size = " + this.size + ", "
			+ "page = " + this.page + ", "
			+ "pagelen = " + this.pagelen + ", "
			+ "values = " + this.values + ", "
			+ "]"; 
	}	
}	
