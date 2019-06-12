package org.eclipse.scava.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.Date;

public class CommunicationChannelDocumentation implements Serializable {

	private static final long serialVersionUID = 920092867291559843L;
	
	private String url;
	private Date dateDelta;
	private Date nextExecutionDate;
	private String username;
	private String password;
	private String loginURL;
	private String usernameFieldName;
	private String passwordFieldName;
	private boolean loginNeeded = false;
	
	public boolean isLoginNeeded() {
		return loginNeeded;
	}
	public void setLoginNeeded(boolean loginNeeded) {
		this.loginNeeded = loginNeeded;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginURL() {
		return loginURL;
	}
	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}
	public String getUsernameFieldName() {
		return usernameFieldName;
	}
	public void setUsernameFieldName(String usernameFieldName) {
		this.usernameFieldName = usernameFieldName;
	}
	public String getPasswordFieldName() {
		return passwordFieldName;
	}
	public void setPasswordFieldName(String passwordFieldName) {
		this.passwordFieldName = passwordFieldName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getDateDelta() {
		return dateDelta;
	}
	public void setDateDelta(Date dateDelta) {
		this.dateDelta = dateDelta;
	}
	public Date getNextExecutionDate() {
		return nextExecutionDate;
	}
	public void setNextExecutionDate(Date nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
