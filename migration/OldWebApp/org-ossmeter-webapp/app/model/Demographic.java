package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Demographic extends Pongo {
	
	protected List<DemographicEntry> entries = null;
	
	
	public Demographic() { 
		super();
		dbObject.put("entries", new BasicDBList());
		NAME.setOwningType("model.Demographic");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Demographic setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	public List<DemographicEntry> getEntries() {
		if (entries == null) {
			entries = new PongoList<DemographicEntry>(this, "entries", true);
		}
		return entries;
	}
	
	
}