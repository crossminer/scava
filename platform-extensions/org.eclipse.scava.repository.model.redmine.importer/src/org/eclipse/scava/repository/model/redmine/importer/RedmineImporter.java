/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.redmine.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;













import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Person;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.Role;
import org.eclipse.scava.repository.model.importer.IImporter;
import org.eclipse.scava.repository.model.importer.exception.*;
import org.eclipse.scava.repository.model.redmine.*;
//import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

public class RedmineImporter implements IImporter {
	private String token;
	private String baseRepo;
	private String username;
	private String password;
	protected OssmeterLogger logger;

	
	private String getEssentialUrl(String url)
	{
		url = url.replace("http://", "");
		url = url.replace("https://", "");
		url = url.replace("www.", "");
		return url;
	}
	
	public RedmineImporter(String baseRepo, String key, String user, String password)
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.redmine");
		this.baseRepo = baseRepo;
		this.token = key;
		this.username = user;
		this.password = password;
	}
	public RedmineImporter() 
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.redmine");
	}


	private boolean exisistWiki(String url) {
		
		try {
			Jsoup.connect(url).timeout(10000).get();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	@Override
	public void importAll(Platform platform ) throws RepoInfoNotFound 
	{
		if (baseRepo == null || token == null || username == null || password == null)
			throw new RepoInfoNotFound();
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		int offset = 0;
		int total = 0;
		
		
		
		while (offset <= total)
		{ 
			InputStream is;
			try {
				is = new URL(baseRepo + "projects.json?key=" + token).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
				JSONArray projArray = ((JSONArray)obj.get("projects"));
				for (Object proj : projArray) {
					String shortName = ((JSONObject)proj).get("identifier").toString();
					platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(importProject(shortName, platform));
					platform.getProjectRepositoryManager().getProjectRepository().sync();
				}
				total = Integer.parseInt(obj.get("total_count").toString());
				offset += 25;
			} catch (MalformedURLException e) {
				logger.error("Error during import all redmine project " + e.getMessage());
			} catch (IOException e) {
				logger.error("Error during import all redmine project " + e.getMessage());
			}
		}
	}
	@Override
	public void importProjects(Platform platform, int numberOfProjects ) throws RepoInfoNotFound 
	{
		if (baseRepo == null || token == null || username == null || password == null)
			throw new RepoInfoNotFound();
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		int offset = 0;
		int total = 0;
		
		int iteration = 0;
		while (offset <= total && iteration < numberOfProjects)
		{ 
			InputStream is;
			try {
				is = new URL(baseRepo + "projects.json?key=" + token).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
				JSONArray projArray = ((JSONArray)obj.get("projects"));
				for (Object proj : projArray) {
					String shortName = ((JSONObject)proj).get("identifier").toString();
					platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(importProject(shortName, platform));
					platform.getProjectRepositoryManager().getProjectRepository().sync();
					iteration ++;
					if(iteration > numberOfProjects)
						break;
				}
				total = Integer.parseInt(obj.get("total_count").toString());
				offset += 25;
				
			} catch (MalformedURLException e) {
				logger.error("Error during import all redmine project " + e.getMessage());
			} catch (IOException e) {
				logger.error("Error during import all redmine project " + e.getMessage());
			}
		}
	}
	@Override
	public RedmineProject importProject(String projectId, Platform platform) throws RepoInfoNotFound 
	{	
		
		RedmineProject project = null;
		Boolean projectToBeUpdated = false;
		Project projectTemp = null;
		Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = pl.iterator();
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			
			if (projectTemp instanceof RedmineProject) {
				project = (RedmineProject)projectTemp;
				
				
				
					this.password = project.getPassword();
					this.username = project.getUsername();
					this.token = project.getToken();
					this.baseRepo = project.getBaseRepo();
					
				
				projectToBeUpdated = true;
				logger.info("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
				break;
					
			}
		}
		if (this.password == null || this.username == null || this.token == null || this.baseRepo == null)
			throw new RepoInfoNotFound();
		if (!projectToBeUpdated)  {
			project = new RedmineProject();
		}
		else	
		{
			project.getCommunicationChannels().clear();
			project.getVcsRepositories().clear();	
			project.getBugTrackingSystems().clear();
			project.getPersons().clear();
			project.getLicenses().clear();
			project.getVersions();
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}
		project.setHomePage(baseRepo + "projects/"+ projectId);
		project.setBaseRepo(baseRepo);
		project.setUsername(username);
		project.setPassword(password);
		project.setToken(token);
		try {
			InputStream is;
			is = new URL(baseRepo + "projects/"+ projectId +".json?key=" + token).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject jsonProj = (JSONObject)obj.get("project");
			project.setShortName(projectId);
			project.setName(jsonProj.get("name").toString());
			project.setDescription(jsonProj.get("description").toString());
			project.setCreated_on(jsonProj.get("created_on").toString());
			project.setUpdated_on(jsonProj.get("updated_on").toString());
			project.setIdentifier(jsonProj.get("id").toString());
			if(exisistWiki( baseRepo + "projects/" + projectId + "/wiki"))
			{
				RedmineWiki wiki = new RedmineWiki();
				wiki.setUrl(baseRepo + "projects/" + projectId + "/wiki");
				wiki.setNonProcessable(true);
				project.setWiki(wiki);
			}
			List<Person> persons = getPersonProject(project.getIdentifier(), platform);
			if (persons != null)
				project.getPersons().addAll(getPersonProject(project.getIdentifier(), platform));
			RedmineBugIssueTracker bit = new RedmineBugIssueTracker();
			bit.setName("Redmine_" + projectId);
			bit.getIssues().addAll(getIssue(project.getIdentifier(), platform));
			project.getBugTrackingSystems().add(bit);			
			project.getVersions().addAll(getRedmineProjectVersion(project.getIdentifier()));
			if(projectToBeUpdated)
				logger.info("Project " + projectId + " has been updated");
			else
				logger.info("Project " + projectId + " has been added");
			return project;
		}
		catch (MalformedURLException e) {
			if(project!=null)
				if(project.getExecutionInformation()!=null)
					project.getExecutionInformation().setInErrorState(true);
			logger.error("Error during import " + projectId + " redmine project ");
			return project;
		} catch (IOException e) {
			if(project!=null)
				if(project.getExecutionInformation()!=null)
					project.getExecutionInformation().setInErrorState(true);
			logger.error("Error during import " + projectId + " redmine project ");
			return project;
		} 
		
		
	}

	public RedmineProject importProject(String projectId, Platform platform, 
			String baseRepo, String password, String username, String token) throws RepoInfoNotFound 
	{	
		this.baseRepo = baseRepo;
		this.username = username;
		this.token = token;
		this.password = password;
		RedmineProject project = null;
		
		Boolean projectToBeUpdated = false;
		Project projectTemp = null;
		Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = pl.iterator();
		
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof RedmineProject) {
				project = (RedmineProject)projectTemp;
				projectToBeUpdated = true;
				logger.info("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
				break;
					
			}
		}
		if (this.baseRepo == null || this.username == null || this.password == null || this.token == null)
			throw new RepoInfoNotFound();
		if (!projectToBeUpdated)  {
			project = new RedmineProject();
		}
		else	
		{
			project.getCommunicationChannels().clear();
			project.getVcsRepositories().clear();	
			project.getBugTrackingSystems().clear();
			project.getPersons().clear();
			project.getLicenses().clear();
			project.getVersions();
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}
		
		project.setBaseRepo(this.baseRepo);
		project.setUsername(this.username);
		project.setPassword(this.password);
		project.setToken(this.token);
		project.setHomePage(baseRepo + "projects/"+ projectId);
		try {
			InputStream is;
			is = new URL(baseRepo + "projects/"+ projectId +".json?key=" + token).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject jsonProj = (JSONObject)obj.get("project");
			project.setShortName(projectId);
			project.setName(jsonProj.get("name").toString());
			project.setDescription(jsonProj.get("description").toString());
			project.setCreated_on(jsonProj.get("created_on").toString());
			project.setUpdated_on(jsonProj.get("updated_on").toString());
			project.setIdentifier(jsonProj.get("id").toString());
			if(exisistWiki( baseRepo + "projects/" + projectId + "/wiki"))
			{
				RedmineWiki wiki = new RedmineWiki();
				wiki.setUrl(baseRepo + "projects/" + projectId + "/wiki");
				wiki.setNonProcessable(true);
				project.setWiki(wiki);
			}			
			project.getPersons().addAll(getPersonProject(project.getIdentifier(), platform));
			RedmineBugIssueTracker bit = new RedmineBugIssueTracker();
			bit.setName("Redmine_" + projectId);
			bit.getIssues().addAll(getIssue(project.getIdentifier(), platform));
			project.getBugTrackingSystems().add(bit);			
			project.getVersions().addAll(getRedmineProjectVersion(project.getIdentifier()));
			if(projectToBeUpdated)
				logger.info("Project " + projectId + " has been updated");
			else
				logger.info("Project " + projectId + " has been added");
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			return project;
		}
		catch (MalformedURLException e) {
			if(project!=null)
				if(project.getExecutionInformation()!=null)
					project.getExecutionInformation().setInErrorState(true);
			logger.error("Error during import " + projectId + " redmine project ");
			return project;
		} catch (IOException e) {
			if(project!=null)
				if(project.getExecutionInformation()!=null)
					project.getExecutionInformation().setInErrorState(true);
			logger.error("Error during import " + projectId + " redmine project ");
			return project;
		}
		
	}
	
	private List<Person> getPersonProject(String projectId, Platform platform) 
	{	
		ArrayList<Person> result = new ArrayList<Person>();	
		InputStream is;
		try {
			is = new URL("http://" + username + ":" + password + "@" + getEssentialUrl(baseRepo) + "projects/" + projectId +  "/memberships.json?key=" + token).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONArray memberships = (JSONArray)obj.get("memberships");
			for (Object version : memberships) {
				JSONObject projId = (JSONObject)((JSONObject)version).get("project");
				if((projId.get("id").toString()).equals(projectId))
				{
					String id = ((JSONObject)((JSONObject)version).get("user")).get("id").toString();
					Person us = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(
								((JSONObject)((JSONObject)version).get("user")).get("name").toString()
							);
					if( us != null && us instanceof RedmineUser)
					{
						JSONArray roleName = (JSONArray)((JSONObject)version).get("roles");
						for (Object object : roleName) {
							Role r = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(((JSONObject)object).get("name").toString());
							if (r != null)
								us.getRoles().add(r);
							else
							{
								r = new Role();
								platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(r);
								r.setName(((JSONObject)object).get("name").toString());
							}
						}
						result.add(us);
					}
					else {
						//TODO
						try
						{
							String urlPerson = "http://" + username + ":" + password + "@" + getEssentialUrl(baseRepo) + "users/" + id +".json?key=" + token;
							InputStream is2 = new URL(urlPerson).openStream();
							BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
							String jsonText2 = readAll(rd2);
							JSONObject obj2 = (JSONObject)JSONValue.parse(jsonText2);
							JSONObject user = (JSONObject)obj2.get("user");
							String fullname = user.get("firstname").toString() + " " + user.get("lastname").toString();
							us = new RedmineUser();
							us.setName(fullname);
							((RedmineUser)us).setLogin(((JSONObject)user).get("login").toString());
							JSONArray roleName = (JSONArray)((JSONObject)version).get("roles");
							for (Object object : roleName) {
								Role r = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(((JSONObject)object).get("name").toString());
								if (r != null)
									us.getRoles().add(r);
								else
								{
									r = new Role();
									platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(r);
									r.setName(((JSONObject)object).get("name").toString());
									us.getRoles().add(r);
								}
							}
							result.add(us);
							platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(us);
							platform.getProjectRepositoryManager().getProjectRepository().sync();
						} catch (MalformedURLException e) {
							logger.error("Error during import person in " + projectId +" project " + e.getMessage());
							return null;
						} catch (IOException e) {
							logger.error("Error during import person in " + projectId +" project " + e.getMessage());
							return null;
						}
					}
				}
			}
			return result;
		} catch (MalformedURLException e) {
			logger.error("Error during import person in " + projectId +" project " + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error during import person in " + projectId +" project " + e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("Error during import person in " + projectId +" project " + e.getMessage());
			return null;
		}
	}

//	private ArrayList<Role> getRoles(Platform platform) throws RepoInfoNotFound
//	{
//		if (baseRepo == null || token == null || username == null || password == null)
//			throw new RepoInfoNotFound();
//		ArrayList<Role> result = new ArrayList<Role>();
//		
//		InputStream is;
//		try {
//			is = new URL(baseRepo + "roles.json?key=" + token).openStream();
//			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//			String jsonText = readAll(rd);
//			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
//			JSONArray roles = (JSONArray)obj.get("roles");
//			for (Object version : roles) {
//				Role role = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(((JSONObject)version).get("name").toString());
//				if (role == null)
//					role = new Role();
//				role.setName(((JSONObject)version).get("name").toString());
//				result.add(role);
//			}
//		} catch (MalformedURLException e) {
//			logger.error("Error during import role " + e.getMessage());
//		} catch (IOException e) {
//			logger.error("Error during import role " + e.getMessage());
//		}
//		return result;
//	}
//	
	private ArrayList<RedmineProjectVersion> getRedmineProjectVersion(String projectID)
	{
		
		ArrayList<RedmineProjectVersion> result = new ArrayList<RedmineProjectVersion>();
		InputStream is;
		try {
			is = new URL(baseRepo + "projects/"+ projectID +"/versions.json?key=" + token).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONArray versions = (JSONArray)obj.get("versions");
			for (Object version : versions) {
				RedmineProjectVersion rpv = new RedmineProjectVersion();
				rpv.setName(((JSONObject)version).get("name").toString());
				rpv.setDescription(((JSONObject)version).get("description").toString());
				rpv.setUpdated_on(((JSONObject)version).get("updated_on").toString());
				rpv.setCreated_on(((JSONObject)version).get("created_on").toString());
				String vs = ((JSONObject)version).get("status").toString();
				rpv.setStatus(vs);

				result.add(rpv);
			}
		} catch (MalformedURLException e) {
			logger.error("Error during import project version for " + projectID +" " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error during import project version for " + projectID +" " + e.getMessage());
		}
		
		return result;
	}

	private static String readAll(Reader rd) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	private List<RedmineIssue> getIssue(String id, Platform platform)
	{
		ArrayList<RedmineIssue> result = new ArrayList<RedmineIssue>();
		int offset = 0;
		int total = 0;
		while (offset <= total)
		{ 
			String issuesUrlString = baseRepo + "issues.json?project_id=" + id + "&status_id=*&key=" + token + "&offset=" + offset;
			try {
				InputStream is2 = new URL(issuesUrlString).openStream();
				BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
				String jsonText2 = readAll(rd2);
				//String jsonText2 = IOUtils.toString(new URL(issuesUrlString).openStream());
	            JSONObject obj2 = (JSONObject) JSONValue.parseWithException(jsonText2);
	            JSONArray issuesList = (JSONArray)obj2.get("issues");
	            
	            for (Object issue : issuesList) {
					//_-_-_-_-_-_-_-_-_-
	            	
					RedmineIssue ri = new RedmineIssue();
					ri.setDescription(((JSONObject)issue).get("description").toString());
					String status = ((JSONObject)((JSONObject)issue).get("status")).get("name").toString();
					ri.setStatus(status);
	
					if(((JSONObject)issue).get("start_date")!=null)
						ri.setStart_date(((JSONObject)issue).get("start_date").toString());
					if(((JSONObject)issue).get("due_date")!=null)
						ri.setDue_date(((JSONObject)issue).get("due_date").toString());
					if(((JSONObject)issue).get("update_date")!=null)
						ri.setUpdate_date(((JSONObject)issue).get("update_date").toString());
					if(((JSONObject)issue).get("description")!=null)
						ri.setDescription(((JSONObject)issue).get("description").toString());
					String priority = ((JSONObject)issue).get("priority").toString();
					ri.setPriority(priority);
				
					JSONObject cat = (JSONObject)((JSONObject)issue).get("category");
					
					if (cat != null)
					{
						RedmineCategory g = new RedmineCategory();
						g.setName(cat.get("name").toString());
						ri.setCategory(g);
					}
					JSONObject author = (JSONObject)((JSONObject)issue).get("author");
					Person p = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(author.get("name").toString());
					if (p!=null && p instanceof RedmineUser)
						ri.setAuthor((RedmineUser)p);
					JSONObject assigned = (JSONObject)((JSONObject)issue).get("assigned_to");
					if (assigned != null)
					{
						Person assignedPerson = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(assigned.get("name").toString());
						if (assignedPerson !=null && assignedPerson instanceof RedmineUser)
							ri.setAssignedTo((RedmineUser)assignedPerson);
					}
					result.add(ri);
	            	
	            	
	            	//_-_-_-_-_-_-_-_-_-
	            	
				}
				total = Integer.parseInt(obj2.get("total_count").toString());
				offset += 25;
			} catch (MalformedURLException e) {
				logger.error("Error during import issue for redmine project: " + id);
				break;
			} catch (IOException e) {
				logger.error("Error during import issue for redmine project: " + id);
				break;
			} catch (ParseException e) {
				logger.error("Error during import issue for redmine project: " + id);
				break;
			}
		}
		return result;
	}

	@Override
	public boolean isProjectInDB(String projectId, Platform platform)
	{
		try 
		{
			Iterable<Project> projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
			Iterator<Project> iprojects = projects.iterator();
			RedmineProject project = null;
			Project projectTemp = null;
			while (iprojects.hasNext()) {
				projectTemp = iprojects.next();
				if (projectTemp instanceof RedmineProject) {
					project = (RedmineProject)projectTemp;
					if (project.getShortName().equals(projectId)) {
						return true;
					}	
				}
			}
			return false;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean isProjectInDBByUrl(String url, Platform platform) throws WrongUrlException
	{
		return isProjectInDB(getProjectIdFromUrl(url), platform);
	}
	@Override
	public RedmineProject importProjectByUrl(String url, Platform platform) throws RepoInfoNotFound, WrongUrlException
	{
		return importProject(getProjectIdFromUrl(url), platform);
	}
	
	public RedmineProject importProjectByUrl(String url, Platform platform, String baseRepo, String token, String username, String password) throws RepoInfoNotFound, WrongUrlException
	{
		return importProject(getProjectIdFromUrl(url), platform, baseRepo, password, username, token);
	}
	private String getProjectIdFromUrl(String url) throws WrongUrlException
	{
		baseRepo = url.startsWith("http://")? url:"http://" + url;
		url = url.endsWith("/")?url.substring(0,url.length()-1):url;
		if (url.contains("/"))
			return url.substring(url.lastIndexOf("/")+1,url.length());
		else throw new WrongUrlException();
	}
	
}
