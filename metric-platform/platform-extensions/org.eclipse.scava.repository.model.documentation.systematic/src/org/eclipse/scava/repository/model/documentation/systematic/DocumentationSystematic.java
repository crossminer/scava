package org.eclipse.scava.repository.model.documentation.systematic;

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationSystematic extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public DocumentationSystematic() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.documentation.systematic.CommunicationChannel");
		URL.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		EXECUTIONFREQUENCY.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		LOGINURL.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		USERNAMEFIELDNAME.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		PASSWORDFIELDNAME.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer EXECUTIONFREQUENCY = new NumericalQueryProducer("executionFrequency");
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer LOGINURL = new StringQueryProducer("loginURL"); 
	public static StringQueryProducer USERNAMEFIELDNAME = new StringQueryProducer("usernameFieldName"); 
	public static StringQueryProducer PASSWORDFIELDNAME = new StringQueryProducer("passwordFieldName"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public DocumentationSystematic setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getExecutionFrequency() {
		return parseInteger(dbObject.get("executionFrequency")+"", 0);
	}
	
	public DocumentationSystematic setExecutionFrequency(int executionFrequency) {
		dbObject.put("executionFrequency", executionFrequency);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public DocumentationSystematic setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public DocumentationSystematic setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getLoginURL() {
		return parseString(dbObject.get("loginURL")+"", "");
	}
	
	public DocumentationSystematic setLoginURL(String loginURL) {
		dbObject.put("loginURL", loginURL);
		notifyChanged();
		return this;
	}
	public String getUsernameFieldName() {
		return parseString(dbObject.get("usernameFieldName")+"", "");
	}
	
	public DocumentationSystematic setUsernameFieldName(String usernameFieldName) {
		dbObject.put("usernameFieldName", usernameFieldName);
		notifyChanged();
		return this;
	}
	public String getPasswordFieldName() {
		return parseString(dbObject.get("passwordFieldName")+"", "");
	}
	
	public DocumentationSystematic setPasswordFieldName(String passwordFieldName) {
		dbObject.put("passwordFieldName", passwordFieldName);
		notifyChanged();
		return this;
	}

	@Override
	public String getCommunicationChannelType() {
		return "DocumentationSystematic";
	}

	@Override
	public String getInstanceId() {
		return getUrl();
	}
	
	
	
	
}