package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Project extends Pongo {
	
	protected List<Tag> tags = null;
	
	
	public Project() { 
		super();
		dbObject.put("tags", new BasicDBList());
		ID.setOwningType("model.Project");
		NAME.setOwningType("model.Project");
		DESCRIPTION.setOwningType("model.Project");
		ANALYSED.setOwningType("model.Project");
		CREATEDBY.setOwningType("model.Project");
		STARS.setOwningType("model.Project");
	}
	
	public static StringQueryProducer ID = new StringQueryProducer("id"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer ANALYSED = new StringQueryProducer("analysed");
	public static NumericalQueryProducer STARS = new NumericalQueryProducer("stars");
	public static StringQueryProducer CREATEDBY = new StringQueryProducer("createdBy"); 

	public String getId() {
		return parseString(dbObject.get("id")+"", "");
	}
	
	public Project setId(String id) {
		dbObject.put("id", id);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Project setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	public int getStars() {
		return parseInteger(dbObject.get("stars")+"", 0);
	}
	public Project setStars(int stars) {
		dbObject.put("stars", stars);
		notifyChanged();
		return this;
	}

	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}

	public Project setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getCreatedBy() {
		return parseString(dbObject.get("createdBy")+"", "");
	}
	
	public Project setCreatedBy(String createdBy) {
		dbObject.put("createdBy", createdBy);
		notifyChanged();
		return this;
	}

	public List<Tag> getTags() {
		if (tags == null) {
			tags = new PongoList<Tag>(this, "tags", true);
		}
		return tags;
	}
	
	public boolean getAnalysed() {
		return parseBoolean(dbObject.get("analysed")+"", false);
	}

	public Project setAnalysed(boolean analysed) {
		dbObject.put("analysed", analysed);
		notifyChanged();
		return this;
	}
	
}