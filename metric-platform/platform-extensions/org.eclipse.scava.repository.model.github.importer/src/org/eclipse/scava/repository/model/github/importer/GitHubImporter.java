/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.github.importer;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.ImportData;
import org.eclipse.scava.repository.model.Person;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.Role;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;
import org.eclipse.scava.repository.model.github.GitHubContent;
import org.eclipse.scava.repository.model.github.GitHubDownload;
import org.eclipse.scava.repository.model.github.GitHubIssue;
import org.eclipse.scava.repository.model.github.GitHubRepository;
import org.eclipse.scava.repository.model.github.GitHubUser;
import org.eclipse.scava.repository.model.importer.IImporter;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;



public class GitHubImporter implements IImporter{

	private String authToken;
	private String authString;
	protected OssmeterLogger logger;
	public GitHubImporter() {
		this.authToken = getToken();
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.gitHub");
		if (authToken != null)
			authString = "?access_token=" + authToken;
		else 
			authString = "";
/*		GitHubClient gitHubClient = new GitHubClient();
		gitHubClient.setOAuth2Token("ffab283e2be3265c7b0af244e474b28430351973");
		repositoryService = new RepositoryService();
		userService = new UserService();
		collaboratorService = new CollaboratorService();
		watcherService = new WatcherService();
*/	
	}
	private static String getToken(){
		try {
    		Properties forgeProperties = new Properties();
			forgeProperties.load(new FileReader("github_auth.properties"));
			return forgeProperties.getProperty("token");
			
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
	}
	private int getRemainingResource()
	{
		try {
			String url = "https://api.github.com/rate_limit" + authString;
			InputStream is;
			is = new URL(url).openStream();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));		
			String jsonText = readAll(rd);
	
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject rate = (JSONObject)((JSONObject)obj.get("rate"));
			//logger.debug("attempt to");
			Integer remaining = new Integer(rate.get("remaining").toString());
			return remaining;
		} catch (MalformedURLException e) {
			logger.error("Get remaining api exception");
			return 0;
		} catch (IOException e) {
			logger.error("Get remaining api exception");
			return 0;
		}
	}
	
	private void waitApiRate(){
		
		String url = "https://api.github.com/rate_limit" + authString;
		boolean sleep = true;
		logger.info("API rate limit exceeded. Waiting to restart the importing...");
		while (sleep) {
			try {
					InputStream is = new URL(url).openStream();
					BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));		
					String jsonText = readAll(rd);
	
					JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
					JSONObject rate = (JSONObject)((JSONObject)obj.get("rate"));
					Integer remaining = new Integer(rate.get("remaining").toString());
					if (remaining > 0) {
						sleep = false;
					}
				
			} catch (IOException e) {
				logger.error("Having difficulties to connect, retrying...");
				continue;
			}		
		}	
	}

	
	private boolean isNotNull(JSONObject currentRepo, String attribute ) 
	{
		if (currentRepo.get(attribute) == null)
			return false;
		else 
			return true;		
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

	@Override
	public void importAll(Platform platform){
	
		int lastImportedId = 0;
		InputStream is = null;
		BufferedReader rd = null;
		String jsonText = null;
		boolean stop = false;
		
		int firstId = 0;
		int lastId = 0;


		if (platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().size() != 0) {
			lastImportedId = new Integer(platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first().getLastImportedProject());
		} else {
			ImportData id = new ImportData();
			id.setLastImportedProject(String.valueOf(lastImportedId));
			platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().add(id);	
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}

		while (!stop) {
			try {
				if (getRemainingResource()==0)
					waitApiRate();
				is = new URL("https://api.github.com/repositories?since=" + lastImportedId + "&access_token=" + this.authToken).openStream();		
				rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				jsonText = readAll(rd);			
			} catch (IOException e) {
				logger.error("API rate limit exceeded. Waiting to restart the importing...");
//				platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportingData().first().setLastImportedProject(String.valueOf(lastImportedId));
//				platform.getProjectRepositoryManager().getProjectRepository().sync();
				waitApiRate();
				break;				
			}
			
			JSONArray obj=(JSONArray)JSONValue.parse(jsonText);
			if (! obj.isEmpty()) {
				firstId = new Integer((((JSONObject)(obj.get(0))).get("id")).toString());
				lastId = new Integer((((JSONObject)(obj.get(obj.size()-1))).get("id")).toString());
				logger.info("Scanning page: " + "https://api.github.com/repositories?since=" + lastImportedId);
				logger.info("--> Importing repositories from id:" + firstId + " to id:" + lastId);
				platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first().setLastImportedProject(String.valueOf(firstId-1));
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
				
				Iterator iter = obj.iterator();
				while (iter.hasNext()) {
					JSONObject entry = (JSONObject) iter.next();		
					try {
						GitHubRepository repository = importProject((String) entry.get("full_name"), platform);
					} catch (WrongUrlException e) {
						logger.error("Wrong project id");
					}
				}
				lastImportedId = lastId;
			} else {
				stop = true;
				logger.info("Importing completed.");
				break;
			}
		}
	}
	@Override
	public void importProjects(Platform platform, int numberOfProjects) throws WrongUrlException{
		int lastImportedId = 1;
		BufferedReader rd = null;
		String jsonText = null;
		boolean stop = false;
		int firstId = 0;
		int lastId = 0;
		try
		{
			if (platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().size() != 0) {
				lastImportedId = new Integer(platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first().getLastImportedProject());
			} else {
				ImportData id = new ImportData();
				id.setLastImportedProject(String.valueOf(lastImportedId));
				platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().add(id);	
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}			
		}
		catch (Exception e)
		{
			ImportData id = new ImportData();
			id.setLastImportedProject(String.valueOf(lastImportedId));
			platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().add(id);	
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}
		int iteration  = 0;
		while (!stop) {
			try {
				if (getRemainingResource()==0)
					waitApiRate();
				URL obj2 = new URL("https://api.github.com/repositories?since=" + lastImportedId + authString);
				URLConnection conn = obj2.openConnection();
				String sorry = conn.getHeaderField("STATUS");
				if (sorry.startsWith("404"))
					throw new WrongUrlException();
				InputStream is = conn.getInputStream();
					
				rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				jsonText = readAll(rd);			
				JSONArray obj=(JSONArray)JSONValue.parse(jsonText);
				if (! obj.isEmpty()) {
					firstId = new Integer((((JSONObject)(obj.get(0))).get("id")).toString());
					lastId = new Integer((((JSONObject)(obj.get(obj.size()-1))).get("id")).toString());
					logger.info("Scanning page: " + "https://api.github.com/repositories?since=" + lastImportedId);
					logger.info("--> Importing repositories from id:" + firstId + " to id:" + lastId);
					platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first().setLastImportedProject(String.valueOf(firstId-1));
					platform.getProjectRepositoryManager().getProjectRepository().sync();	
					
					Iterator iter = obj.iterator();
					while (iter.hasNext()) {
						JSONObject entry = (JSONObject) iter.next();		
						try {
							GitHubRepository repository = importProject((String) entry.get("full_name"), platform);
						} catch (WrongUrlException e) {
							logger.error("Wrong project id");
						}
						iteration++;
						if (iteration > numberOfProjects)
							break;
					}
					lastImportedId = lastId;
					if (iteration > numberOfProjects)
						break;
				} else {
					stop = true;
					logger.info("Importing completed.");
					break;
				}
			} catch (IOException e) {
				logger.error("API rate limit exceeded. Waiting to restart the importing...");
//				platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportingData().first().setLastImportedProject(String.valueOf(lastImportedId));
//				platform.getProjectRepositoryManager().getProjectRepository().sync();
				waitApiRate();				
			}	
		}
	}
	
	
	@Override
	public GitHubRepository importProject(String projectId, Platform platform) throws WrongUrlException {
		try 
		{
			
			int lastImportedId = 0;
			if (platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().size()!=0)
				lastImportedId = new Integer(platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first().getLastImportedProject());
			GitHubRepository repository = null;
			JSONObject currentRepo = null;
			Boolean projectToBeUpdated = false;
			logger.info("---> processing repository " + projectId);
			URL obj = new URL("https://api.github.com/repos/" + projectId + authString);
			URLConnection conn = obj.openConnection();
			String sorry = conn.getHeaderField("STATUS");
			if (sorry.startsWith("404"))
				throw new WrongUrlException();
			InputStream is = conn.getInputStream();

				
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
		     		
			currentRepo=(JSONObject)JSONValue.parse(jsonText);

			Iterable<Project> projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByName(currentRepo.get("name").toString());		

			Iterator<Project> ip = projects.iterator();
			Project p = null;
			while (ip.hasNext()) {
				p = ip.next();				
				if (p instanceof GitHubRepository) {
					repository = (GitHubRepository)p;
					if (repository.getFull_name().equals(projectId)) {
						logger.info("-----> repository " + repository.getFull_name() + " already in the repository. Its metadata will be updated.");
						projectToBeUpdated = true;
						break;
					}
				}
			}
			if(!projectToBeUpdated)
				repository = new GitHubRepository();
			else repository.getVcsRepositories().clear();
			String appDescription = (String)currentRepo.get("description");
			if (appDescription != null)					
				repository.setDescription(appDescription);

			repository.setFull_name(projectId);
			repository.setName(currentRepo.get("name").toString());

			if ((isNotNull(currentRepo,"private")))					
				repository.set_private(new Boolean(currentRepo.get("private").toString()));

			repository.setFull_name(projectId);
//			repository.setShortName(projectId);
			repository.setName(currentRepo.get("name").toString());

			if ((isNotNull(currentRepo,"private")))					
				repository.set_private(new Boolean(currentRepo.get("private").toString()));

			if ((isNotNull(currentRepo,"fork")))					
				repository.setFork(new Boolean(currentRepo.get("fork").toString()));
			
			if ((isNotNull(currentRepo,"git_url")))					
				repository.setGit_url(currentRepo.get("git_url").toString());
			
			if ((isNotNull(currentRepo,"html_url")))					
				repository.setHtml_url(currentRepo.get("html_url").toString());
			
			if ((isNotNull(currentRepo,"clone_url")))
			{
				repository.setClone_url(currentRepo.get("clone_url").toString());
				GitRepository git = new GitRepository();
				git.setUrl(currentRepo.get("clone_url").toString());
				repository.getVcsRepositories().add(git);
			}
			if ((isNotNull(currentRepo,"homepage")))					
				repository.setHomePage(currentRepo.get("homepage").toString());

			if ((isNotNull(currentRepo,"mirror_url")))					
				repository.setMirror_url(currentRepo.get("mirror_url").toString());

			if ((isNotNull(currentRepo,"master_branch")))					
				repository.setMaster_branch(currentRepo.get("master_branch").toString());

			if ((isNotNull(currentRepo,"ssh_url")))					
				repository.setSsh_url(currentRepo.get("ssh_url").toString());
			

			if ((isNotNull(currentRepo,"svn_url")))	
			{
				repository.setSvn_url(currentRepo.get("svn_url").toString());
				SvnRepository svn = new SvnRepository();
				svn.setUrl(currentRepo.get("svn_url").toString());
//				repository.getVcsRepositories().add(svn);
			}
			repository.setSize(new Integer(currentRepo.get("size").toString()));
			try{
				List<Person> persons = getPersonsInAProject(platform, projectId);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				for (Person person : persons)
					repository.getPersons().add((Person)person);
			}
			catch(Exception e)
			{
				logger.debug("Unable to load collaborators for this project");
			}
			repository.getBugTrackingSystems().clear();
			if (projectId.split("/").length == 2)
			{
				GitHubBugTracker bt;
				bt = new GitHubBugTracker();
				bt.setUrl("https://api.github.com/repos/" + projectId + "/issues");
				String user = projectId.split("/")[0];
				String repo = projectId.split("/")[1];
				bt.setProject(user, repo);
				repository.getBugTrackingSystems().add(bt);
			}
			

			/*
			try{
				List<GitHubIssue> issues = getIssuesInAProject(platform, projectId);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				
				for (GitHubIssue person : issues)
					bt.getIssues().add((GitHubIssue)person);
			}
			catch(Exception e)
			{
				logger.debug("Unable to load issues for this project");
			}
			repository.getBugTrackingSystems().add(bt);
			
			try{
				List<GitHubContent> issues = getContentInAProject(platform, projectId);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				for (GitHubContent person : issues)
					repository.getContents().add((GitHubContent)person);
			}
			catch(Exception e)
			{
				logger.debug("Unable to load contents for this project");
			}
			
			try{
				List<GitHubDownload> downloads = getDownloadsInAProject(platform, projectId);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
				for (GitHubDownload person : downloads)
					repository.getDownloads().add((GitHubDownload)person);
			}
			catch(Exception e)
			{
				logger.debug("Unable to load downloads for this project");
			}
			
			*/
			lastImportedId = new Integer(currentRepo.get("id").toString());
			ImportData importData = null;
			if (platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().size() == 0)
			{
				importData = new ImportData();
				platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().add(importData);
			}
			else importData = platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first();
			importData.setLastImportedProject(String.valueOf(lastImportedId));
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			platform.getProjectRepositoryManager().getProjectRepository().getGitHubImportData().first().setLastImportedProject(String.valueOf(lastImportedId));
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			if (!projectToBeUpdated) {
				repository.setShortName(platform.getProjectRepositoryManager().generateUniqueId(repository));
				platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(repository);
			}
			
			
			
			platform.getProjectRepositoryManager().getProjectRepository().sync();	
			logger.info("Project has been importerd");
			return repository;
		}  catch (IOException e1) {
			if (getRemainingResource()==0)
			{
				logger.error("API rate limit exceeded. Waiting to restart the importing..." + e1.getMessage());
				waitApiRate();
				return importProject(projectId, platform);
			}
			else
			{
				logger.error("Repository access blocked for project " + projectId + " " + e1.getMessage());
				return null;
			}
		} 
	}
	private List<GitHubContent> getContentInAProject(Platform platform, String projectId) {
		List<GitHubContent> result = new ArrayList<GitHubContent>();
		JSONArray currentRepo = null;
		InputStream is;
		try {
			URL obj = new URL("https://api.github.com/repos/" + projectId + "/contents" + authString);
			URLConnection conn = obj.openConnection();
			int numberOfPage = getNumberOfPage(conn);
			is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			for (int i = 1; i <= numberOfPage; i++)
			{
				try
				{
					if(i != 1)
					{	URL obj2 = new URL("https://api.github.com/repos/" + projectId + "/contents" + authString + "&page=" + i);
						URLConnection conn2 = obj2.openConnection();
						InputStream is2 = conn2.getInputStream();
						BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
						jsonText = readAll(rd2);
					}
					currentRepo=(JSONArray)JSONValue.parse(jsonText);
					for (Object object : currentRepo) {
						JSONObject ownerObject = (JSONObject)object;
						GitHubContent content = new GitHubContent();
						
						content.setType((String)ownerObject.get("type"));
						content.setName((String)ownerObject.get("name"));
						content.setSha((String)ownerObject.get("sha"));
						int number = 0;
						try {
							number = Integer.parseInt((String)ownerObject.get("size"));
						}
						catch (Exception e) {}
						content.setPath((String)ownerObject.get("path"));
						content.setSize(number);
						result.add(content);
					}
				}catch (MalformedURLException e) {
					return result;
				} catch (IOException e) {
					return result;
				} catch (Exception e) {
					return result;
				}
			}
			return result;
		} catch (MalformedURLException e) {
			return result;
		} catch (IOException e) {
			return result;
		} catch (Exception e)
		{
			return result;
		}
	}
	private List<GitHubDownload> getDownloadsInAProject(Platform platform, String projectId) {
		List<GitHubDownload> result = new ArrayList<GitHubDownload>();
		JSONArray currentRepo = null;
		InputStream is;
		try {
			URL obj = new URL("https://api.github.com/repos/" + projectId + "/releases" + authString);
			URLConnection conn = obj.openConnection();
			int numberOfPage = getNumberOfPage(conn);
			is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			for (int i = 1; i <= numberOfPage; i++)
			{
				try
				{
					if(i != 1)
					{	URL obj2 = new URL("https://api.github.com/repos/" + projectId + "/releases" + authString + "&page=" + i);
						URLConnection conn2 = obj2.openConnection();
						InputStream is2 = conn2.getInputStream();
						BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
						jsonText = readAll(rd2);
					}
					currentRepo=(JSONArray)JSONValue.parse(jsonText);
					for (Object object : currentRepo) {
						JSONObject ownerObject = (JSONObject)object;
						GitHubDownload download = new GitHubDownload();
						
						int number = 0;
						try {
							number = Integer.parseInt((String)ownerObject.get("size"));
						}
						catch (Exception e) {}
						download.set_id(number);
						download.setDescription((String)ownerObject.get("body"));
						download.setName((String)ownerObject.get("name"));
						download.setUrl((String)ownerObject.get("zipball_url"));
						download.setHtml_url((String)ownerObject.get("html_url"));
						result.add(download);
					}
				}catch (MalformedURLException e) {
					return result;
				} catch (IOException e) {
					return result;
				} catch (Exception e) {
					return result;
				}
			}
			return result;
		} catch (MalformedURLException e) {
			return result;
		} catch (IOException e) {
			return result;
		} catch (Exception e)
		{
			return result;
		}
	}
	
	private List<GitHubIssue> getIssuesInAProject(Platform platform, String projectId) {
		List<GitHubIssue> result = new ArrayList<GitHubIssue>();
		JSONArray currentRepo = null;
		InputStream is;
		try {
			URL obj = new URL("https://api.github.com/repos/" + projectId + "/issues" + authString);
			URLConnection conn = obj.openConnection();
			int numberOfPage = getNumberOfPage(conn);
			is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			for (int i = 1; i <= numberOfPage; i++)
			{
				try
				{
					if(i != 1)
					{	URL obj2 = new URL("https://api.github.com/repos/" + projectId + "/issues" + authString + "&page=" + i);
						URLConnection conn2 = obj2.openConnection();
						InputStream is2 = conn2.getInputStream();
						BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
						jsonText = readAll(rd2);
					}
					currentRepo=(JSONArray)JSONValue.parse(jsonText);
					for (Object object : currentRepo) {
						JSONObject ownerObject = (JSONObject)object;
						String login = (String)ownerObject.get("login");
						String role = (String)ownerObject.get("type");
						GitHubIssue issues = new GitHubIssue();
						
						
						issues.setState((String)ownerObject.get("state"));
						issues.setBody((String)ownerObject.get("body"));
						issues.setClosed_at((String)ownerObject.get("closed_at"));
						issues.setCreated_at((String)ownerObject.get("created_at"));
						int number = 0;
						try {
							number = Integer.parseInt((String)ownerObject.get("number"));
						}
						catch (Exception e) {}
						issues.setNumber(number);
						issues.setState((String)ownerObject.get("state"));
						issues.setTitle((String)ownerObject.get("title"));
						issues.setTitle((String)ownerObject.get("html_url"));
						result.add(issues);
					}
				}catch (MalformedURLException e) {
					return result;
				} catch (IOException e) {
					return result;
				} catch (Exception e) {
					return result;
				}
			}
			return result;
		} catch (MalformedURLException e) {
			return result;
		} catch (IOException e) {
			return result;
		} catch (Exception e)
		{
			return result;
		}
	}
	private int getNumberOfPage(URLConnection urlConn)
	{
		int result = 1;
		Map<String, List<String>> map = urlConn.getHeaderFields();
		List<String> numberOfElements = map.get("Link");
		if (numberOfElements != null)
		for (String string : numberOfElements) {
			int indexOfLast = string.lastIndexOf("&page=") + 6;
			string = string.substring(indexOfLast);
			string = string.substring(0,string.indexOf(">"));
			try
			{
				result = Integer.parseInt(string);
			}
			catch(NumberFormatException e)
			{
				result = 1;
			}
		}
		return result;
	}
	private List<Person> getPersonsInAProject(Platform platform, String projectId) {
		List<Person> result = new ArrayList<Person>();
		JSONArray currentRepo = null;
		InputStream is;
		try {
			URL obj = new URL("https://api.github.com/repos/" + projectId + "/contributors" + authString);
			URLConnection conn = obj.openConnection();
			int numberOfPage = getNumberOfPage(conn);
			is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			
			
			for (int i = 1; i <= numberOfPage; i++)
			{
				try
				{
					if(i != 1)
					{	URL obj2 = new URL("https://api.github.com/repos/" + projectId + "/contributors" + authString + "&page=" + i);
						URLConnection conn2 = obj2.openConnection();
						InputStream is2 = conn2.getInputStream();
						BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
						jsonText = readAll(rd2);
					}
					currentRepo=(JSONArray)JSONValue.parse(jsonText);
					for (Object object : currentRepo) {
						JSONObject ownerObject = (JSONObject)object;
						String login = (String)ownerObject.get("login");
						String role = (String)ownerObject.get("type");
						
						Role gitHubOwnerRole = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(role);
						if (gitHubOwnerRole == null)
						{ 
							gitHubOwnerRole = new Role();
							gitHubOwnerRole.setName(role);
							platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gitHubOwnerRole);
						}
						GitHubUser user = null;
						for (Person p : platform.getProjectRepositoryManager().getProjectRepository().getPersons().findByName(login))
							if(p instanceof GitHubUser)
								user = (GitHubUser)p;
						if (user == null)
						{
							user = new GitHubUser();
							platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(user);
						}
						user.setHtml_url((String)ownerObject.get("login"));
						user.setHtml_url((String)ownerObject.get("html_url"));
						user.setHomePage((String)ownerObject.get("html_url"));
						user.setUrl((String)ownerObject.get("url"));
						user.setLogin((String)ownerObject.get("login"));
						user.setName((String)ownerObject.get("login"));
						user.setFollowers_url((String)ownerObject.get("followers_url"));
						boolean inRole = false;
						for (Role r : user.getRoles())
						{
							if (r.getName().equals(role))
							{	
								inRole = true;
								break;
							}
						}
						if (!inRole)
							user.getRoles().add(gitHubOwnerRole);
						result.add(user);
					}
				}catch (MalformedURLException e) {
					return result;
				} catch (IOException e) {
					return result;
				} catch (Exception e) {
					return result;
				}
			}
			return result;
		} catch (MalformedURLException e) {
			return result;
		} catch (IOException e) {
			return result;
		} catch (Exception e)
		{
			return result;
		}
		
	}
	private String getProjectIdFromUrl(String url)
	{
		url = url.replace("http://", "");
		url = url.replace("https://", "");
		url = url.replace("www.", "");
		
		if (url.startsWith("github.com")) {
			url= url.replace("github.com/", "");
			if(url.contains("?"))
				url = url.substring(0, url.indexOf("?"));
			return url;
		}
		else return null;
	}
	@Override
	public GitHubRepository importProjectByUrl(String url, Platform platform) throws WrongUrlException {
		String s = getProjectIdFromUrl(url);
		if (s == null)
			throw new WrongUrlException();
		return importProject(s, platform);
	}
	@Override
	public boolean isProjectInDB(String projectId, Platform platform)
	{	
		Iterable<Project> projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);		

		Iterator<Project> ip = projects.iterator();
		Project p = null;
		while (ip.hasNext()) {
			p = ip.next();				
			if (p instanceof GitHubRepository) {
				return true;		
			}
		}
		return false;
	}
	@Override
	public boolean isProjectInDBByUrl(String url, Platform platform)
	{
		return isProjectInDB(getProjectIdFromUrl(url), platform);
	}
}
