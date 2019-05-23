package org.eclipse.scava.metricprovider.trans.newversion.docker.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class NewDockerVersion extends Pongo {
	
	public NewDockerVersion() { 
		super();
		PACKAGENAME.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.docker.model.NewDockerVersion");
		OLDVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.docker.model.NewDockerVersion");
		NEWVERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.docker.model.NewDockerVersion");
	}
	
	public static StringQueryProducer PACKAGENAME = new StringQueryProducer("packageName"); 
	public static StringQueryProducer OLDVERSION = new StringQueryProducer("oldVersion");
	public static StringQueryProducer NEWVERSION = new StringQueryProducer("newVersion");
	
	
	
	
	public String getPackageName() {
		return parseString(dbObject.get("packageName")+"", "");
	}
	
	public NewDockerVersion setPackageName(String packageName) {
		dbObject.put("packageName", packageName);
		notifyChanged();
		return this;
	}
	
	public String getOldVersion() {
		return parseString(dbObject.get("oldVersion")+"", "");
	}
	
	public NewDockerVersion setOldVersion(String oldVersion) {
		dbObject.put("oldVersion", oldVersion);
		notifyChanged();
		return this;
	}
	
	public String getNewVersion() {
		return parseString(dbObject.get("newVersion")+"", "");
	}
	
	public NewDockerVersion setNewVersion(String newVersion) {
		dbObject.put("newVersion", newVersion);
		notifyChanged();
		return this;
	}
}
