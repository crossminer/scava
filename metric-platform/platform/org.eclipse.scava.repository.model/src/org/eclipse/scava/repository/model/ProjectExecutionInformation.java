/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ProjectExecutionInformation extends Pongo {
	
	protected List<MetricProviderExecution> metricProviderData = null;
	protected LocalStorage storage = null;
	
	
	public ProjectExecutionInformation() { 
		super();
		dbObject.put("storage", new LocalStorage().getDbObject());
		dbObject.put("metricProviderData", new BasicDBList());
		LASTEXECUTED.setOwningType("org.eclipse.scava.repository.model.ProjectExecutionInformation");
		MONITOR.setOwningType("org.eclipse.scava.repository.model.ProjectExecutionInformation");
		INERRORSTATE.setOwningType("org.eclipse.scava.repository.model.ProjectExecutionInformation");
	}
	
	public static StringQueryProducer LASTEXECUTED = new StringQueryProducer("lastExecuted"); 
	public static StringQueryProducer MONITOR = new StringQueryProducer("monitor"); 
	public static StringQueryProducer INERRORSTATE = new StringQueryProducer("inErrorState"); 
	
	
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "");
	}
	
	public ProjectExecutionInformation setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted);
		notifyChanged();
		return this;
	}
	public boolean getMonitor() {
		return parseBoolean(dbObject.get("monitor")+"", true);
	}
	
	public ProjectExecutionInformation setMonitor(boolean monitor) {
		dbObject.put("monitor", monitor);
		notifyChanged();
		return this;
	}
	public boolean getInErrorState() {
		return parseBoolean(dbObject.get("inErrorState")+"", false);
	}
	
	public ProjectExecutionInformation setInErrorState(boolean inErrorState) {
		dbObject.put("inErrorState", inErrorState);
		notifyChanged();
		return this;
	}
	
	
	public List<MetricProviderExecution> getMetricProviderData() {
		if (metricProviderData == null) {
			metricProviderData = new PongoList<MetricProviderExecution>(this, "metricProviderData", true);
		}
		return metricProviderData;
	}
	
	
	public LocalStorage getStorage() {
		if (storage == null && dbObject.containsField("storage")) {
			storage = (LocalStorage) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("storage"));
			storage.setContainer(this);
		}
		return storage;
	}
	
	public ProjectExecutionInformation setStorage(LocalStorage storage) {
		if (this.storage != storage) {
			if (storage == null) {
				dbObject.removeField("storage");
			}
			else {
				dbObject.put("storage", storage.getDbObject());
			}
			this.storage = storage;
			notifyChanged();
		}
		return this;
	}
}
