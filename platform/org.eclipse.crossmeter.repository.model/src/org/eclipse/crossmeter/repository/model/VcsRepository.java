package org.eclipse.crossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class VcsRepository extends NamedElement {
	
	protected List<Person> persons = null;
	
	
	public VcsRepository() { 
		super();
		dbObject.put("persons", new BasicDBList());
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.eclipse.crossmeter.repository.model.VcsRepository");
		CREATED_AT.setOwningType("org.eclipse.crossmeter.repository.model.VcsRepository");
		UPDATED_AT.setOwningType("org.eclipse.crossmeter.repository.model.VcsRepository");
		URL.setOwningType("org.eclipse.crossmeter.repository.model.VcsRepository");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public VcsRepository setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public VcsRepository setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public VcsRepository setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	public List<Person> getPersons() {
		if (persons == null) {
			persons = new PongoList<Person>(this, "persons", false);
		}
		return persons;
	}
	
	
}