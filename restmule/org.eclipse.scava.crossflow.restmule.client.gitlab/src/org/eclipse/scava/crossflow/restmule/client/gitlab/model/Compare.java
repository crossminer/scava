package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Compare {

	public Compare(){}

	@JsonProperty("compare_timeout") 
	private String compareTimeout;
	
	@JsonProperty("compare_same_ref") 
	private String compareSameRef;
	
	@JsonProperty("commit") 
	private Object commit;
	
	@JsonProperty("commits") 
	private Object commits;
	
	@JsonProperty("diffs") 
	private Object diffs;
	
	public String getCompareTimeout() {
		return this.compareTimeout;
	}
	
	public String getCompareSameRef() {
		return this.compareSameRef;
	}
	
	public Object getCommit() {
		return this.commit;
	}
	
	public Object getCommits() {
		return this.commits;
	}
	
	public Object getDiffs() {
		return this.diffs;
	}
	
	@Override
	public String toString() {
		return "Compare [ "
			+ "compareTimeout = " + this.compareTimeout + ", "
			+ "compareSameRef = " + this.compareSameRef + ", "
			+ "commit = " + this.commit + ", "
			+ "commits = " + this.commits + ", "
			+ "diffs = " + this.diffs + ", "
			+ "]"; 
	}	
}	
