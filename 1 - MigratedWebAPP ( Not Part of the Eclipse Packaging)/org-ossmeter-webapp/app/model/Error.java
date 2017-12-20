package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Error extends Pongo {
	
	public Error() { 
		super();
		EMAIL.setOwningType("model.Error");
		DATE.setOwningType("model.Error");
	}
	
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date");  
	
	
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public Error setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getUri() {
		return parseString(dbObject.get("uri")+"", "");
	}
	
	public Error setUri(String uri) {
		dbObject.put("uri", uri);
		notifyChanged();
		return this;
	}
	public String getRemoteAddress() {
		return parseString(dbObject.get("remoteAddress")+"", "");
	}
	
	public Error setRemoteAddress(String remoteAddress) {
		dbObject.put("remoteAddress", remoteAddress);
		notifyChanged();
		return this;
	}
	public String getMethod() {
		return parseString(dbObject.get("method")+"", "");
	}
	
	public Error setMethod(String method) {
		dbObject.put("method", method);
		notifyChanged();
		return this;
	}
	public String getMessage() {
		return parseString(dbObject.get("message")+"", "");
	}
	
	public Error setMessage(String message) {
		dbObject.put("message", message);
		notifyChanged();
		return this;
	}
	
}