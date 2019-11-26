package org.eclipse.scava.repository.model.importer.dto;

public class Credentials {

	private String authToken;
	private String username;
	private String password;
	private String credentialsId;
	
	public Credentials(String authToken, String username, String password) {
		this.authToken = authToken;
		this.username = username;
		this.password = password;
		this.credentialsId="";
	}
	
	public Credentials(String credentialsId, String authToken, String username, String password) {
		this.authToken = authToken;
		this.username = username;
		this.password = password;
		this.credentialsId = credentialsId;
	}
	
	public String getCredentialsId() {
		return credentialsId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
