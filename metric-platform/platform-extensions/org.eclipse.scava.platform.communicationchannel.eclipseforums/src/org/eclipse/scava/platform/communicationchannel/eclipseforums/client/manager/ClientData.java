package org.eclipse.scava.platform.communicationchannel.eclipseforums.client.manager;

import okhttp3.OkHttpClient;

public class ClientData {

	private String oAuthToken;
	private String clientSecret;
	private String clientId;
	private Long generatedAt;
	private int expiresIn;
	private String ossmeterID;
	private OkHttpClient client;
	
	public String getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}


	public String getOssmeterID() {
		return ossmeterID;
	}

	public void setOssmeterID(String ossmeterID) {
		this.ossmeterID = ossmeterID;
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


	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(Long generatedAt) {
		this.generatedAt = generatedAt;
	}

	public OkHttpClient getClient() {
		return client;
	}

	public void setClient(OkHttpClient client) {
		this.client = client;
	}

}
