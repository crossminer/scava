/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.googlecode.importer;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.License;
import org.eclipse.scava.repository.model.Person;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.Role;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.googlecode.*;
import org.eclipse.scava.repository.model.importer.IImporter;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleCodeImporter implements IImporter{
	
	
	protected OssmeterLogger logger;
	private Map<String, Role> rolePending = new HashMap<String, Role>();
	private Map<String, License> licensePending = new HashMap<String, License>();
	private Map<String, Person> userPending = new HashMap<String, Person>();
	public GoogleCodeImporter()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.GoogleCode");
	}
	@Override
	public GoogleCodeProject importProject(String projectId, Platform platform) throws WrongUrlException 
	{
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		String projectUrl = "https://code.google.com/p/" + projectId + "/";
		String URL_PROJECT = projectUrl;
		GoogleCodeProject project = new GoogleCodeProject();;
		Boolean projectToBeUpdated = false;
		
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e = doc.getElementById("pname");

			Project projectTemp = null;
			
			
			Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(e.text());
			Iterator<Project> iprojects = pl.iterator();
			
			
			while (iprojects.hasNext()) {
				projectTemp = iprojects.next();
				if (projectTemp instanceof GoogleCodeProject) {
					project = (GoogleCodeProject)projectTemp;
					projectToBeUpdated = true;
					logger.info("-----> project " + e.text() + " already in the repository. Its metadata will be updated.");
					break;
						
				}
			}
			
			project.getCommunicationChannels().clear();
			project.getVcsRepositories().clear();	
			project.getPersons().clear();
			project.getDownloads().clear();
			project.getLicenses().clear();
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			
			project.setName(e.text());
			project.setHomePage(URL_PROJECT);
			project.setShortName(projectId);
			e = doc.getElementById("wikicontent");
			project.setDescription(Jsoup.parse(e.toString()).text());
			e = doc.getElementById("mt");
			//SET WIKI
			Elements wikis = e.getElementsContainingText("Wiki");
			Element wiki = null;
			for (Element element : wikis) 
			{
				if(element.text().toString().contains("Wiki"))
					wiki = element;
			}
			if (wiki != null)
			{
				GoogleWiki gw = new GoogleWiki();
				gw.setUrl("https://code.google.com" + wiki.outerHtml().substring(wiki.outerHtml().indexOf("href")+6, wiki.outerHtml().indexOf("class")-2));
				gw.setNonProcessable(true);
				project.setWiki(gw);
				
			}
			//SET PERSON
			List<GoogleUser> gul = getPersonProject(platform, projectUrl + "people/list");
			for (GoogleUser googleUser : gul) {
				platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(googleUser);
				project.getPersons().add(googleUser);
			}
			project.getPersons().addAll(gul);
			//SET GoogleIssueTracker
			Elements issues = e.getElementsContainingText("Issues");
			Element issue = null;
			for (Element element : issues) 
			{
				if(element.text().toString().contains("Issues"))
					issue = element;
			}
			
			if (issue != null)
			{
				GoogleIssueTracker git = new GoogleIssueTracker();
				git.setUrl("https://code.google.com" + issue.outerHtml().substring(issue.outerHtml().indexOf("href")+6, issue.outerHtml().indexOf("class")-2));
				
				//This line work fine but there is a google access limit
				
				//System.out.println(git.getUrl());
				//List<GoogleIssue> gi = getGoogleIssueList(platform, git.getUrl());
				//git.getIssues().addAll(gi);
				project.setIssueTracker(git);
			}
			
			//SET DOWNLOAD
			Elements dwnldLinks = e.getElementsContainingText("Downloads");
			Element dwnldLink = null;
			String downloadPage = "";
			for (Element element : dwnldLinks) 
			{
				if(element.text().toString().contains("Downloads"))
					dwnldLink = element;
			}
			if (dwnldLink != null)
			{
				downloadPage = "https://code.google.com" + dwnldLink.attr("href");
				project.getDownloads().addAll(getGoogleDownload(downloadPage));
			}
			
			//SET VCSREPOSITORY
			Elements sources = e.getElementsContainingText("Source");
			Element source = null;
			String sourcePage = "";
			for (Element element : sources) 
			{
				if(element.text().toString().contains("Source"))
					source = element;
			}
			if (source != null)
			{
				sourcePage = "https://code.google.com" + source.attr("href");
				project.getVcsRepositories().addAll(getGoogleVcsRepository(platform, sourcePage));
			}
			
		
			
			//SET CODE LICENSE
			Elements leftColumns = doc.getElementsByClass("pscolumnl");
			Element leftColumn = leftColumns.first();
			Element listLeftColumn = leftColumn.getElementsByTag("ul").first();
			Elements listElement = listLeftColumn.getElementsByTag("li");
			for (int i = 0; i < listElement.size(); i++)
			{
				if (listElement.get(i).text().equals("Code license"))
				{
					i++;
					boolean guard = false;
					while (!guard && i < listElement.size())
					{
						if (!listElement.get(i).attr("class").equals("psgap"))
						{
							String licenseName = listElement.get(i).text();
							boolean guardStart = true;
							boolean guardEnd = true;
							while (guardStart || guardEnd )
							{
								if(licenseName.codePointAt(0)==160)
									licenseName = licenseName.substring(1);
								else guardStart = false;
								if(licenseName.codePointAt(licenseName.length()-1)==160)
									licenseName.substring(0, licenseName.length()-1);
								else guardEnd = false;
							}
							License l = platform.getProjectRepositoryManager().getProjectRepository().getLicenses().findOneByName(licenseName);
							if(l==null)
							{
								l = licensePending.get(licenseName);
								if (l==null)
								{
									l = new License();
									Element linkLicense = listElement.get(i).getElementsByTag("a").first();
									l.setUrl(linkLicense.outerHtml().substring(linkLicense.outerHtml().indexOf("href")+6, linkLicense.outerHtml().indexOf("rel")-2));
									l.setName(listElement.get(i).text());
									platform.getProjectRepositoryManager().getProjectRepository().getLicenses().add(l);
									licensePending.put(licenseName, l);
								}
							}
							project.getLicenses().add(l);
							
						}
							
						else guard = true;
						i++;
					}
				}
			}
			if ((project != null) & (!projectToBeUpdated)) {	
				platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
				logger.info("Project " + project.getShortName() + " has bern added");
				
			}
			else
			{
				logger.info("Project " + project.getShortName() + " has bern updated");
			}
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
			return project;
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Unable to connect at " + projectUrl + "Importer exception:" + e1.getMessage());
			throw new WrongUrlException();
		}
	}
	
	
	private List<VcsRepository> getGoogleVcsRepository(Platform platform, String sourcePageLink) {
		List<VcsRepository> result = new ArrayList<VcsRepository>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = sourcePageLink;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e = doc.getElementById("checkoutcmd");
			if (e!=null)
			{
				VcsRepository vcsRepository = null;
				String vcs = e.text();
				String vcsUrl = e.text().substring(e.text().indexOf("http"));
				if (vcs.startsWith("git"))
				{
					vcsRepository = new GitRepository();
					vcsRepository.setUrl(vcsUrl);
					result.add(vcsRepository);
				}
				else if (vcs.startsWith("svn"))
				{
					vcsRepository = new SvnRepository();
					String tmp = vcsUrl.substring(0, vcsUrl.lastIndexOf("/")+1);
					vcsRepository.setUrl(tmp);
					result.add(vcsRepository);
				}
				else if (vcs.startsWith("hg"))
				{
					vcsRepository = new MercurialRepository();
					vcsRepository.setUrl(vcsUrl);
					result.add(vcsRepository);
				}
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer VcsRepository error");
		}
		return result;
	}

	private List<GoogleIssue> getGoogleIssueList(Platform platform, String url) {
		List<GoogleIssue> result = new ArrayList<GoogleIssue>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = url;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			
			//Pagination
			Element pagination = doc.getElementsByClass("pagination").first();
			Integer totPagination = Integer.parseInt(pagination.text().split(" ")[4]);
			Integer numPag = (totPagination % 100) == 0 ? totPagination/100:totPagination/100+1;
			//End pagination
			
			Element e = doc.getElementById("resultstable");
			e = e.getElementsByTag("tbody").first();
			Elements tableRows = e.getElementsByTag("tr");
			for (Element iterable_element : tableRows) 
			{
				String urlIssue = url.substring(0,url.length()-4) +
						"detail?id="+ iterable_element.getElementsByTag("td").get(1).getElementsByTag("a").first().text();
				GoogleIssue gi = getGoogleIssue(platform, urlIssue);
				result.add(gi);
				
				break;
			}
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve Issue list" + e1.getMessage());
		}
		return result;
	}
	
	
	private GoogleIssue getGoogleIssue (Platform platform, String url) 
	{
		GoogleIssue result = new GoogleIssue();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = url;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			
			Element e = doc.getElementById("issueheader");
			
			Element summary = e.getElementsByTag("span").first();
			result.setSummary(summary.text());
			Elements starsList = e.getElementsByTag("tr");
			String stars = starsList.get(1).getElementsByTag("td").get(1).text().split(" ")[0];
			if (stars!=null)
			{
				result.setStars(Integer.parseInt(stars));
				
			}
			result.setCreated_at(doc.getElementById("cursorarea").getElementsByClass("date").first().text());
			e = doc.getElementById("issuemeta");
			Elements menu = e.getElementsByTag("tr");
			String status = menu.get(0).getElementsByTag("td").first().text();
			if (status.equals("Started"))
				result.setStatus("Started");
			if (status.equals("New"))
				result.setStatus("New");
			if (status.equals("Accepted"))
				result.setStatus("Accepted");
			if (status.equals("Reviewed"))
				result.setStatus("Reviewed");
			if (status.equals("Acknowledged"))
				result.setStatus("Acknowledged");
			status = menu.get(1).getElementsByTag("td").first().text();
			boolean guardStart = true;
			boolean guardEnd = true;
			while (guardStart || guardEnd )
			{
				if(status.codePointAt(0)==160)
					status = status.substring(1);
				else guardStart = false;
				if(status.codePointAt(status.length()-1)==160)
					status.substring(0, status.length()-1);
				else guardEnd = false;
			}
			Person gu = userPending.get(status);
			if (gu==null)
			{
				gu = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(status);			
				if(gu==null)
				{
					gu = new GoogleUser();
					gu.setName(status);
					((GoogleUser)gu).setEmail(status);
				}
				userPending.put(status, gu);
			}
			result.setOwner((GoogleUser) gu);
			for(int i = 2; i < menu.size(); i++ )
			{
				Elements el = menu.get(i).getElementsByTag("td");
				if( el.size() != 0)
					if(el.first().getElementsByAttribute("colspan") != null)
					{
						for (Element element : el.first().getElementsByTag("div")) {
							String string = element.text();
							if(string.startsWith("Type"))
							{	if (string.substring(5).equals("Bug"))
									result.setType("Bug");
								if (string.substring(5).equals("Defect"))
									result.setType("Defect");
								if (string.substring(5).equals("Enhancement"))
									result.setType("Enhancement");
							}
							if(string.startsWith("Pri"))
							{	if (string.substring(4).equals("3") || string.substring(4).equals("Low") )
									result.setPriority("Low");
								if (string.substring(4).equals("2") || string.substring(4).equals("Medium"))
									result.setPriority("Medium");
								if (string.substring(4).equals("1") || string.substring(4).equals("High"))
									result.setPriority("High");
							}
							
						}
					}
			}
			boolean guard = false;
			int i = 0;
			while (!guard)
			{
				Element k = doc.getElementById("hc"+i);
				if (k==null)
					guard=true;
				else
				{
					i++;
					GoogleIssueComment gic = new GoogleIssueComment();
					gic.setText(k.getElementsByTag("pre").first().text());
					gic.setDate(k.getElementsByClass("date").first().text());
					result.getComments().add(gic);
				}
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve Issue" + e1.getMessage());
		}
		return result;
	}
	
	private List<GoogleUser> getPersonProject(Platform platform,
			String projectId) 
	{
		List<GoogleUser> result = new ArrayList<GoogleUser>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = projectId;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e = doc.getElementById("resultstable");
			e = e.getElementsByTag("tbody").first();
			Elements tableRows = e.getElementsByTag("tr");
			for (Element iterable_element : tableRows) 
			{
				Elements columns = iterable_element.getElementsByTag("td");
				String username = columns.get(0).text();
				username = username.trim();
				boolean guardStart = true;
				boolean guardEnd = true;
				while (guardStart || guardEnd )
				{
					if(username.codePointAt(0)==160)
						username = username.substring(1);
					else guardStart = false;
					if(username.codePointAt(username.length()-1)==160)
						username.substring(0, username.length()-1);
					else guardEnd = false;
				}
				Person gu = userPending.get(username);
				if (gu==null)
				{
					gu = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(username);			
					if(gu==null)
					{
						gu = new GoogleUser();
						gu.setName(username);
						((GoogleUser)gu).setEmail(columns.get(0).text());
						platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(gu);
						
					}
					userPending.put(username, gu);
					
				}
				String [] arrayRole = columns.get(1).text().split("\\+");
				for (String string : arrayRole) 
				{
					
					String role = string.trim();
					
					guardStart = true;
					guardEnd = true;
					while (guardStart || guardEnd )
					{
						if(role.codePointAt(0)==160)
							role = role.substring(1);
						else guardStart = false;
						if(role.codePointAt(role.length()-1)==160)
							role = role.substring(0, role.length()-1);
						else guardEnd = false;
					}
					Role gr = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(role);
					
					if (gr == null)
					{
						
						gr = rolePending.get(role);
						if(gr==null)
						{
							gr = new Role();
							gr.setName(role);
							rolePending.put(role, gr);
							platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gr);
						}
					}
					gu.getRoles().add(gr);				
				}
				result.add((GoogleUser) gu);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve Person list" + e1.getMessage());
		}
		return result;
	}

	private List<GoogleDownload> getGoogleDownload(String downloadPage)
	{
		List<GoogleDownload> result = new ArrayList<GoogleDownload>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		String URL_PROJECT = downloadPage;
		
		
			try {
				doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
				Element e = doc.getElementById("resultstable");
				if(e!=null)
				{
					e = e.getElementsByTag("tbody").first();
					Elements tableRows = e.getElementsByTag("tr");
					for (Element iterable_element : tableRows) {
						Elements columns = iterable_element.getElementsByTag("td");
						if(columns.size()>6)
						{
							GoogleDownload gd = new GoogleDownload();
							gd.setFileName(columns.get(1).text());
							gd.setUploaded_at(columns.get(3).text());
							gd.setUpdated_at(columns.get(4).text());
							gd.setSize(columns.get(5).text());
							try
							{
								gd.setDownloadCounts(Integer.parseInt(columns.get(6).text()));
							}
							catch(NumberFormatException exc)
							{
								logger.error("GoogleCode importer unable to load download from " + downloadPage + " " + exc.getMessage());
							}
							result.add(gd);
						}
						
					}
				
				}
				//result.add(e.toString());
			}
			catch (SocketTimeoutException e2)
			{
				logger.error("Google code importer error for retirve downaload list" + e2.getMessage());
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("Google code importer error for retirve downaload list" + e1.getMessage());
			}
			
		
		
		return result;
	}
	@Override
	public void importAll(Platform platform) 
	{	
		List<GoogleCodeProject> result = new ArrayList<GoogleCodeProject>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		String URL_PROJECT = "https://code.google.com/hosting/search?q=&sa=Search";
		
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e =  doc.getElementsByClass("mainhdr").first();
			String pagination = e.text();
			String endS = "";
			String startS = "";
			String totalItemS = "";
			String [] array = pagination.split(" ");				
			endS = array[3];
			startS = array[1];
			totalItemS = array[5];
			Integer start = Integer.parseInt(startS);
			Integer end = Integer.parseInt(endS);
			Integer totalItem = Integer.parseInt(totalItemS);
			if(end <= totalItem)
			{
				int i = 0;
				
				while (i <= totalItem )
				{					
					String url = "https://code.google.com/hosting/search?q=&filter=0&mode=&start=" + i;
					logger.info("Processing the content of the page " + url + " from project " + i);
					result.addAll(importPage(url, platform));
					
					i= i + 10;
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve project list" + e1.getMessage());
		}
	}
	@Override
	public void importProjects(Platform platform, int numberOfProjects) 
	{	
		List<GoogleCodeProject> result = new ArrayList<GoogleCodeProject>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		String URL_PROJECT = "https://code.google.com/hosting/search?q=&sa=Search";
		
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e =  doc.getElementsByClass("mainhdr").first();
			String pagination = e.text();
			String endS = "";
			String startS = "";
			String totalItemS = "";
			String [] array = pagination.split(" ");				
			endS = array[3];
			startS = array[1];
			totalItemS = array[5];
			Integer start = Integer.parseInt(startS);
			Integer end = Integer.parseInt(endS);
			Integer totalItem = Integer.parseInt(totalItemS);
			if(end <= totalItem)
			{
				int i = 0;
				
				while (i <= totalItem )
				{					
					String url = "https://code.google.com/hosting/search?q=&filter=0&mode=&start=" + i;
					logger.info("Processing the content of the page " + url + " from project " + i);
					result.addAll(importPage(url, platform, numberOfProjects));
					i= i + 10;
					if (result.size() > numberOfProjects)
						break;
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve project list" + e1.getMessage());
		}
	}
	
	
	private List<GoogleCodeProject> importPage(String url, Platform platform)
	{
		List<GoogleCodeProject> result = new ArrayList<GoogleCodeProject>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		//String URL_PROJECT = "https://code.google.com/hosting/search?q=&sa=Search";
				
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
			Element e =  doc.getElementById("serp");
			Elements projectList = e.getElementsByTag("table");
			
			for (Element element : projectList) 
			{
				String string = element.getElementsByTag("td").get(1).getElementsByTag("a").first().attr("href");
				string = string.substring(3);
				string = string.substring(0, string.length()-1);
				try{
					result.add(importProject(string,platform));	
				} catch (WrongUrlException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve single project page list " + e1.getMessage());
		}
		return result;
	}
	
	private List<GoogleCodeProject> importPage(String url, Platform platform, int numberOfProjects)
	{
		List<GoogleCodeProject> result = new ArrayList<GoogleCodeProject>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		//String URL_PROJECT = "https://code.google.com/hosting/search?q=&sa=Search";
				
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
			Element e =  doc.getElementById("serp");
			Elements projectList = e.getElementsByTag("table");
			for (Element element : projectList) 
			{
				String string = element.getElementsByTag("td").get(1).getElementsByTag("a").first().attr("href");
				string = string.substring(3);
				string = string.substring(0, string.length()-1);
				try{
					result.add(importProject(string,platform));	
				} catch (WrongUrlException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(result.size() > numberOfProjects)
					break;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Google code importer error for retirve single project page list " + e1.getMessage());
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
			GoogleCodeProject project = null;
			Project projectTemp = null;
			while (iprojects.hasNext()) {
				projectTemp = iprojects.next();
				if (projectTemp instanceof GoogleCodeProject) {
					project = (GoogleCodeProject)projectTemp;
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
	@Override
	public boolean isProjectInDBByUrl(String url, Platform platform) throws WrongUrlException
	{
		return isProjectInDB(getProjectIdFromUrl(url), platform);
	}
	@Override
	public GoogleCodeProject importProjectByUrl(String url, Platform platform) throws WrongUrlException
	{
		return importProject(getProjectIdFromUrl(url), platform);
	}
	
	private String getProjectIdFromUrl(String url) throws WrongUrlException
	{
		//https://code.google.com/p/firetray/
		url = url.replace("http://", "");
		url = url.replace("https://", "");
		url = url.replace("www.", "");
		if (url.startsWith("code.google.com/p/")) {
			url= url.replace("code.google.com/p/", "");
			if(url.contains("?"))
				url = url.substring(0, url.indexOf("?"));
			if(url.endsWith("/"))
				url = url.substring(0, url.length()-1);
			return url;
		}
		else throw new WrongUrlException();
	}

	
}
