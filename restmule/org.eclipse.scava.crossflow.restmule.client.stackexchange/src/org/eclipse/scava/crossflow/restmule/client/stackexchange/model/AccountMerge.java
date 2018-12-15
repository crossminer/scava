package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMerge {

	public AccountMerge(){}

	@JsonProperty("new_account_id") 
	private Integer newAccountId;
	
	@JsonProperty("old_account_id") 
	private Integer oldAccountId;
	
	@JsonProperty("merge_date") 
	private Integer mergeDate;
	
	public Integer getNewAccountId() {
		return this.newAccountId;
	}
	
	public Integer getOldAccountId() {
		return this.oldAccountId;
	}
	
	public Integer getMergeDate() {
		return this.mergeDate;
	}
	
	@Override
	public String toString() {
		return "AccountMerge [ "
			+ "newAccountId = " + this.newAccountId + ", "
			+ "oldAccountId = " + this.oldAccountId + ", "
			+ "mergeDate = " + this.mergeDate + ", "
			+ "]"; 
	}	
}	
