package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deployment {

	public Deployment(){}

	@JsonProperty("environment") 
	private Object environment;
	
	@JsonProperty("ref") 
	private String ref;
	
	@JsonProperty("deployable") 
	private Object deployable;
	
	@JsonProperty("iid") 
	private String iid;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("sha") 
	private String sha;
	
	@JsonProperty("user") 
	private Object user;
	
	public Object getEnvironment() {
		return this.environment;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public Object getDeployable() {
		return this.deployable;
	}
	
	public String getIid() {
		return this.iid;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSha() {
		return this.sha;
	}
	
	public Object getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "Deployment [ "
			+ "environment = " + this.environment + ", "
			+ "ref = " + this.ref + ", "
			+ "deployable = " + this.deployable + ", "
			+ "iid = " + this.iid + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "sha = " + this.sha + ", "
			+ "user = " + this.user + ", "
			+ "]"; 
	}	
}	
