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


public class SchedulingInformation extends Pongo {
	
	
	
	public SchedulingInformation() { 
		super();
		STATUS.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		CURRENTDATE.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		WORKERID.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		PROGRESS.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		CURRENTMETRIC.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		EXECUTIONREQUESTDATE.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
		LASTDAILYEXECUTIONDURATION.setOwningType("org.eclipse.scava.platform.analysis.data.model.SchedulingInformation");
	}
	
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer CURRENTDATE = new StringQueryProducer("currentDate"); 
	public static StringQueryProducer WORKERID = new StringQueryProducer("workerId"); 
	public static NumericalQueryProducer PROGRESS = new NumericalQueryProducer("progress");
	public static StringQueryProducer CURRENTMETRIC = new StringQueryProducer("currentMetric"); 
	public static StringQueryProducer EXECUTIONREQUESTDATE = new StringQueryProducer("executionRequestDate"); 
	public static NumericalQueryProducer LASTDAILYEXECUTIONDURATION = new NumericalQueryProducer("lastDailyExecutionDuration");
	
	
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public SchedulingInformation setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public Date getCurrentDate() {
		return parseDate(dbObject.get("currentDate")+"", null);
	}
	
	public SchedulingInformation setCurrentDate(Date currentDate) {
		dbObject.put("currentDate", currentDate);
		notifyChanged();
		return this;
	}
	public String getWorkerId() {
		return parseString(dbObject.get("workerId")+"", "");
	}
	
	public SchedulingInformation setWorkerId(String workerId) {
		dbObject.put("workerId", workerId);
		notifyChanged();
		return this;
	}
	public long getProgress() {
		return parseLong(dbObject.get("progress")+"", 0);
	}
	
	public SchedulingInformation setProgress(long progress) {
		dbObject.put("progress", progress);
		notifyChanged();
		return this;
	}
	public String getCurrentMetric() {
		return parseString(dbObject.get("currentMetric")+"", "");
	}
	
	public SchedulingInformation setCurrentMetric(String currentMetric) {
		dbObject.put("currentMetric", currentMetric);
		notifyChanged();
		return this;
	}
	public Date getExecutionRequestDate() {
		return parseDate(dbObject.get("executionRequestDate")+"", null);
	}
	
	public SchedulingInformation setExecutionRequestDate(Date executionRequestDate) {
		dbObject.put("executionRequestDate", executionRequestDate);
		notifyChanged();
		return this;
	}
	public long getLastDailyExecutionDuration() {
		return parseLong(dbObject.get("lastDailyExecutionDuration")+"", 0);
	}
	
	public SchedulingInformation setLastDailyExecutionDuration(long lastDailyExecutionDuration) {
		dbObject.put("lastDailyExecutionDuration", lastDailyExecutionDuration);
		notifyChanged();
		return this;
	}
	
	
	
	
}