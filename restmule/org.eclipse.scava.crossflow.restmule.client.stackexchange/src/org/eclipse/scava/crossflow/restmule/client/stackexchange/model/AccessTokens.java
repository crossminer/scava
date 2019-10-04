package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokens {

	public AccessTokens(){}

	@JsonProperty("access_token") 
	private String accessToken;
	
	@JsonProperty("account_id") 
	private Integer accountId;
	
	@JsonProperty("expires_on_date") 
	private Integer expiresOnDate;
	
	@JsonProperty("scope") 
	private List<Object> scope = new ArrayList<Object>();
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public Integer getAccountId() {
		return this.accountId;
	}
	
	public Integer getExpiresOnDate() {
		return this.expiresOnDate;
	}
	
	public List<Object> getScope() {
		return this.scope;
	}
	
	@Override
	public String toString() {
		return "AccessTokens [ "
			+ "accessToken = " + this.accessToken + ", "
			+ "accountId = " + this.accountId + ", "
			+ "expiresOnDate = " + this.expiresOnDate + ", "
			+ "scope = " + this.scope + ", "
			+ "]"; 
	}	
}	
