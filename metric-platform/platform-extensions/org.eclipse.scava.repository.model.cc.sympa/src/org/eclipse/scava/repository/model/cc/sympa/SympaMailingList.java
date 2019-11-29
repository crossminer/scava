package org.eclipse.scava.repository.model.cc.sympa;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SympaMailingList extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public SympaMailingList() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.sympa.CommunicationChannel");
		MAILINGLISTNAME.setOwningType("org.eclipse.scava.repository.model.cc.sympa.SympaMailingList");
		MAILINGLISTDESCRIPTION.setOwningType("org.eclipse.scava.repository.model.cc.sympa.SympaMailingList");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.cc.sympa.SympaMailingList");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.cc.sympa.SympaMailingList");
		COMPRESSEDFILEEXTENSION.setOwningType("org.eclipse.scava.repository.model.cc.sympa.SympaMailingList");
		setUsername("");
		setPassword("");
	}
	
	public static StringQueryProducer MAILINGLISTNAME = new StringQueryProducer("MailingListName"); 
	public static StringQueryProducer MAILINGLISTDESCRIPTION = new StringQueryProducer("MailingListDescription"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer COMPRESSEDFILEEXTENSION = new StringQueryProducer("compressedFileExtension"); 
	
	
	public String getMailingListName() {
		return parseString(dbObject.get("MailingListName")+"", "");
	}
	
	public SympaMailingList setMailingListName(String MailingListName) {
		dbObject.put("MailingListName", MailingListName);
		notifyChanged();
		return this;
	}
	public String getMailingListDescription() {
		return parseString(dbObject.get("MailingListDescription")+"", "");
	}
	
	public SympaMailingList setMailingListDescription(String MailingListDescription) {
		dbObject.put("MailingListDescription", MailingListDescription);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public SympaMailingList setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public SympaMailingList setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getCompressedFileExtension() {
		return parseString(dbObject.get("compressedFileExtension")+"", "");
	}
	
	public SympaMailingList setCompressedFileExtension(String compressedFileExtension) {
		dbObject.put("compressedFileExtension", compressedFileExtension);
		notifyChanged();
		return this;
	}

	@Override
	public String getCommunicationChannelType() {
		return "sympa";
	}

	@Override
	public String getInstanceId() {
		return getUrl() + "/" + getMailingListName();
	}
	
	@Override
	public boolean needsLocalStorage() {
		return false;
	}
	
}