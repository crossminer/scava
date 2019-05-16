package org.eclipse.scava.metricprovider.trans.newversion.maven.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class NewMavenVersion extends Pongo {
	
	public NewMavenVersion() { 
		super();
		PACKAGENAME.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewVersion");
		VERSION.setOwningType("org.eclipse.scava.metricprovider.trans.newversion.maven.model.NewVersion");
	}
	
	public static StringQueryProducer PACKAGENAME = new StringQueryProducer("packageName"); 
	public static StringQueryProducer VERSION = new StringQueryProducer("version");
	
	
	
	
	public String getPackageName() {
		return parseString(dbObject.get("packageName")+"", "");
	}
	
	public NewMavenVersion setPackageName(String packageName) {
		dbObject.put("packageName", packageName);
		notifyChanged();
		return this;
	}
	
	public String getVersion() {
		return parseString(dbObject.get("version")+"", "");
	}
	
	public NewMavenVersion setVersion(String version) {
		dbObject.put("version", version);
		notifyChanged();
		return this;
	}
}
