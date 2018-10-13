package org.eclipse.scava.metricprovider.trans.configuration.puppet.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;

public class Metadata extends Pongo {
	
	public Metadata() { 
		super();
		PUPPETFILECOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		CLASSCOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		DEFINECOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		FILERESOURCECOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		PACKAGECOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		SERVICECOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		EXECCOUNT.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
		LOC.setOwningType("org.eclipse.scava.metricprovider.trans.configuration.puppet.model.Smell");
	}
	
	public static NumericalQueryProducer PUPPETFILECOUNT = new NumericalQueryProducer("puppetFileCount"); 
	public static NumericalQueryProducer CLASSCOUNT = new NumericalQueryProducer("classCount");
	public static NumericalQueryProducer DEFINECOUNT = new NumericalQueryProducer("defineCount"); 
	public static NumericalQueryProducer FILERESOURCECOUNT = new NumericalQueryProducer("fileResourceCount"); 
	public static NumericalQueryProducer PACKAGECOUNT = new NumericalQueryProducer("packageCount");
	public static NumericalQueryProducer SERVICECOUNT = new NumericalQueryProducer("serviceCount");
	public static NumericalQueryProducer EXECCOUNT = new NumericalQueryProducer("execCount");
	public static NumericalQueryProducer LOC = new NumericalQueryProducer("loc");
	
	
	
	
	public int getPuppetFileCount() {
		return parseInteger(dbObject.get("puppetFileCount")+"", 0);
	}
	
	public Metadata setPuppetFileCount(int puppetFileCount) {
		dbObject.put("puppetFileCount", puppetFileCount);
		notifyChanged();
		return this;
	}
	
	public int getClassCount() {
		return parseInteger(dbObject.get("classCount")+"", 0);
	}
	
	public Metadata setClassCount(int classCount) {
		dbObject.put("classCount", classCount);
		notifyChanged();
		return this;
	}
	
	public int getDefineCount() {
		return parseInteger(dbObject.get("defineCount")+"", 0);
	}
	
	public Metadata setDefineCount(int defineCount) {
		dbObject.put("defineCount", defineCount);
		notifyChanged();
		return this;
	}
	
	public int getFileResourceCount() {
		return parseInteger(dbObject.get("fileResourceCount")+"", 0);
	}
	
	public Metadata setFileResourceCount(int fileResourceCount) {
		dbObject.put("fileResourceCount", fileResourceCount);
		notifyChanged();
		return this;
	}
	
	public int getPackageCount() {
		return parseInteger(dbObject.get("packageCount")+"", 0);
	}
	
	public Metadata setPackageCount(int packageCount) {
		dbObject.put("packageCount", packageCount);
		notifyChanged();
		return this;
	}
	
	public int getServiceCount() {
		return parseInteger(dbObject.get("serviceCount")+"", 0);
	}
	
	public Metadata setServiceCount(int serviceCount) {
		dbObject.put("serviceCount", serviceCount);
		notifyChanged();
		return this;
	}
	
	public int getExecCount() {
		return parseInteger(dbObject.get("execCount")+"", 0);
	}
	
	public Metadata setExecCount(int execCount) {
		dbObject.put("execCount", execCount);
		notifyChanged();
		return this;
	}
	
	public int getLoc() {
		return parseInteger(dbObject.get("loc")+"", 0);
	}
	
	public Metadata setLoc(int loc) {
		dbObject.put("loc", loc);
		notifyChanged();
		return this;
	}

}
