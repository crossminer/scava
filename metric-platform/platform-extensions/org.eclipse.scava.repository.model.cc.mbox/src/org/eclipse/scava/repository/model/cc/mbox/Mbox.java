package org.eclipse.scava.repository.model.cc.mbox;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Mbox extends org.eclipse.scava.repository.model.CommunicationChannel {
	
	
	
	public Mbox() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.cc.mbox.CommunicationChannel");
		MBOXNAME.setOwningType("org.eclipse.scava.repository.model.cc.mbox.Mbox");
		MBOXDESCRIPTION.setOwningType("org.eclipse.scava.repository.model.cc.mbox.Mbox");
		USERNAME.setOwningType("org.eclipse.scava.repository.model.cc.mbox.Mbox");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.cc.mbox.Mbox");
		COMPRESSEDFILEEXTENSION.setOwningType("org.eclipse.scava.repository.model.cc.mbox.Mbox");
		DUMPDATEFORMAT.setOwningType("org.eclipse.scava.repository.model.cc.mbox.Mbox");
		setUsername("");
		setPassword("");
		setDumpDateFormat("");
	}
	
	public static StringQueryProducer MBOXNAME = new StringQueryProducer("MboxName"); 
	public static StringQueryProducer MBOXDESCRIPTION = new StringQueryProducer("MboxDescription"); 
	public static StringQueryProducer USERNAME = new StringQueryProducer("username"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer COMPRESSEDFILEEXTENSION = new StringQueryProducer("compressedFileExtension"); 
	public static StringQueryProducer DUMPDATEFORMAT = new StringQueryProducer("dumpDateFormat"); 
	
	
	public String getMboxName() {
		return parseString(dbObject.get("MboxName")+"", "");
	}
	
	public Mbox setMboxName(String MboxName) {
		dbObject.put("MboxName", MboxName);
		notifyChanged();
		return this;
	}
	public String getMboxDescription() {
		return parseString(dbObject.get("MboxDescription")+"", "");
	}
	
	public Mbox setMboxDescription(String MboxDescription) {
		dbObject.put("MboxDescription", MboxDescription);
		notifyChanged();
		return this;
	}
	public String getUsername() {
		return parseString(dbObject.get("username")+"", "");
	}
	
	public Mbox setUsername(String username) {
		dbObject.put("username", username);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public Mbox setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getCompressedFileExtension() {
		return parseString(dbObject.get("compressedFileExtension")+"", "");
	}
	
	public Mbox setCompressedFileExtension(String compressedFileExtension) {
		dbObject.put("compressedFileExtension", compressedFileExtension);
		notifyChanged();
		return this;
	}
	public String getDumpDateFormat() {
		return parseString(dbObject.get("dumpDateFormat")+"", "");
	}
	
	public Mbox setDumpDateFormat(String dumpDateFormat) {
		dbObject.put("dumpDateFormat", dumpDateFormat);
		notifyChanged();
		return this;
	}

	@Override
	public String getCommunicationChannelType() {
		return "Mbox";
	}

	@Override
	public String getInstanceId() {
		return getUrl();
	}

	@Override
	public boolean needsLocalStorage() {
		return true;
	}
	
	
	
	
}