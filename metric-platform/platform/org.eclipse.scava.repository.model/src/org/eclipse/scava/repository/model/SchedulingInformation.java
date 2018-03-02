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


public class SchedulingInformation extends Pongo {
	
	protected List<String> currentLoad = null;
	
	
	public SchedulingInformation() { 
		super();
		dbObject.put("currentLoad", new BasicDBList());
		WORKERIDENTIFIER.setOwningType("org.eclipse.scava.repository.model.SchedulingInformation");
		HEARTBEAT.setOwningType("org.eclipse.scava.repository.model.SchedulingInformation");
		ISMASTER.setOwningType("org.eclipse.scava.repository.model.SchedulingInformation");
		CURRENTLOAD.setOwningType("org.eclipse.scava.repository.model.SchedulingInformation");
	}
	
	public static StringQueryProducer WORKERIDENTIFIER = new StringQueryProducer("workerIdentifier"); 
	public static NumericalQueryProducer HEARTBEAT = new NumericalQueryProducer("heartbeat");
	public static StringQueryProducer ISMASTER = new StringQueryProducer("isMaster"); 
	public static ArrayQueryProducer CURRENTLOAD = new ArrayQueryProducer("currentLoad");
	
	
	public String getWorkerIdentifier() {
		return parseString(dbObject.get("workerIdentifier")+"", "");
	}
	
	public SchedulingInformation setWorkerIdentifier(String workerIdentifier) {
		dbObject.put("workerIdentifier", workerIdentifier);
		notifyChanged();
		return this;
	}
	public long getHeartbeat() {
		return parseLong(dbObject.get("heartbeat")+"", 0);
	}
	
	public SchedulingInformation setHeartbeat(long heartbeat) {
		dbObject.put("heartbeat", heartbeat);
		notifyChanged();
		return this;
	}
	public boolean getIsMaster() {
		return parseBoolean(dbObject.get("isMaster")+"", false);
	}
	
	public SchedulingInformation setIsMaster(boolean isMaster) {
		dbObject.put("isMaster", isMaster);
		notifyChanged();
		return this;
	}
	
	public List<String> getCurrentLoad() {
		if (currentLoad == null) {
			currentLoad = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("currentLoad"));
		}
		return currentLoad;
	}
	
	
	
}
