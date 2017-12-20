package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class EventGroup extends GridEntry {
	
	protected List<Event> events = null;
	
	
	public EventGroup() { 
		super();
		dbObject.put("events", new BasicDBList());
		super.setSuperTypes("model.GridEntry");
		COL.setOwningType("model.EventGroup");
		ROW.setOwningType("model.EventGroup");
		SIZEX.setOwningType("model.EventGroup");
		SIZEY.setOwningType("model.EventGroup");
		NAME.setOwningType("model.EventGroup");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public EventGroup setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	
	
	public List<Event> getEvents() {
		if (events == null) {
			events = new PongoList<Event>(this, "events", true);
		}
		return events;
	}
	
	
}