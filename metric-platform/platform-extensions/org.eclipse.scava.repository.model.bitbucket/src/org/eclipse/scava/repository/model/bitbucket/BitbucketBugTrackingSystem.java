package org.eclipse.scava.repository.model.bitbucket;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class BitbucketBugTrackingSystem extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public BitbucketBugTrackingSystem() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.bitbucket.BugTrackingSystem");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public BitbucketBugTrackingSystem setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public BitbucketBugTrackingSystem setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}

    @Override
    public String getBugTrackerType() {
        return "bitbucket";
    }

    @Override
    public String getInstanceId() {
        return getUrl();
    }
}