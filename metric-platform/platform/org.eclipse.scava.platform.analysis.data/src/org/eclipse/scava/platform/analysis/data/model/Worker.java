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


public class Worker extends Pongo {
	
	protected AnalysisTask currentTask = null;
	
	
	public Worker() { 
		super();
		dbObject.put("currentTask", new BasicDBObject());
		WORKERID.setOwningType("org.eclipse.scava.platform.analysis.data.model.Worker");
		HEARTBEAT.setOwningType("org.eclipse.scava.platform.analysis.data.model.Worker");
	}
	
	public static StringQueryProducer WORKERID = new StringQueryProducer("workerId"); 
	public static StringQueryProducer HEARTBEAT = new StringQueryProducer("heartbeat"); 
	
	
	public String getWorkerId() {
		return parseString(dbObject.get("workerId")+"", "");
	}
	
	public Worker setWorkerId(String workerId) {
		dbObject.put("workerId", workerId);
		notifyChanged();
		return this;
	}
	public Date getHeartbeat() {
		return parseDate(dbObject.get("heartbeat")+"", null);
	}
	
	public Worker setHeartbeat(Date heartbeat) {
		dbObject.put("heartbeat", heartbeat);
		notifyChanged();
		return this;
	}
	
	
	
	public Worker setCurrentTask(AnalysisTask currentTask) {
		if (this.currentTask != currentTask) {
			if (currentTask == null) {
				dbObject.put("currentTask", new BasicDBObject());
			}
			else {
				createReference("currentTask", currentTask);
			}
			this.currentTask = currentTask;
			notifyChanged();
		}
		return this;
	}
	
	public AnalysisTask getCurrentTask() {
		if (currentTask == null) {
			currentTask = (AnalysisTask) resolveReference("currentTask");
		}
		return currentTask;
	}
	
}