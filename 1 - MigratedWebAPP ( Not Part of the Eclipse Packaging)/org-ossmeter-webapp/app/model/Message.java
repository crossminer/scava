package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Message extends GridEntry {
	
	
	
	public Message() { 
		super();
		super.setSuperTypes("model.GridEntry");
		COL.setOwningType("model.Message");
		ROW.setOwningType("model.Message");
		SIZEX.setOwningType("model.Message");
		SIZEY.setOwningType("model.Message");
		TITLE.setOwningType("model.Message");
		BODY.setOwningType("model.Message");
	}
	
	public static NumericalQueryProducer COL = new NumericalQueryProducer("col");
	public static NumericalQueryProducer ROW = new NumericalQueryProducer("row");
	public static NumericalQueryProducer SIZEX = new NumericalQueryProducer("sizeX");
	public static NumericalQueryProducer SIZEY = new NumericalQueryProducer("sizeY");
	public static StringQueryProducer TITLE = new StringQueryProducer("title");
	public static StringQueryProducer BODY = new StringQueryProducer("body");
	
	
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public Message setTitle(String title) {
		dbObject.put("title", title);
		notifyChanged();
		return this;
	}
	
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public Message setBody(String body) {
		dbObject.put("body", body);
		notifyChanged();
		return this;
	}
	
	
}