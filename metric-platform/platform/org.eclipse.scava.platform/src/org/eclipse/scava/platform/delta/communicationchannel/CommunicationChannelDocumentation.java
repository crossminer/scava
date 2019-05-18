package org.eclipse.scava.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.Date;

public class CommunicationChannelDocumentation implements Serializable {

	private static final long serialVersionUID = 920092867291559843L;
	
	private String url;
	private Date dateDelta;
	private Date nextExecutionDate;
	private String login;
	private String password;
	
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
