package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class Event extends Pongo {
	
	
	// protected region custom-fields-and-methods on begin
	// public Date getDate() {
	// 	Object d = dbObject.get("date");
	// 	if (d == null) return null;
	// 	else return (Date)d;
	// }
	
	// public Event setDate(Date date) {
	// 	dbObject.put("date", date);
	// 	notifyChanged();
	// 	return this;
	// }
	// protected region custom-fields-and-methods end
	
	public Event() { 
		super();
		NAME.setOwningType("model.Event");
		DATE.setOwningType("model.Event");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Event setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public Event setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}