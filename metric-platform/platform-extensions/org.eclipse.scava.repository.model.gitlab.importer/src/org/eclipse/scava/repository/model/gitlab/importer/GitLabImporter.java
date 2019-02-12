/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.gitlab.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.logging.OssmeterLogger;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.gitlab.GitLabRepository;
import org.eclipse.scava.repository.model.importer.IImporter;
import org.eclipse.scava.repository.model.importer.dto.Credentials;
import org.eclipse.scava.repository.model.importer.exception.ProjectUnknownException;
import org.eclipse.scava.repository.model.importer.exception.RepoInfoNotFound;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GitLabImporter implements IImporter {

	protected OssmeterLogger logger;

	public GitLabImporter() {
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.gitLab");
	}
	
	public static void main(String[] args) {
		try {
			new GitLabImporter().importProject("gitlab.ow2.org/asm/asm", null);
		} catch (ProjectUnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongUrlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepoInfoNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Project importProject(String projectId, Platform platform)
			throws ProjectUnknownException, WrongUrlException, RepoInfoNotFound {
		try {
			// Assuming projectId is in the form: gitlabhost.org/user/repo,
			// because of ProjectImporter, we need to undo the strip
			logger.info("---> processing repository " + projectId);
			String fullProjectURI = "https://" + projectId;
			URI projectURI = new URI(fullProjectURI);
			String host = projectURI.getHost();
			String path = projectURI.getPath().substring(1, projectURI.getPath().length());
			
			// API Call is in the form: https://gitlabhost.org/api/v4/projects/user%2Frepo
			String apiURI = "https://" + host + "/api/v4/projects/" + path.replace("/", "%2F");
			logger.info("Gathering project information from " + apiURI);
			
			URL apiURL = new URL(apiURI);
			URLConnection conn = apiURL.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = (JSONObject) JSONValue.parse(jsonText);
			
			GitLabRepository repo = new GitLabRepository();
			
			// NPEs everywhere
			repo.setClone_url(json.get("http_url_to_repo").toString());
			repo.setDescription(json.get("description").toString());
			repo.setName(json.get("name").toString());
			repo.setFull_name(json.get("name").toString());
			repo.setGit_url(json.get("ssh_url_to_repo").toString());
			repo.setHomePage(json.get("web_url").toString());
			
			repo.setShortName(platform.getProjectRepositoryManager().generateUniqueId(repo));
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(repo);
			platform.getProjectRepositoryManager().getProjectRepository().sync();
			
			logger.info("Project has been imported");
			
			return repo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void importAll(Platform platform) throws RepoInfoNotFound {
		throw new UnsupportedOperationException();
	}

	@Override
	public GitLabRepository importProjectByUrl(String url, Platform platform)
			throws WrongUrlException, ProjectUnknownException, RepoInfoNotFound {
		throw new UnsupportedOperationException();
	}

	@Override
	public void importProjects(Platform platform, int numberfOfProjects) throws WrongUrlException, RepoInfoNotFound {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isProjectInDB(String projectId, Platform platform)
			throws WrongUrlException, ProjectUnknownException, MalformedURLException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isProjectInDBByUrl(String projectId, Platform platform)
			throws WrongUrlException, ProjectUnknownException, MalformedURLException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCredentials(Credentials credentials) {
		throw new UnsupportedOperationException();
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
