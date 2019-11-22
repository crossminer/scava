package org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager;

import java.util.concurrent.TimeUnit;

public class ClientDataOAuth extends ClientData {

	private String oAuthToken;
	private String clientSecret;
	private String clientId;
	private Long generatedAt;
	private Long expiresIn;
	private Long updatedExpiresIn;
	
	public ClientDataOAuth() {
		super();
		updatedExpiresIn=null;
		generatedAt=null;
		expiresIn=null;
	}
	
	public String getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getExpiresIn() {
		if(updatedExpiresIn!=null)
		{
			long timeElapsed = System.nanoTime()-updatedExpiresIn;
			expiresIn-=TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
		}
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
		this.updatedExpiresIn=System.nanoTime();
	}

	public Long getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(Long generatedAt) {
		this.generatedAt = generatedAt;
	}

}
