package org.eclipse.crossmeter.metricprovider.trans.githubstars.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Stargazers extends Pongo {
	
	
	
	public Stargazers() { 
		super();
		LOGIN.setOwningType("org.eclipse.crossmeter.metricprovider.trans.githubstars.model.Stargazers");
		DATESTAMP.setOwningType("org.eclipse.crossmeter.metricprovider.trans.githubstars.model.Stargazers");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer DATESTAMP = new StringQueryProducer("datestamp"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public Stargazers setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getDatestamp() {
		return parseString(dbObject.get("datestamp")+"", "");
	}
	
	public Stargazers setDatestamp(String datestamp) {
		dbObject.put("datestamp", datestamp);
		notifyChanged();
		return this;
	}
	
	
	
	
}