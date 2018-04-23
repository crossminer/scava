package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MailingListItem extends Pongo {
	
	public MailingListItem() { 
		super();
		EMAIL.setOwningType("model.MailingListItem");
		DATE.setOwningType("model.MailingListItem");
	}
	
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date");  
	
	
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public MailingListItem setEmail(String email) {
		dbObject.put("email", email);
		notifyChanged();
		return this;
	}
	public Date getDate() {
		return parseDate(dbObject.get("date")+"", null);
	}
	
	public MailingListItem setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getUri() {
		return parseString(dbObject.get("uri")+"", "");
	}	
	
}