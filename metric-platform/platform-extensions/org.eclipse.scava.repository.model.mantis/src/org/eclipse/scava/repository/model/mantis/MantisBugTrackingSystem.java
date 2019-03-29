/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.mantis;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class MantisBugTrackingSystem extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
	@Override
	public String getBugTrackerType() {
		return "mantis";
	}

	@Override
	public String getInstanceId() {
		return getHost() + ":" +getProject_id();
	}
	
	// protected region custom-fields-and-methods end
	
	public MantisBugTrackingSystem() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.mantis.BugTrackingSystem");
		PROJECT_ID.setOwningType("org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem");
		TOKEN.setOwningType("org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem");
		HOST.setOwningType("org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem");
	}
	
	public static StringQueryProducer PROJECT_ID = new StringQueryProducer("project_id"); 
	public static StringQueryProducer TOKEN = new StringQueryProducer("token"); 
	public static StringQueryProducer HOST = new StringQueryProducer("host"); 
	
	
	public String getProject_id() {
		return parseString(dbObject.get("project_id")+"", "");
	}
	
	public MantisBugTrackingSystem setProject_id(String project_id) {
		dbObject.put("project_id", project_id);
		notifyChanged();
		return this;
	}
	public String getToken() {
		return parseString(dbObject.get("token")+"", "");
	}
	
	public MantisBugTrackingSystem setToken(String token) {
		dbObject.put("token", token);
		notifyChanged();
		return this;
	}
	public String getHost() {
		return parseString(dbObject.get("host")+"", "");
	}
	
	public MantisBugTrackingSystem setHost(String host) {
		dbObject.put("host", host);
		notifyChanged();
		return this;
	}


	
	
	
}