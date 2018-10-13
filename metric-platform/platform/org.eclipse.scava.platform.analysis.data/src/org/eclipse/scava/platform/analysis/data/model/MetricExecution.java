/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.analysis.data.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MetricExecution extends Pongo {
	
	
	
	public MetricExecution() { 
		super();
		METRICPROVIDERID.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricExecution");
		PROJECTID.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricExecution");
		LASTEXECUTIONDATE.setOwningType("org.eclipse.scava.platform.analysis.data.model.MetricExecution");
	}
	
	public static StringQueryProducer METRICPROVIDERID = new StringQueryProducer("metricProviderId"); 
	public static StringQueryProducer PROJECTID = new StringQueryProducer("projectId"); 
	public static StringQueryProducer LASTEXECUTIONDATE = new StringQueryProducer("lastExecutionDate"); 
	
	
	public String getMetricProviderId() {
		return parseString(dbObject.get("metricProviderId")+"", "");
	}
	
	public MetricExecution setMetricProviderId(String metricProviderId) {
		dbObject.put("metricProviderId", metricProviderId);
		notifyChanged();
		return this;
	}
	public String getProjectId() {
		return parseString(dbObject.get("projectId")+"", "");
	}
	
	public MetricExecution setProjectId(String projectId) {
		dbObject.put("projectId", projectId);
		notifyChanged();
		return this;
	}
	public Date getLastExecutionDate() {
		return parseDate(dbObject.get("lastExecutionDate")+"", null);
	}
	
	public MetricExecution setLastExecutionDate(Date lastExecutionDate) {
		dbObject.put("lastExecutionDate", lastExecutionDate);
		notifyChanged();
		return this;
	}
	
	
	
	
}