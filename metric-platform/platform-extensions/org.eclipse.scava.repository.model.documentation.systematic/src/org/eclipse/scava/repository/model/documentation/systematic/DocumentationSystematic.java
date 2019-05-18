package org.eclipse.scava.repository.model.documentation.systematic;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationSystematic extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public DocumentationSystematic() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.documentation.systematic.CommunicationChannel");
		URL.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		EXECUTIONFREQUENCY.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer EXECUTIONFREQUENCY = new NumericalQueryProducer("executionFrequency");
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	
	
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
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public DocumentationSystematic setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}

	@Override
	public String getCommunicationChannelType() {
		return "documentationSystematic";
	}

	@Override
	public String getInstanceId() {
		return getUrl();
	}
	
	
	
	
}