package org.eclipse.scava.repository.model.gitlab;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;

// protected region custom-imports on begin
// protected region custom-imports end

public class GitLabTracker extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public GitLabTracker() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.gitlab.BugTrackingSystem");
		PERSONAL_ACCESS_TOKEN.setOwningType("org.eclipse.scava.repository.model.gitlab.GitLabTracker");
	}
	
	public static StringQueryProducer PERSONAL_ACCESS_TOKEN = new StringQueryProducer("personal_access_token"); 
	
	
	public String getPersonal_access_token() {
		return parseString(dbObject.get("personal_access_token")+"", "");
	}
	
	public GitLabTracker setPersonal_access_token(String personal_access_token) {
		dbObject.put("personal_access_token", personal_access_token);
		notifyChanged();
		return this;
	}

	@Override
	public String getBugTrackerType() {
		return "gitlab";
	}

	@Override
	public String getInstanceId() {
		return getUrl();
	}
	
	
	
	
}