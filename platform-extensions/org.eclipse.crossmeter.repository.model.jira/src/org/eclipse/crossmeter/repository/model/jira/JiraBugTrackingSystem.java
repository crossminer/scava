/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.repository.model.jira;

import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class JiraBugTrackingSystem extends org.eclipse.crossmeter.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "jira";
    }

    @Override
    public String getInstanceId() {
        return getUrl() + ':' + getProject();
    }
    
	// protected region custom-fields-and-methods end
	
	public JiraBugTrackingSystem() { 
		super();
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.jira.BugTrackingSystem");
		PROJECT.setOwningType("org.eclipse.crossmeter.repository.model.jira.JiraBugTrackingSystem");
		LOGIN.setOwningType("org.eclipse.crossmeter.repository.model.jira.JiraBugTrackingSystem");
		PASSWORD.setOwningType("org.eclipse.crossmeter.repository.model.jira.JiraBugTrackingSystem");
	}
	
	public static StringQueryProducer PROJECT = new StringQueryProducer("project"); 
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getProject() {
		return parseString(dbObject.get("project")+"", "");
	}
	
	public JiraBugTrackingSystem setProject(String project) {
		dbObject.put("project", project);
		notifyChanged();
		return this;
	}
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public JiraBugTrackingSystem setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public JiraBugTrackingSystem setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	
	
}