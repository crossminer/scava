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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.scava.business.IImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.integration.MavenLibraryRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.GithubUser;
import org.eclipse.scava.business.model.MavenLibrary;
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
	private String ossmeterUrl2;

	@Autowired
	private MavenLibraryRepository mavenLibraryRepository;

	@Autowired
	private ArtifactRepository projectRepository;

	@Autowired
	private GithubImporter importer;
	private static final Logger logger = LoggerFactory.getLogger(OssmeterImporter.class);

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
			sb.append((char) cp);
		return sb.toString();
	}

	private Artifact setArtifactFromJson(Artifact result, JSONObject obj) {
		if (obj.get("clone_url") != null)
			result.setClone_url((String) obj.get("clone_url"));
		if (obj.get("full_name") != null)
			result.setFullName((String) obj.get("full_name"));
		if (obj.get("fork") != null)
			result.setFork((boolean) obj.get("fork"));
		if (obj.get("git_url") != null)
			result.setGit_url((String) obj.get("git_url"));
		if (obj.get("html_url") != null)
			result.setHtml_url((String) obj.get("html_url"));
		if (obj.get("name") != null)
			result.setName((String) obj.get("name"));
		if (obj.get("_private") != null)
			result.setPrivate_((boolean) obj.get("_private"));
		if (obj.get("shortName") != null)
			result.setShortName((String) obj.get("shortName"));
		if (obj.get("size") != null)
			result.setSize((long) obj.get("size"));
		if (obj.get("ssh_url") != null)
			result.setSsh_url((String) obj.get("ssh_url"));
		if (obj.get("svn_url") != null)
			result.setSvn_url((String) obj.get("svn_url"));
		if (obj.get("shortName") != null)
			result.setMetricPlatformId((String) obj.get("shortName"));
		if (obj.get("description") != null)
			result.setDescription((String) obj.get("description"));
		if (obj.get("descritpion") != null)
			result.setReadmeText((String) obj.get("description"));
		return result;
	}

	@Override
	public Artifact importProject(String projectName, String access_token) throws IOException {

		Artifact result = projectRepository.findOneByName(projectName);
		if (result == null)
			result = new Artifact();
		else
			logger.info("Updating {} metadata", projectName);
		URL url = new URL(ossmeterUrl2 + "projects/p/" + projectName);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream is = connection.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
		String jsonText = readAll(bufferReader);
		JSONObject obj = (JSONObject) JSONValue.parse(jsonText);
		setArtifactFromJson(result, obj);
		
		bufferReader.close();
		if(result.getHomePage().startsWith("https://github.com/")) {
			try {
				Artifact art = importer.importProject(result.getHomePage().replace("https://github.com/",""), access_token);
				setArtifactFromJson(art, obj);
				projectRepository.delete(art.getId());
				art.setId(result.getId());
				result = art;
			}catch(Exception e) {
				
			}
		}
		else {
			result.setStarred(getStargazers(projectName));
			storeGithubUser(result.getStarred(), result.getFullName());
		}
		try {
			List<String> deps = getDependencies(projectName);
			if(deps.size() > 0)	result.setDependencies(deps);
		} catch (Exception e) {
			logger.error("Import dependencies failed: {}", e.getMessage());
		}
		is.close();
		projectRepository.save(result);
		return result;
	}

	private List<Stargazers> getStargazers(String artId) {
		List<Stargazers> result = new ArrayList<>();
		try {

			URL url = new URL(ossmeterUrl2 + "raw/projects/p/" + artId + "/m/stars");
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

	private List<String> getDependencies(String artId) {
		List<String> result = new ArrayList<>();
		try {
			URL url = new URL(
					ossmeterUrl2 + "raw/projects/p/" + artId + "/m/trans.rascal.dependency.maven.allMavenDependencies");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
			String jsonText = readAll(bufferReader);
			JSONArray array = (JSONArray) JSONValue.parse(jsonText);
			for (Object object : array) {
				String dependency = (String) ((JSONObject) object).get("value");
				dependency = dependency.replace("bundle://maven/", "");
				String[] splitted = dependency.split("/");
				if (splitted.length == 3)
					result.add(splitted[0] + ":" + splitted[1]);
				else
					result.add(dependency);
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
		importAll(ossmeterUrl);
	}

	public void importAll(String mppUrl) {
		boolean guard = true;
		int page = 0;
		while (guard) {
			try {
				if (!mppUrl.startsWith("http://"))
					ossmeterUrl2 = "http://" + mppUrl + "/";
				else
					ossmeterUrl2 = mppUrl + "/";
				URL url = new URL(ossmeterUrl2 + "projects/?page=" + page + "&size=" + pageSize);
				URLConnection connection = url.openConnection();
				connection.connect();
				InputStream is = connection.getInputStream();
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
				String jsonText = readAll(bufferReader);
				JSONArray projects = (JSONArray) JSONValue.parse(jsonText);
				if (projects.isEmpty())
					guard = false;
				else

					for (Object object : projects) {
						String name = (String) ((JSONObject) object).get("shortName");
						try {
							logger.info("Importing {} project...", name);
							importProject(name, null);
							logger.info("Imported {} project.", name);
						} catch (Exception e) {
							logger.error("Error importing {} project", name);
						}
					}
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
			if (user == null) {
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
			if (user == null) {
				user = new GithubUser();
				user.setLogin(committer.getLogin());
			}
			user.getCommitsToRepo().add(repoName);
			userRepository.save(user);
		}
	}

	public void importVersionsFromMavenMinerCSV(String fileName) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			boolean first = true;
			for (String repo : stream.collect(Collectors.toList())) {
				if (first)
					first = false;
				else {
					MavenLibrary mvn = new MavenLibrary();
					String completeLibrary = repo.replace("\"", "").split(",")[0];
					mvn.setGroupid(completeLibrary.split(":")[0]);
					mvn.setArtifactid(completeLibrary.split(":")[1]);
					mvn.setVersion(completeLibrary.split(":")[2]);

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					try {
						mvn.setReleasedate(
								dateFormat.parse(repo.split(",")[2].replace("\"", "").replace("Z[GMT]", "")));
					} catch (ParseException e) {

					}

					mavenLibraryRepository.save(mvn);
				}
			}
		} catch (IOException e) {

		}
	}
}
