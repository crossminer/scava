/*******************************************************************************
 * Copyright (c) 2014 SCAVA Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.eclipse.scava.repository.model.sourceforge.importer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.ImportData;
import org.eclipse.scava.repository.model.License;
import org.eclipse.scava.repository.model.Person;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.Role;
import org.eclipse.scava.repository.model.cc.wiki.Wiki;
import org.eclipse.scava.repository.model.importer.IImporter;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.eclipse.scava.repository.model.sourceforge.*;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.jsoup.nodes.Node;
import org.jsoup.select.*;
import org.jsoup.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class SourceforgeProjectImporter implements IImporter {
	protected OssmeterLogger logger;
	public SourceforgeProjectImporter()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.sourceforge");
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
	public void importAll(Platform platform) {
		
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		Integer numPagesToBeScanned;
		String url = null;	
		List<String> toRetry = new ArrayList<String>();
		
		try {
			doc = Jsoup.connect("http://sourceforge.net/directory/").get();
			content = doc.getElementById("result_count");
			numPagesToBeScanned = new Integer(content.toString().substring(("<p id=\"result_count\"> Showing page 1 of ".length()), content.toString().length()-6));
			int count = 0;

			String lastImportedProject = null;
			int startingPage = 1;
			int startingProject = 0;
			
			if (platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().size() != 0) {
				lastImportedProject = new String(platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().getLastImportedProject());
				if(lastImportedProject.equals(""))
				{
					startingPage = 1;
					startingProject = 0;
				}
				else
				{
					startingPage = new Integer((lastImportedProject.split("/"))[0]);
					startingProject = new Integer((lastImportedProject.split("/"))[1]);
				}
			} else {
				ImportData id = new ImportData();
				id.setLastImportedProject(new String());
				platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().add(id);	
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}
		
			
			for (int j = startingPage; j < (numPagesToBeScanned); j++)
			{
				logger.info("Scanning the projects directory page " + j + " of " + numPagesToBeScanned);
				try {
					String URL_PROJECT = "http://sourceforge.net/directory/?page="+j;
					url = URL_PROJECT;
					doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
					content = doc.getElementsByClass("projects").first();
					Elements e = content.getElementsByClass("project_info");
					for (int i = startingProject; i < e.size(); i++){
						
							url = e.get(i).getElementsByAttributeValue("itemprop", "url").first().attr("href");
							count++;
							logger.info("--> (" + count + ") " + url);
							importProject(url.split("/")[2], platform);
							lastImportedProject = new String(j + "/" + i);
							platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().setLastImportedProject(lastImportedProject);
							platform.getProjectRepositoryManager().getProjectRepository().sync();
						
					}
					startingProject=0;
				}
				catch(SocketTimeoutException  st) {
					logger.error("Page summary: Read timed out during the connection to " + url + ". I'll retry later with it.");
					toRetry.add(url);
//					continue;
				}
				catch(IOException e) {
					logger.error("Page summary: No further details available for the project " + url );
//					continue;
				}
			}		
		} 
		catch(SocketTimeoutException st) {
			logger.error("Read timed out during the connection to the projects directory, please try again.");
		}
		catch(Exception e){
				logger.error(e.getMessage());
		}
		if (! toRetry.isEmpty()) {
			logger.info("Trying again with...");
			Iterator<String> it = toRetry.iterator();
			String el;
			while (it.hasNext()) {
				el = (String) it.next();
				logger.info(el);
				if ((platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByName(el.split("/")[2])) != null) {
					try {
						importProject(el.split("/")[2], platform);
					} catch (WrongUrlException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	@Override
	public void importProjects(Platform platform, int numberOfProjects) {
		
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		Integer numPagesToBeScanned;
		String url = null;	
		List<String> toRetry = new ArrayList<String>();
		
		try {
			doc = Jsoup.connect("http://sourceforge.net/directory/").get();
			content = doc.getElementById("result_count");
			numPagesToBeScanned = new Integer(content.toString().substring(("<p id=\"result_count\"> Showing page 1 of ".length()), content.toString().length()-6));
			int count = 0;

			String lastImportedProject = null;
			int startingPage = 1;
			int startingProject = 0;
			
			if (platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().size() != 0) {
				lastImportedProject = new String(platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().getLastImportedProject());
				if(lastImportedProject.equals(""))
				{
					startingPage = 1;
					startingProject = 0;
				}
				else
				{
					startingPage = new Integer((lastImportedProject.split("/"))[0]);
					startingProject = new Integer((lastImportedProject.split("/"))[1]);
				}
			} else {
				ImportData id = new ImportData();
				id.setLastImportedProject(new String());
				platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().add(id);	
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}
		
			int iteration = 0;
			for (int j = startingPage; j < (numPagesToBeScanned); j++)
			{
				logger.info("Scanning the projects directory page " + j + " of " + numPagesToBeScanned);
				try {
					String URL_PROJECT = "http://sourceforge.net/directory/?page="+j;
					url = URL_PROJECT;
					doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
					content = doc.getElementsByClass("projects").first();
					Elements e = content.getElementsByClass("project_info");
					for (int i = startingProject; i < e.size(); i++){
						
							url = e.get(i).getElementsByAttributeValue("itemprop", "url").first().attr("href");
							count++;
							logger.info("--> (" + count + ") " + url);
							importProject(url.split("/")[2], platform);
							lastImportedProject = new String(j + "/" + i);
							platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().setLastImportedProject(lastImportedProject);
							platform.getProjectRepositoryManager().getProjectRepository().sync();
							iteration ++;
							if (iteration > numberOfProjects)
								break;
						
					}
					startingProject=0;
					if (iteration > numberOfProjects)
						break;
					
				}
				catch(SocketTimeoutException  st) {
					logger.error("Page summary: Read timed out during the connection to " + url + ". I'll retry later with it.");
					toRetry.add(url);
//					continue;
				}
				catch(IOException e) {
					logger.error("Page summary: No further details available for the project " + url );
//					continue;
				}
			}		
		} 
		catch(SocketTimeoutException st) {
			logger.error("Read timed out during the connection to the projects directory, please try again.");
		}
		catch(Exception e){
				logger.error(e.getMessage());
		}
		if (! toRetry.isEmpty()) {
			logger.info("Trying again with...");
			Iterator<String> it = toRetry.iterator();
			String el;
			while (it.hasNext()) {
				el = (String) it.next();
				logger.info(el);
				
				if ((platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByName(el.split("/")[2])) != null) {
					try {
						importProject(el.split("/")[2], platform);
					} catch (WrongUrlException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
	}

	@Override
	public SourceForgeProject importProject(String projectId, Platform platform) throws WrongUrlException  {
		
		Boolean projectToBeUpdated = false;
		
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		Iterable<Project> projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = projects.iterator();
		SourceForgeProject project = null;
		Project projectTemp = null;
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof SourceForgeProject) {
				project = (SourceForgeProject)projectTemp;
				if (project.getShortName().equals(projectId)) {
					projectToBeUpdated = true;
					logger.info("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
					break;
				}	
			}
		}
		
		if (!projectToBeUpdated)  {
			project = new SourceForgeProject();
		}
		else {
			// Clear containments to be updated
						project.getOs().clear();
						project.getTopics().clear();
						project.getProgramminLanguages().clear();
						project.getAudiences().clear();
						project.getEnvironments().clear();
						project.getCategories().clear();
						project.getFeatureRequests().clear();
						project.getPatches().clear();
						project.getFeatureRequests().clear();
						project.getBugs().clear();		
						project.getCommunicationChannels().clear();
						project.getVcsRepositories().clear();
		}
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = null;
			org.w3c.dom.Document doc = null;
			try {
				db = dbf.newDocumentBuilder();
				doc = db.parse("https://sourceforge.net/rest/p/" + projectId + "?doap");
			} catch (ParserConfigurationException e1) {
				throw new WrongUrlException();
			} catch (SAXException | IOException e1) {
				throw new WrongUrlException();
			}
			doc.getDocumentElement().normalize();
			/*Si puo' eliminare*/doc.getElementById("creation_date");
			Element nList = (Element)doc.getElementsByTagName("Project").item(0);	
			project.setShortName(projectId); 
			String app = getXMLNodeValue(nList,"name");
			project.setName(app);
			app = getXMLNodeValue(nList,"created");
			project.setCreated(app);
			app = getXMLNodeValue(nList,"sf:id");
			project.setProjectId(Integer.parseInt(app));
			app = getXMLNodeValue(nList,"sf:private");
			project.set_private(Integer.parseInt(app));
			project.setShortDesc(getXMLNodeValue(nList,"shortdesc"));
			
