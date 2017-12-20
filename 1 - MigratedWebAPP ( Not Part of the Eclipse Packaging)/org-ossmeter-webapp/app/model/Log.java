package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Log extends Pongo {
	
	public Log() { 
		super();
		USER.setOwningType("model.Log");
		DATE.setOwningType("model.Log");
		URI.setOwningType("model.Log");
	}
	
	public static StringQueryProducer USER = new StringQueryProducer("user"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	
	
	public String getUser() {
		return parseString(dbObject.get("user")+"", "");
	}
	
	public Log setUser(String user) {
		dbObject.put("user", user);
		notifyChanged();
		return this;
	}
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public Log setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getUri() {
		return parseString(dbObject.get("uri")+"", "");
	}
	
	public Log setUri(String uri) {
		dbObject.put("uri", uri);
		notifyChanged();
		return this;
	}
	
	
	
	
}