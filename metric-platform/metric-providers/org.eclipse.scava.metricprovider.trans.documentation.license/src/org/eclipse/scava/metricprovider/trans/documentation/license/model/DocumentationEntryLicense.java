package org.eclipse.scava.metricprovider.trans.documentation.license.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationEntryLicense extends Pongo {
	
	
	
	public DocumentationEntryLicense() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
		LICENSEFOUND.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
		LICENSENAME.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
		LICENSEGROUP.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
		HEADERTYPE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
		SCORE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.license.model.DocumentationEntryLicense");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static StringQueryProducer LICENSEFOUND = new StringQueryProducer("licenseFound"); 
	public static StringQueryProducer LICENSENAME = new StringQueryProducer("licenseName"); 
	public static StringQueryProducer LICENSEGROUP = new StringQueryProducer("licenseGroup"); 
	public static StringQueryProducer HEADERTYPE = new StringQueryProducer("headerType"); 
	public static NumericalQueryProducer SCORE = new NumericalQueryProducer("score");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntryLicense setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntryLicense setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	public boolean getLicenseFound() {
		return parseBoolean(dbObject.get("licenseFound")+"", false);
	}
	
	public DocumentationEntryLicense setLicenseFound(boolean licenseFound) {
		dbObject.put("licenseFound", licenseFound);
		notifyChanged();
		return this;
	}
	public String getLicenseName() {
		return parseString(dbObject.get("licenseName")+"", "");
	}
	
	public DocumentationEntryLicense setLicenseName(String licenseName) {
		dbObject.put("licenseName", licenseName);
		notifyChanged();
		return this;
	}
	public String getLicenseGroup() {
		return parseString(dbObject.get("licenseGroup")+"", "");
	}
	
	public DocumentationEntryLicense setLicenseGroup(String licenseGroup) {
		dbObject.put("licenseGroup", licenseGroup);
		notifyChanged();
		return this;
	}
	public boolean getHeaderType() {
		return parseBoolean(dbObject.get("headerType")+"", false);
	}
	
	public DocumentationEntryLicense setHeaderType(boolean headerType) {
		dbObject.put("headerType", headerType);
		notifyChanged();
		return this;
	}
	public double getScore() {
		return parseDouble(dbObject.get("score")+"", 0.0d);
	}
	
	public DocumentationEntryLicense setScore(double score) {
		dbObject.put("score", score);
		notifyChanged();
		return this;
	}
	
	
	
	
}