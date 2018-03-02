/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.IImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.GithubUser;
import org.eclipse.scava.business.model.Stargazers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("Ossmeter")
public class OssmeterImporter implements IImporter {

	private static final String UTF8 = "UTF-8";
	@Autowired
	private GithubUserRepository userRepository;

	private static final int pageSize = 100;

	@Value("${ossmeter.url}")
	private String ossmeterUrl;

	@Autowired
	private ArtifactRepository projectRepository;

	private static final Logger logger = Logger.getLogger(OssmeterImporter.class);

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
			sb.append((char) cp);
		return sb.toString();
	}

	@Override
	public Artifact importProject(String projectName) throws IOException {
		
		Artifact result = projectRepository.findOneByName(projectName);
		if (result == null)
			result = new Artifact();
		URL url = new URL(ossmeterUrl + "projects/p/" + projectName);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream is = connection.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
		String jsonText = readAll(bufferReader);
		JSONObject obj = (JSONObject) JSONValue.parse(jsonText);
		result.setClone_url((String) obj.get("clone_url"));
		result.setFullName((String) obj.get("full_name"));
		result.setFork((boolean) obj.get("fork"));
		result.setGit_url((String) obj.get("git_url"));
		result.setHtml_url((String) obj.get("html_url"));
		result.setName((String) obj.get("name"));
		result.setPrivate_((boolean) obj.get("_private"));
		result.setShortName((String) obj.get("shortName"));
		result.setSize((long) obj.get("size"));
		result.setSsh_url((String) obj.get("ssh_url"));
		result.setSvn_url((String) obj.get("svn_url"));
		is.close();
		bufferReader.close();
		result.setStarred(getStargazers(projectName));
		storeGithubUser(result.getStarred(), result.getFullName());
		result.setDependencies(getDependencies(projectName));	
		projectRepository.save(result);
		return result;
	}
	
	private List<Stargazers> getStargazers(String artId){
		List<Stargazers> result = new ArrayList<>();
		try {
			
			URL url = new URL(ossmeterUrl + "raw/projects/p/" + artId + "/m/stars");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
			String jsonText = readAll(bufferReader);
			JSONArray array = (JSONArray) JSONValue.parse(jsonText);
			
			for (Object object : array) {
				Stargazers s = new Stargazers();
				s.setDatestamp((String) ((JSONObject) object).get("datestamp"));
				s.setLogin((String) ((JSONObject) object).get("login"));
				result.add(s);
			}
			is.close();
			bufferReader.close();
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
		return result;
	}
	
	private List<String> getDependencies(String artId){
		List<String> result = new ArrayList<>();
		try {
			URL url = new URL(ossmeterUrl + "raw/projects/p/" + artId + "/m/dependencies");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
			String jsonText = readAll(bufferReader);
			JSONArray array = (JSONArray) JSONValue.parse(jsonText);
			for (Object object : array) {
				String s = (String) ((JSONObject) object).get("dependency");
				result.add(s);
			}
			is.close();
			bufferReader.close();
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public void importAll() {
		boolean guard = true;
		int page = 0;
		while (guard) {
			try {
				URL url = new URL(ossmeterUrl + "projects/?page=" + page + "&size=" + pageSize);
				URLConnection connection = url.openConnection();
				connection.connect();
				InputStream is = connection.getInputStream();
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
				String jsonText = readAll(bufferReader);
				JSONArray projects = (JSONArray) JSONValue.parse(jsonText);
				if (projects.isEmpty())
					guard = false;
				else
					for (Object object : projects)
						importProject((String)((JSONObject) object).get("name"));
				page++;
			} catch (IOException e) {
				guard = false;
				logger.error(e.getMessage());
			}
		}

	}

	@Override
	public void storeGithubUser(List<Stargazers> starred, String repoName) {
		for (Stargazers stargazer : starred) {
			GithubUser user = userRepository.findOneByLogin(stargazer.getLogin());
			if(user == null){
				user = new GithubUser();
				user.setLogin(stargazer.getLogin());
			}
			user.getStarredRepo().add(repoName);
			userRepository.save(user);
		}
	}
	@Override
	public void storeGithubUserCommitter(List<GithubUser> committers, String repoName) {
		for (GithubUser committer : committers) {
			GithubUser user = userRepository.findOneByLogin(committer.getLogin());
			if(user == null){
				user = new GithubUser();
				user.setLogin(committer.getLogin());
			}
			user.getCommitsToRepo().add(repoName);
			userRepository.save(user);
		}
	}

}
