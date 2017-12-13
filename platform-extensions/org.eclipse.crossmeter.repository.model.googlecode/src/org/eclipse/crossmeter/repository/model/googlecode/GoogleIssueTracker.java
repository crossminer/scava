/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

//protected region custom-imports on begin
//protected region custom-imports end

public class GoogleIssueTracker extends org.eclipse.crossmeter.repository.model.BugTrackingSystem {
	
	protected List<GoogleIssue> issues = null;
	
	// protected region custom-fields-and-methods on begin
		 @Override
		    public String getBugTrackerType() {
		        return "google";
		    }

		    @Override
		    public String getInstanceId() {
		        return getUrl();
		    }
		    
		// protected region custom-fields-and-methods end
	
	
	public GoogleIssueTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
		super.setSuperTypes("org.eclipse.crossmeter.repository.model.googlecode.BugTrackingSystem");
		URL.setOwningType("org.eclipse.crossmeter.repository.model.googlecode.GoogleIssueTracker");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GoogleIssueTracker setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	
	
	public List<GoogleIssue> getIssues() {
		if (issues == null) {
			issues = new PongoList<GoogleIssue>(this, "issues", true);
		}
		return issues;
	}
	
	
}