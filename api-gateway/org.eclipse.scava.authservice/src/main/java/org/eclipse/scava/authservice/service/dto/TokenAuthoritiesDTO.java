package org.eclipse.scava.authservice.service.dto;

import javax.validation.constraints.Size;

/**
 * A DTO representing a tokenAuthorities associated the current Account.
 */
public class TokenAuthoritiesDTO {
	
	@Size(min = 1, max = 50)
	private String label;
	
	@Size(max = 100)
	private String accessToken;
    
    private boolean monitoringAuthorities;
    
	public TokenAuthoritiesDTO() {
		// TODO Auto-generated constructor stub
	}

	public TokenAuthoritiesDTO(String label, String accessToken, boolean monitoringAuthorities) {
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

	public void setHasMotitoringAuthorities(boolean monitoringAuthorities) {
		this.monitoringAuthorities = monitoringAuthorities;
	}
    
}
