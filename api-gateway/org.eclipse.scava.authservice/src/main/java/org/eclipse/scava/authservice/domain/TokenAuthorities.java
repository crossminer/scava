package org.eclipse.scava.authservice.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "token_authorities")
public class TokenAuthorities implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Field("label")
	@Id
	private String label;
	
    @Field("access_token")
	private String accessToken;
    
    @Field("monitoring_authorities")
    private boolean monitoringAuthorities;
    
	public TokenAuthorities() {
	}
	
	public TokenAuthorities(String label, String accessToken, boolean monitoringAuthorities) {
		this.label = label;
		this.accessToken = accessToken;
		this.monitoringAuthorities = monitoringAuthorities;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean getMonitoringAuthorities() {
		return monitoringAuthorities;
	}

	public void setMonitoringAuthorities(boolean monitoringAuthorities) {
		this.monitoringAuthorities = monitoringAuthorities;
	}

	@Override
	public String toString() {
		return "TokenAuthorities [label=" + label + ", accessToken=" + accessToken + ", monitoringAuthorities="
				+ monitoringAuthorities + "]";
	}
	
}
