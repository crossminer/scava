package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class QualityElement extends Pongo {
	
	
	
	public QualityElement() { 
		super();
		IDENTIFIER.setOwningType("model.QualityElement");
		NAME.setOwningType("model.QualityElement");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getIdentifier() {
		return parseString(dbObject.get("identifier")+"", "");
	}
	
	public QualityElement setIdentifier(String identifier) {
		dbObject.put("identifier", identifier);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public QualityElement setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	
	
}