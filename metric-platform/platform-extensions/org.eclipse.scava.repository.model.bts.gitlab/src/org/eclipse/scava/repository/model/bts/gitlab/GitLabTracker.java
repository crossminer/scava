/*******************************************************************************
 * Copyright (c) 2019 University of Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.bts.gitlab;

import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class GitLabTracker extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public GitLabTracker() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.bts.gitlab.BugTrackingSystem");
		PERSONAL_ACCESS_TOKEN.setOwningType("org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker");
		CLIENT_ID.setOwningType("org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker");
		CLIENT_SECRET.setOwningType("org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker");
		PROJECT_ID.setOwningType("org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker");
	}
	
	public static StringQueryProducer PERSONAL_ACCESS_TOKEN = new StringQueryProducer("personal_access_token"); 
	public static StringQueryProducer CLIENT_ID = new StringQueryProducer("client_id"); 
	public static StringQueryProducer CLIENT_SECRET = new StringQueryProducer("client_secret"); 
	public static StringQueryProducer PROJECT_ID = new StringQueryProducer("project_id"); 
	
	
	public String getPersonal_access_token() {
		return parseString(dbObject.get("personal_access_token")+"", "");
	}
	
	public GitLabTracker setPersonal_access_token(String personal_access_token) {
		dbObject.put("personal_access_token", personal_access_token);
		notifyChanged();
		return this;
	}
	public String getClient_id() {
		return parseString(dbObject.get("client_id")+"", "");
	}
	
	public GitLabTracker setClient_id(String client_id) {
		dbObject.put("client_id", client_id);
		notifyChanged();
		return this;
	}
	public String getClient_secret() {
		return parseString(dbObject.get("client_secret")+"", "");
	}
	
	public GitLabTracker setClient_secret(String client_secret) {
		dbObject.put("client_secret", client_secret);
		notifyChanged();
		return this;
	}
	public String getProject_id() {
		return parseString(dbObject.get("project_id")+"", "");
	}
	
	public GitLabTracker setProject_id(String project_id) {
		dbObject.put("project_id", project_id);
		notifyChanged();
		return this;
	}

	@Override
	public String getBugTrackerType() {
		// TODO Auto-generated method stub
		return "gitlab";
	}

	@Override
	public String getInstanceId() {
		// TODO Auto-generated method stub
		return getUrl() + ':' + getProject_id();
	}
	
	
	
	
}