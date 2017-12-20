package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsItem extends Pongo {
	
	public NewsItem() { 
		super();
		TITLE.setOwningType("model.NewsItem");
		BODY.setOwningType("model.NewsItem");
		DATE.setOwningType("model.NewsItem");
		NEWSTYPE.setOwningType("model.NewsItem");
		STATUS.setOwningType("model.NewsItem");
	}
	
	public static StringQueryProducer TITLE = new StringQueryProducer("title"); 
	public static StringQueryProducer BODY = new StringQueryProducer("body"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer NEWSTYPE = new StringQueryProducer("newsType"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public NewsItem setTitle(String title) {
		dbObject.put("title", title);
		notifyChanged();
		return this;
	}
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public NewsItem setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public NewsItem setBody(String uri) {
		dbObject.put("body", uri);
		notifyChanged();
		return this;
	}
	
	public NewsItemType getType() {
		NewsItemType newsType = null;
		try {
			newsType = NewsItemType.valueOf(dbObject.get("newsType")+"");
		}
		catch (Exception ex) {}
		return newsType;
	}
	
	public NewsItem setType(NewsItemType newsType) {
		dbObject.put("newsType", newsType.toString());
		notifyChanged();
		return this;
	}
	
	public NewsItemStatus getStatus() {
		NewsItemStatus status = null;
		try {
			status = NewsItemStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public NewsItem setStatus(NewsItemStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	
}