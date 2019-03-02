/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.scava.repository.model.github;

import com.mongodb.*;
import java.util.*;

import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class GitHubBugTracker extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	private static StringQueryProducer USER = new StringQueryProducer("user"); 
	private static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	private static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	private static StringQueryProducer PASSWORD = new StringQueryProducer("password");
	private static StringQueryProducer TOKEN = new StringQueryProducer("token"); 
	private static StringQueryProducer OWNER = new StringQueryProducer("owner");
	
	
	public GitHubBugTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.github.BugTrackingSystem");
	}
	
	public void setProject(String user, String userLogin, String password, String repository, String repositoryOwner)
	{
		try {
			setUser(user);
			setLogin(userLogin);
			setPassword(password);
			setRepository(repository);
			setOwner(repositoryOwner);
		} catch (GitHubBugTrackerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void setProject(String user, String accessToken, String repository, String repositoryOwner)
	{
		try {
			setUser(user);
			setToken(accessToken);
			setRepository(repository);
			setOwner(repositoryOwner);
		} catch (GitHubBugTrackerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public void setProject(String user, String repository) 
	{
		try {
			setUser(user);
			setRepository(repository);
		} catch (GitHubBugTrackerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private String validateEntry(String entry, String nameEntry) throws GitHubBugTrackerException
	{
		entry=entry.trim();
		if(entry.isEmpty())
		{
			throw new GitHubBugTrackerException("Error: "+nameEntry+" is empty.");
		}
		return entry;
	}
	
	public Map<String, String> getAuthenticationData()
	{
		Map<String, String> authenticationData = new HashMap<String,String>();
		if(getLogin()==null)
		{
			if(getToken()==null)
			{
				authenticationData.put("SECURITY_TYPE","none");	
			}
			else
			{
				authenticationData.put("SECURITY_TYPE","token");
				authenticationData.put("TOKEN",getToken());
			}
		}
		else
		{
			if(getPassword()==null)
			{
				authenticationData.put("SECURITY_TYPE","none");
			}
			else
			{
				authenticationData.put("SECURITY_TYPE","password");
				authenticationData.put("LOGIN",getLogin());
				authenticationData.put("PASSWORD",getPassword());
			}
			
		}
		return authenticationData;
	}
	
	//protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "github";
    }

    @Override
    public String getInstanceId() {
        return getUser() + '/' + getRepository();
    }
    //protected region custom-fields-and-methods end
	
	private void setUser(String user) throws GitHubBugTrackerException{
		user=validateEntry(user, "user");
		USER.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		dbObject.put("user", user);
		notifyChanged();
	}
	
	public String getUser() {
		return parseString(dbObject.get("user")+"", "");
	}
	
	private void setRepository(String repository) throws GitHubBugTrackerException{
		repository=validateEntry(repository, "repository");
		REPOSITORY.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		dbObject.put("repository", repository);
		notifyChanged();
		
	}
	
	public String getRepository(){
		return parseString(dbObject.get("repository")+"", "");
	}
	
	private void setLogin(String login) throws GitHubBugTrackerException{
		login=validateEntry(login, "login");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		dbObject.put("login", login);
		notifyChanged();
	}
	
	private String getLogin() {
		if(dbObject.get("login") == null)
		{
			return null;
		}
		return parseString(dbObject.get("login")+"", "");
	}
	
	private void setPassword(String password) throws GitHubBugTrackerException{
		password=validateEntry(password, "password");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		dbObject.put("password", password);
		notifyChanged();
	}
	
	private String getPassword() {
		if(dbObject.get("password") == null)
		{
			return null;
		}
		return parseString(dbObject.get("password")+"", "");
	}
	
	private void setToken(String token) throws GitHubBugTrackerException{
		token=validateEntry(token, "token");
		TOKEN.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		dbObject.put("token", token);
		notifyChanged();
		
	}
	
	private String getToken() {
		if(dbObject.get("token") == null)
		{
			return null;
		}
		return parseString(dbObject.get("token")+"", "");
	}
	
	private void setOwner(String owner) throws GitHubBugTrackerException{
		owner=validateEntry(owner, "owner");
		OWNER.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		dbObject.put("owner", owner);
		notifyChanged();
		
	}
	
	public String getOwner() {
		return parseString(dbObject.get("owner")+"", "");
	}
	
}