//	###########################################################
//			//percentile & ranking not present in xml format	#
//																#
//			app = currentProg.get("percentile").toString();		#
//			project.setPercentile(Float.parseFloat(app));		#
//			app = currentProg.get("ranking").toString();		#
//			project.setRanking(Integer.parseInt(app));			#
//##############################################################			
			try
			{
				app = nList.getElementsByTagName("download-page").item(0).getAttributes().item(0).getNodeValue();
				project.setDownloadPage(app);
			}
			catch (Exception e)
			{
				logger.info("Retrive value for key: download-page trhows an exception.");
			}	
			
			/////Use JSON resource///////////
			try {
				InputStream isJSON = null;
				String jsonText = null;
				isJSON = new URL("https://sourceforge.net/rest/p/" + projectId).openStream();
				BufferedReader rdJSON = new BufferedReader(new InputStreamReader(isJSON, Charset.forName("UTF-8")));	
				jsonText = readAll(rdJSON);
				JSONObject currentProg=(JSONObject)JSONValue.parse(jsonText);
				project.setSupportPage((String)currentProg.get("preferred_support_url"));
				project.setSummary((String)currentProg.get("summary"));
				project.setHomePage((String)currentProg.get("external_homepage"));
				JSONObject categories = ((JSONObject)currentProg.get("categories"));
				if (categories != null) {
				JSONArray topics = (JSONArray)categories.get("topic");
					for (Object object : topics) {
						Topic topic  = new Topic();
						topic.setName(((JSONObject)object).get("fullname").toString());
						project.getTopics().add(topic);
					}
				}
				
				
			} catch (MalformedURLException e1) {
				throw new WrongUrlException();
			} catch (IOException e1) {
				throw new WrongUrlException();
			}
