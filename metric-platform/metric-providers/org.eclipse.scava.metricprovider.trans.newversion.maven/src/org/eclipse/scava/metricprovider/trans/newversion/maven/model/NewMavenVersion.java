package org.eclipse.scava.metricprovider.trans.newversion.maven.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class NewMavenVersion extends Pongo {
	
	public NewMavenVersion() { 
		super();
		PACKAGENAME.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewMavenVersion");
		OLDVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewMavenVersion");
		NEWVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewMavenVersion");
	}
	
	public static StringQueryProducer PACKAGENAME = new StringQueryProducer("packageName"); 
	public static StringQueryProducer OLDVERSION = new StringQueryProducer("oldVersion");
	public static StringQueryProducer NEWVERSION = new StringQueryProducer("newVersion");
	
	
	
	
	public String getPackageName() {
		return parseString(dbObject.get("packageName")+"", "");
	}
	
	public NewMavenVersion setPackageName(String packageName) {
		dbObject.put("packageName", packageName);
		notifyChanged();
		return this;
	}
	
	public String getOldVersion() {
		return parseString(dbObject.get("oldVersion")+"", "");
	}
	
	public NewMavenVersion setOldVersion(String oldVersion) {
		dbObject.put("oldVersion", oldVersion);
		notifyChanged();
		return this;
	}
	
	public String getNewVersion() {
		return parseString(dbObject.get("newVersion")+"", "");
	}
	
	public NewMavenVersion setNewVersion(String newVersion) {
		dbObject.put("newVersion", newVersion);
		notifyChanged();
		return this;
	}
}
