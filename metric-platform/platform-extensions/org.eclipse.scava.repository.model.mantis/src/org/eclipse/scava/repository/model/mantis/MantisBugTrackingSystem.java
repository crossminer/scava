package org.eclipse.scava.repository.model.mantis;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class MantisBugTrackingSystem extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public MantisBugTrackingSystem() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.mantis.BugTrackingSystem");
		TOKEN.setOwningType("org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem");
		PROJECT_IDENTIFIER.setOwningType("org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem");
	}
	
	public static StringQueryProducer TOKEN = new StringQueryProducer("token"); 
	public static StringQueryProducer PROJECT_IDENTIFIER = new StringQueryProducer("project_identifier"); 
	
	
	public String getToken() {
		return parseString(dbObject.get("token")+"", "");
	}
	
	public MantisBugTrackingSystem setToken(String token) {
		dbObject.put("token", token);
		notifyChanged();
		return this;
	}
	public String getProject_identifier() {
		return parseString(dbObject.get("project_identifier")+"", "");
	}
	
	public MantisBugTrackingSystem setProject_identifier(String project_identifier) {
		dbObject.put("project_identifier", project_identifier);
		notifyChanged();
		return this;
	}

	@Override
	public String getBugTrackerType() {
		return "mantis";
	}

	@Override
	public String getInstanceId() {
		return getUrl() + " : " +getProject_identifier();
	}
	
	
	
	
}