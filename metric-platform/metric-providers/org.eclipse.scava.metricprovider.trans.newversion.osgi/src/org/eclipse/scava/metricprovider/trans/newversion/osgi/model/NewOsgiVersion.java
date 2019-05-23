package org.eclipse.scava.metricprovider.trans.newversion.osgi.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class NewOsgiVersion extends Pongo {
	
	public NewOsgiVersion() { 
		super();
		PACKAGENAME.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.osgi.model.NewOsgiVersion");
		OLDVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.osgi.model.NewOsgiVersion");
		NEWVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.osgi.model.NewOsgiVersion");
	}
	
	public static StringQueryProducer PACKAGENAME = new StringQueryProducer("packageName"); 
	public static StringQueryProducer OLDVERSION = new StringQueryProducer("oldVersion");
	public static StringQueryProducer NEWVERSION = new StringQueryProducer("newVersion");
	
	
	
	
	public String getPackageName() {
		return parseString(dbObject.get("packageName")+"", "");
	}
	
	public NewOsgiVersion setPackageName(String packageName) {
		dbObject.put("packageName", packageName);
		notifyChanged();
		return this;
	}
	
	public String getOldVersion() {
		return parseString(dbObject.get("oldVersion")+"", "");
	}
	
	public NewOsgiVersion setOldVersion(String oldVersion) {
		dbObject.put("oldVersion", oldVersion);
		notifyChanged();
		return this;
	}
	
	public String getNewVersion() {
		return parseString(dbObject.get("newVersion")+"", "");
	}
	
	public NewOsgiVersion setNewVersion(String newVersion) {
		dbObject.put("newVersion", newVersion);
		notifyChanged();
		return this;
	}
}
