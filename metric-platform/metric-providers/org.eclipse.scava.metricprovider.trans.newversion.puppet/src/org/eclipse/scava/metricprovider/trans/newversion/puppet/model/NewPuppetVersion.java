package org.eclipse.scava.metricprovider.trans.newversion.puppet.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class NewPuppetVersion extends Pongo {
	
	public NewPuppetVersion() { 
		super();
		PACKAGENAME.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.puppet.model.NewPuppetVersion");
		OLDVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.puppet.model.NewPuppetVersion");
		NEWVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.puppet.model.NewPuppetVersion");
	}
	
	public static StringQueryProducer PACKAGENAME = new StringQueryProducer("packageName"); 
	public static StringQueryProducer OLDVERSION = new StringQueryProducer("oldVersion");
	public static StringQueryProducer NEWVERSION = new StringQueryProducer("newVersion");
	
	
	
	
	public String getPackageName() {
		return parseString(dbObject.get("packageName")+"", "");
	}
	
	public NewPuppetVersion setPackageName(String packageName) {
		dbObject.put("packageName", packageName);
		notifyChanged();
		return this;
	}
	
	public String getOldVersion() {
		return parseString(dbObject.get("oldVersion")+"", "");
	}
	
	public NewPuppetVersion setOldVersion(String oldVersion) {
		dbObject.put("oldVersion", oldVersion);
		notifyChanged();
		return this;
	}
	
	public String getNewVersion() {
		return parseString(dbObject.get("newVersion")+"", "");
	}
	
	public NewPuppetVersion setNewVersion(String newVersion) {
		dbObject.put("newVersion", newVersion);
		notifyChanged();
		return this;
	}
}
