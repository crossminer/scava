package org.eclipse.scava.repository.model.cc.irc;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Irc extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public Irc() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.irc.CommunicationChannel");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.cc.irc.Irc");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.cc.irc.Irc");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.cc.irc.Irc");
		NAME.setOwningType("org.eclipse.scava.repository.model.cc.irc.Irc");
		COMPRESSEDFILEEXTENSION.setOwningType("org.eclipse.scava.repository.model.cc.irc.Irc");
	}
	
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer COMPRESSEDFILEEXTENSION = new StringQueryProducer("compressedFileExtension"); 
	
	
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public Irc setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public Irc setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Irc setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Irc setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getCompressedFileExtension() {
		return parseString(dbObject.get("compressedFileExtension")+"", "");
	}
	
	public Irc setCompressedFileExtension(String compressedFileExtension) {
		dbObject.put("compressedFileExtension", compressedFileExtension);
		notifyChanged();
		return this;
	}

	@Override
	public String getCommunicationChannelType() {
		
		return "IRC";
	}

	@Override
	public String getInstanceId() {
		
		return getUrl();
	}
	
	
	
	
}