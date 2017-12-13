package org.eclipse.crossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class License extends NamedElement {
	
	
	
	public License() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.eclipse.crossmeter.repository.model.License");
		URL.setOwningType("org.eclipse.crossmeter.repository.model.License");
		CONTENT.setOwningType("org.eclipse.crossmeter.repository.model.License");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer CONTENT = new StringQueryProducer("content"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public License setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getContent() {
		return parseString(dbObject.get("content")+"", "");
	}
	
	public License setContent(String content) {
		dbObject.put("content", content);
		notifyChanged();
		return this;
	}
	
	
	
	
}