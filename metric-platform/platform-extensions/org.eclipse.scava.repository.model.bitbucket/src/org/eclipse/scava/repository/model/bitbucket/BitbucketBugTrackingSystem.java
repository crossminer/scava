package org.eclipse.scava.repository.model.bitbucket;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;

// protected region custom-imports on begin
// protected region custom-imports end

public class BitbucketBugTrackingSystem extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public BitbucketBugTrackingSystem() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.bitbucket.BugTrackingSystem");
		USER.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
		REPOSITORY.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
		OWNER.setOwningType("org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem");
	}
	
	public static StringQueryProducer USER = new StringQueryProducer("user"); 
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer OWNER = new StringQueryProducer("owner"); 
	
	
	public String getUser() {
		return parseString(dbObject.get("user")+"", "");
	}
	
	public BitbucketBugTrackingSystem setUser(String user) {
		dbObject.put("user", user);
		notifyChanged();
		return this;
	}
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public BitbucketBugTrackingSystem setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
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
	public String getOwner() {
		return parseString(dbObject.get("owner")+"", "");
	}
	
	public BitbucketBugTrackingSystem setOwner(String owner) {
		dbObject.put("owner", owner);
		notifyChanged();
		return this;
	}
	
    @Override
    public String getBugTrackerType() {
        return "bitbucket";
    }

    @Override
    public String getInstanceId() {
        return getUser() + '/' + getRepository();
    }
	
}