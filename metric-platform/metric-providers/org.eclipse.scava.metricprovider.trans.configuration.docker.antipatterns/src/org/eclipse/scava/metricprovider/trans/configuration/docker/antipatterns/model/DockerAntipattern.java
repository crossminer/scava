package org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model;

import java.util.Date;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;

public class DockerAntipattern extends Pongo {
	
	public DockerAntipattern() { 
		super();
		SMELLNAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
		REASON.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
		CODE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
		FILENAME.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
		LINE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
		COMMIT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
		DATE.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model.DockerAntipattern");
	}
	
	public static StringQueryProducer SMELLNAME = new StringQueryProducer("smellName"); 
	public static StringQueryProducer REASON = new StringQueryProducer("reason");
	public static StringQueryProducer CODE = new StringQueryProducer("code");
	public static StringQueryProducer FILENAME = new StringQueryProducer("fileName");
	public static StringQueryProducer LINE = new StringQueryProducer("line");
	public static StringQueryProducer COMMIT = new StringQueryProducer("commit");
	public static StringQueryProducer DATE = new StringQueryProducer("date");
	
	
	
	
	public String getSmellName() {
		return parseString(dbObject.get("smellName")+"", "");
	}
	
	public DockerAntipattern setSmellName(String smellName) {
		dbObject.put("smellName", smellName);
		notifyChanged();
		return this;
	}
	
	public String getReason() {
		return parseString(dbObject.get("reason")+"", "");
	}
	
	public DockerAntipattern setReason(String reason) {
		dbObject.put("reason", reason);
		notifyChanged();
		return this;
	}
	
	public String getCode() {
		return parseString(dbObject.get("code")+"", "");
	}
	
	public DockerAntipattern setCode(String code) {
		dbObject.put("code", code);
		notifyChanged();
		return this;
	}
	
	public String getFileName() {
		return parseString(dbObject.get("fileName")+"", "");
	}
	
	public DockerAntipattern setFileName(String fileName) {
		dbObject.put("fileName", fileName);
		notifyChanged();
		return this;
	}
	
	public String getLine() {
		return parseString(dbObject.get("line")+"", "");
	}
	
	public DockerAntipattern setLine(String line) {
		dbObject.put("line", line);
		notifyChanged();
		return this;
	}
	
	public String getCommit() {
		return parseString(dbObject.get("commit")+"", "");
	}
	
	public DockerAntipattern setCommit(String commit) {
		dbObject.put("commit", commit);
		notifyChanged();
		return this;
	}
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public DockerAntipattern setDate(Date date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}

}
