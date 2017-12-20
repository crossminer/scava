package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Tag extends Pongo {
	
	protected List<Project> projects = null;
	
	
	public Tag() { 
		super();
		dbObject.put("projects", new BasicDBList());
		NAME.setOwningType("model.Tag");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Tag setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	public List<Project> getProjects() {
		if (projects == null) {
			projects = new PongoList<Project>(this, "projects", true);
		}
		return projects;
	}
	
	
}