//			// BEGIN Management of Operating Systems
			NodeList nl = nList.getElementsByTagName("os");			
			
			for (int i=0; i < nl.getLength(); i++){					
				OS os = null;
				os = new OS();
				try
				{
					os.setName(nl.item(i).getFirstChild().getNodeValue());
					project.getOs().add(os);
				}
				catch(Exception e)
				{
					logger.info("Project " + projectId + " unable to import os");
				}
			} 
			// END Management of Operating Systems 
		
//			BEGIN Management of Programming Languages
			nl = nList.getElementsByTagName("programming-language");
			for (int i=0; i < nl.getLength(); i++){					
				try
				{
			    	ProgrammingLanguage pl = new ProgrammingLanguage();
					pl.setName(nl.item(i).getFirstChild().getNodeValue());
					project.getProgramminLanguages().add(pl);
				}
				catch ( Exception e)
				{
					logger.info("Project " + projectId + " unable to import programmin languages");
				}
			}
		    
			// END Management of Programming Languages 
			
			// BEGIN Management of Audiences
		    nl = nList.getElementsByTagName("audience");
			for (int i=0; i < nl.getLength(); i++){					
				Audience audience = null;
				try
				{
					audience = new Audience();
					audience.setName(nl.item(i).getFirstChild().getNodeValue());
					project.getAudiences().add(audience);
				}
				catch (Exception e)
				{
					logger.info("Project " + projectId + " unable to import Audiences");
				}
				
			}
			// END Management of Audiences
			
			// BEGIN Management of Environments
			nl = nList.getElementsByTagName("sf:environment");
			for (int i=0; i < nl.getLength(); i++){					
		    	Environment environment = null;
		    	try
		    	{
			    	environment = new Environment();
			    	environment.setName(nl.item(i).getFirstChild().getNodeValue());
					project.getEnvironments().add(environment);
		    	}
		    	catch (Exception e)
				{
					logger.info("Project " + projectId + " unable to import environment");
				}
				
			}
			// END Management of Environments			
		    
			
			String type = null;
			nl = nList.getElementsByTagName("sf:feature");
			for (int i=0; i < nl.getLength(); i++){					
		    	try
		    	{
		    		type = nl.item(i).getFirstChild().getFirstChild().getFirstChild().getNodeValue();
		    		if (type != null)
		    		{
			    		if (type.equals("Feature Requests"))
						{
							FeatureRequest tracker = new FeatureRequest();
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							tracker.setName(type);
							tracker.setLocation(s);
							project.getFeatureRequests().add(tracker);
						}
			    		else if (type.equals("Patches"))
						{
							Patch tracker = new Patch();
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							tracker.setName(type);
							tracker.setLocation(s);
							project.getPatches().add(tracker);
						}
			    		else if (type.contains("Support"))
						{
							SupportRequest tracker = new SupportRequest();
							tracker.setName(type);
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							tracker.setLocation(s);
							project.getSupportRequests().add(tracker);
						}
			    		else if (type.equals("Bugs"))
						{
							Bug tracker = new Bug();
							tracker.setName(type);
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							tracker.setLocation(s);
							project.getBugs().add(tracker);
						}
			    		
			    		else if (type.toLowerCase().equals("discussion"))
						{
							Discussion discussion = new Discussion();
							
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							s = s.replace("sourceforge.net/p/", "sourceforge.net/rest/p/");
							discussion.setUrl(s);
							discussion.setNonProcessable(false);
							project.getDiscussion().add(discussion);
							project.getCommunicationChannels().add(discussion);
						}
			    		else if (type.toLowerCase().equals("donations"))
						{
							Donation donation = new Donation();
							
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							donation.setComment(s);
							project.setDonation(donation);
						}
			    		else if (type.toLowerCase().equals("wiki"))
			    		{
			    			Wiki wiki = new Wiki();
							String s = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							wiki.setUrl(s);
							wiki.setNonProcessable(true);
							project.getCommunicationChannels().add(wiki);
			    		}
			    		
			    		else if (type.toLowerCase().equals("svn"))
						{
							org.jsoup.nodes.Document doc2;
							
							String URL_PROJECT = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							doc2 = Jsoup.connect(URL_PROJECT).timeout(10000).get();
							org.jsoup.nodes.Element content = doc2.getElementById("access_urls");
							Elements els = content.getElementsByTag("a");
							for (org.jsoup.nodes.Element element : els) {
								SvnRepository repository = new SvnRepository();
								repository.setName(type + "_" +projectId);
								String urlSVN = element.attr("data-url");
								if (urlSVN != null)
									if(urlSVN.startsWith("svn checkout "))
										urlSVN = urlSVN.substring(13);
								repository.setUrl(urlSVN);
								project.getVcsRepositories().add(repository);
							}
						
						}
			    		else if (type.toLowerCase().equals("git"))
						{
							org.jsoup.nodes.Document doc2;
							
							String URL_PROJECT = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							doc2 = Jsoup.connect(URL_PROJECT).timeout(10000).get();
							org.jsoup.nodes.Element content = doc2.getElementById("access_urls");
							Elements els = content.getElementsByTag("a");
							for (org.jsoup.nodes.Element element : els) {
								GitRepository repository = new GitRepository();
								repository.setName(type + "_" +projectId);
								String urlSVN = element.attr("data-url");
								if (urlSVN != null)
									if(urlSVN.startsWith("git clone "))
										urlSVN = urlSVN.substring(9);
								repository.setUrl(urlSVN);
								project.getVcsRepositories().add(repository);
							}
						
						}
			    		else if (type.toLowerCase().equals("code"))
						{
							org.jsoup.nodes.Document doc2;
							
							String URL_PROJECT = nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue();
							doc2 = Jsoup.connect(URL_PROJECT).timeout(10000).get();
							org.jsoup.nodes.Element content = doc2.getElementById("access_urls");
							if(content!=null) {
								org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) content.getElementsByTag("a").first();
								String urlSVN = ((Node) element).attr("data-url");
								if (urlSVN != null) {
									if(urlSVN.startsWith("git clone "))	{
										GitRepository repository = new GitRepository();
										repository.setName(type + "_" +projectId);
										urlSVN = urlSVN.replace("git clone ", "");
										repository.setUrl(urlSVN);
										project.getVcsRepositories().add(repository);
									}
									if(urlSVN.startsWith("svn checkout ")) {
										urlSVN = urlSVN.replace("svn checkout ", "");
										SvnRepository repository = new SvnRepository();
										repository.setName(type + "_" +projectId);
										repository.setUrl(urlSVN);
										project.getVcsRepositories().add(repository);
									}
								}
							}
						
						}
						
					}	
					
		    	}
		    	catch (Exception e)
				{
		    		logger.info("Project " + projectId + " unable to import feature " + type);

				}
				
			}		
			// BEGIN Management of CommunicationsChannels
			nl = nList.getElementsByTagName("mailing-list");
			for (int i=0; i < nl.getLength(); i++){					
		    	MailingList mailingList = null;
		    	try
		    	{
			    	mailingList = new MailingList();
			    	mailingList.setUrl(nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue());
			    	if(mailingList.getUrl().startsWith("news://")
							|| mailingList.getUrl().startsWith("git://")
							|| mailingList.getUrl().startsWith("svn://"))
			    		mailingList.setNonProcessable(false);
			    	else mailingList.setNonProcessable(true);
					project.getCommunicationChannels().add(mailingList);
		    	}
		    	catch (Exception e)
				{
		    		logger.info("Project " + projectId + " unable to import mailing list");

				}
				
			}
			// END Management of CommunicationsChannels		
			
			// BEGIN Management of Licenses
		    nl = nList.getElementsByTagName("license");
			for (int i=0; i < nl.getLength(); i++){					
		    	
		    	try
		    	{
		    		License license = platform.getProjectRepositoryManager().getProjectRepository().getLicenses().findOneByName("");
			    	if(license == null)
						license = new License();
			    		license.setName(nl.item(i).getFirstChild().getNodeValue());
			    		{
			    		platform.getProjectRepositoryManager().getProjectRepository().getLicenses().add(license);
			    	}
			    	project.getLicenses().add(license);
					
		    	}
		    	catch (Exception e)
				{
		    		logger.info("Project " + projectId + " unable to import license");
				}
				
			}
		    // END Management of Licenses 
			
			// BEGIN Management of Persons
			Role role = platform.getProjectRepositoryManager().getProjectRepository().
					getRoles().findOneByName("maintainer");
			if (role==null)
			{
				role = new Role();
				role.setName("maintainer");
				platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(role);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}

			nl = nList.getElementsByTagName("maintainer");
			for (int i=0; i < nl.getLength(); i++){					
		    	
		    	try
		    	{
		    	String name = "";
		    		name = nl.item(i).getFirstChild().getFirstChild().getFirstChild().getNodeValue();
		    			Person maintainer = platform.getProjectRepositoryManager().
		    				getProjectRepository().getPersons().findOneByName(name);
		    		if ((maintainer != null) & !checkRole(maintainer, role) )
		    		{
						maintainer.getRoles().add(role);
		    		}
					if(maintainer == null)
			    	{
						maintainer = new Person();
			    		
						maintainer.setName(nl.item(i).getFirstChild().getFirstChild().getFirstChild().getNodeValue());
						maintainer.setHomePage(nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue());
			    		maintainer.getRoles().add(role);
			    		platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(maintainer);
			    	}
			    	project.getPersons().add(maintainer);
		    	}
		    	catch (Exception e)
				{
					logger.info("Project " + projectId + " unable to import person (mainteners)");
				}
			}
			role = null;
			role = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("developer");
			if (role==null)
			{
				role = new Role();
				role.setName("developer");
				platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(role);
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}

			nl = nList.getElementsByTagName("developer");
			for (int i=0; i < nl.getLength(); i++){					
		    	
		    	try
		    	{
		    		String name = "";
		    		name = nl.item(i).getFirstChild().getFirstChild().getFirstChild().getNodeValue();
		    		Person developer = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(name);
		    		if ((developer != null) & !checkRole(developer, role) )
		    		{
						developer.getRoles().add(role);
		    		}
					if(developer == null)
			    	{
						developer = new Person();
			    		
						developer.setName(nl.item(i).getFirstChild().getFirstChild().getFirstChild().getNodeValue());
						developer.setHomePage(nl.item(i).getFirstChild().getLastChild().getAttributes().item(0).getNodeValue());
			    		developer.getRoles().add(role);
			    		platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(developer);
			    	}
			    	project.getPersons().add(developer);
		    	}
		    	catch (Exception e)
				{
					logger.info("Project " + projectId + " unable to import person (mainteners)");
				}
			}
			
			if (!projectToBeUpdated) {
				platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
			}
			
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			logger.info("Project " + projectId + " is imported");
		} catch (Exception e){
			logger.error("Project: " + projectId + " unknow error during import data.");
			return null;
		}
		
	
		return project;		
		
	}
	
	
	private String getXMLNodeValue(Element nList, String key) {
		
		NodeList nl = nList.getElementsByTagName(key);
		String result = null;
		try
		{
		if (nl != null)
			if (nl.getLength()>0)
				if (nl.item(0).getFirstChild() != null)
					result = nl.item(0).getFirstChild().getNodeValue();
		}
		catch(Exception e)
		{
			logger.error("Retrive value for key:" + key +" trhows an exception.");
		}
		return result;
		
	}
	private boolean checkRole(Person p, Role r) {
		boolean hasRole = false;
		Iterator<Role> iroles = null;
		
		try {
			iroles = p.getRoles().iterator();
		} catch (NullPointerException e) {
			return hasRole;
		}
		
		Role role = null;
		while (iroles.hasNext()){
			role = iroles.next(); 
			if (role.getName().equals(r.getName())) {
				hasRole = true;
				break;
			}
		}
		
		return hasRole;
	}
	@Override
	public boolean isProjectInDB(String projectId, Platform platform)
	{
		try 
		{
			Iterable<Project> projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
			Iterator<Project> iprojects = projects.iterator();
			SourceForgeProject project = null;
			Project projectTemp = null;
			while (iprojects.hasNext()) {
				projectTemp = iprojects.next();
				if (projectTemp instanceof SourceForgeProject) {
					project = (SourceForgeProject)projectTemp;
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
		return isProjectInDB(getProjectIdFromUrl(url),platform);
	}
	@Override
	public SourceForgeProject importProjectByUrl(String url, Platform platform) throws WrongUrlException
	{
		return importProject(getProjectIdFromUrl(url), platform);
	}
	
	private String getProjectIdFromUrl(String url) throws WrongUrlException
	{
		url = url.replace("http://", "");
		url = url.replace("https://", "");
		url = url.replace("www.", "");
		if (url.startsWith("sourceforge.net/projects/")) {
			url= url.replace("sourceforge.net/projects/", "");
			if(url.contains("?"))
				url = url.substring(0, url.indexOf("?"));
			if(url.endsWith("/"))
				url = url.substring(0, url.length()-1);
			return url;
		}
		else throw new WrongUrlException();
	}

}
