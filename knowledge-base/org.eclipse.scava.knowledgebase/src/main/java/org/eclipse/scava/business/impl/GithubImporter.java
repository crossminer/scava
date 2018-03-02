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
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.repository.DefaultMirrorSelector;
import org.eclipse.aether.util.repository.DefaultProxySelector;
import org.eclipse.scava.business.IImporter;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.GithubUser;
import org.eclipse.scava.business.model.Stargazers;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("Github")
public class GithubImporter implements IImporter {

	@Value("${egit.github.token}")
	private String token;
	private static final String UTF8 = "UTF-8";
	@Autowired
	private GithubUserRepository userRepository;

	

	@Autowired
	private ArtifactRepository projectRepository;

	private static final Logger logger = Logger.getLogger(GithubImporter.class);

	@Override
	public Artifact importProject(String artId) throws IOException  {
		Artifact checkRepo = projectRepository.findOneByFullName(artId);
		if (checkRepo != null){
			logger.debug("\t" + artId + " already in DB");
			return checkRepo;
			
		}
		logger.debug("Importing project: " + artId);
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(token);
		RepositoryService repoService = new RepositoryService(client);
		
		Repository rep = repoService.getRepository(artId.split("/")[0],artId.split("/")[1]);
		
		Artifact p = new Artifact();
		p.setClone_url(rep.getCloneUrl());
		p.setDescription(rep.getDescription());
		p.setFullName(rep.getOwner().getLogin() + "/" + rep.getName());
		p.setHtml_url(rep.getHtmlUrl());
		p.setGit_url(rep.getGitUrl());
		p.setMaster_branch(rep.getDefaultBranch());
		p.setHomePage(rep.getHomepage());
		p.setName(rep.getName());
		p.setShortName(p.getShortName());
			try {
				p.setStarred(getStargazers(rep));
				
			} catch (MalformedURLException e) {
				logger.error(e.getMessage());
			} catch (Exception e) {
				logger.error("Error getting stars" + e.getMessage());
			}
			try {
				p.setCommitteers(getCommitters(rep));
			} catch (MalformedURLException e) {
				logger.error(e.getMessage());
			} catch (Exception e) {
				logger.error("Error getting committers" + e.getMessage());
			}
			try {
				p.setReadmeText(getReadmeContent(client, rep));
			} catch (Exception e) {
				logger.error("Error getting readmefile" + e.getMessage());
			}
			try {
				p.setDependencies(getDependencies(client, rep));
			} catch (IOException | XmlPullParserException | InterruptedException e) {
				logger.error("Error getting dependencies: "  + e.getMessage());
			}
			
			storeGithubUserCommitter(p.getCommitteers(), p.getFullName());
			storeGithubUser(p.getStarred(), p.getFullName());
			projectRepository.save(p);
		logger.debug("Imported project: " + artId);
		return p;
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

	private List<Stargazers> getStargazers(Repository rep) throws IOException {
		List<Stargazers> results = new ArrayList<>();
		int page = 1;
		boolean continueValue=true;
		while(continueValue){
			if (getRemainingResource("core") == 0)
				waitApiCoreRate();
			URL url;
			url = new URL("https://api.github.com/repos/" +
							rep.getOwner().getLogin() + "/" +
							rep.getName() + "/stargazers?page=" + page + "&per_page=100&access_token=" + token);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty("Accept", "application/vnd.github.v3.star+json");
			connection.connect();
			InputStream is = connection.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
			String jsonText = readAll(bufferReader);
			JSONArray obj = (JSONArray) JSONValue.parse(jsonText);
			if(obj.size() == 0)
				continueValue = false;
			for (Object object : obj) {
				JSONObject star = ((JSONObject)object);
				JSONObject userStarred = (JSONObject) star.get("user");
				Stargazers s = new Stargazers(userStarred.get("login").toString(), 
						star.get("starred_at").toString());
				
				results.add(s);
			}
			page++;
		}
		return results;
	}
	public List<GithubUser> getCommitters(Repository rep) throws IOException {
		List<GithubUser> results = new ArrayList<>();
		if (getRemainingResource("core") == 0)
			waitApiCoreRate();
		URL url;
		url = new URL("https://api.github.com/repos/" +
						rep.getOwner().getLogin() + "/" +
						rep.getName() + "/stats/contributors?access_token=" + token);
		boolean guard = true;
		while(guard){
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.connect();
			InputStream is = connection.getInputStream();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
			String jsonText = readAll(bufferReader);
			Object o = JSONValue.parse(jsonText);
			if(o instanceof JSONArray){
				JSONArray obj = (JSONArray) o;
				for (Object object : obj) {
					JSONObject star = ((JSONObject)object);
					JSONObject userStarred = (JSONObject) star.get("author");
					GithubUser s = new GithubUser();
					s.setLogin(userStarred.get("login").toString());
					results.add(s);
				}
				guard = false;
			}
		}
		return results;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	@Override
	public void importAll() {
		String jsonText = null;


		int startStar = 20;
		int stopStar = 120;
		
		while(stopStar < 5000){
			int page = 1;
			while (page < 11) {
				InputStream is = null;
				BufferedReader bufferReader = null;
	
				//TODO Replace with function that returns string
				try {
					if (getRemainingResource("search") == 0)
						waitApiSearchRate();
					URL url =new URL("https://api.github.com/search/repositories?q=json%20library+stars:" + startStar + ".." + stopStar + "+language:java&page=" + page
							+ "&access_token=" + token);
					is = url .openStream();
					bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
					jsonText = readAll(bufferReader);
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally{
					if (is != null)
						try {
							is.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
				}
				
				
				JSONObject obj = (JSONObject) JSONValue.parse(jsonText);
				JSONArray arr = (JSONArray) obj.get("items");
	
				// it checks that at least one repository in obj Array
				if (!arr.isEmpty()) {
					logger.debug(
							"Scanning page: https://api.github.com/search/repositories?q=json%20library+stars:" + startStar + ".." + stopStar + "+language:java&page=" + page
							+ "&access_token=" + token);
					Iterator<?> iter = arr.iterator();
					while (iter.hasNext()) {
						JSONObject entry = (JSONObject) iter.next();
						String fullname = entry.get("full_name").toString();
						try {
							RepositoryId repo = new RepositoryId(fullname.split("/")[0], fullname.split("/")[1]);
							importProject(fullname);// (repo,
																			// pomFiles,
																			// pomPath,
																			// users);
							Files.write(Paths.get("repo2.txt"), (repo.getOwner() + "/" + repo.getName() + "\n").getBytes(), StandardOpenOption.APPEND);

						} catch (Exception e) {
							waitApiCoreRate();
						}
					}
					
				} else {
					logger.debug("Importing completed.");
					break;
				}
				page++;
			}
			startStar+=101;
			stopStar+=101;
		}
	}

	private JSONObject getRemainingResource() throws IOException{
		URL url = new URL("https://api.github.com/rate_limit");
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", "token "  + token);
		connection.connect();
		InputStream is = connection.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF8)));
		String jsonText = readAll(bufferReader);
		JSONObject obj = (JSONObject) JSONValue.parse(jsonText);
		return (JSONObject)obj.get("resources");
	}
	
	private long getRemainingResource(String value) {
		try {
			JSONObject core = (JSONObject) getRemainingResource().get(value);
			return Long.parseLong(core.get("remaining").toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
			
		return 0;
	}
	
	private void waitApiCoreRate() {
		boolean sleep = true;
		while (sleep) {
			
			long remaining = getRemainingResource("core");
			logger.debug(remaining);
			if (remaining > 0)
				sleep = false;
			else try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error(e.getMessage());
			}
		}
	}
	
	private void waitApiSearchRate() {
		boolean sleep = true;
		while (sleep) {
			
			long remaining = getRemainingResource("search");
			logger.debug(remaining);
			if (remaining > 0)
				sleep = false;
			else try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				logger.error(e.getMessage());
			}
		}
	}

	/*
	 * It Returns the content string of Readme files
	 */
	private String getReadmeContent(GitHubClient client, Repository rep) throws Exception {
		ContentsService contentService = new ContentsService(client);
		RepositoryContents content = contentService.getReadme(rep);
		//TODO handle exception
		String fileConent = content.getContent();
		return new String(Base64.decodeBase64(fileConent.getBytes()));
	}

	/*
	 * It uses /pom.xml path
	 */
	private List<String> getDependencies(GitHubClient client, Repository rep)
			throws IOException, XmlPullParserException, InterruptedException {
		List<String> mavenDeps = getMavenDependencies(client, rep);
		List<String> gradleDeps = getGradleDependencies(client, rep);
		mavenDeps.addAll(gradleDeps);
		return mavenDeps; 
	}
	private List<String> getGradleDependencies(GitHubClient client, Repository rep) 
			throws IOException, XmlPullParserException, InterruptedException {
		String repoFullName = rep.getOwner().getLogin() + "/" + rep.getName();
		List<String> pomPath = new ArrayList<>();
		InputStream searchPomFiles = null; 
		try{
			searchPomFiles = new URL("https://api.github.com/search/code?q=filename:build.gradle+repo:" + repoFullName + "&access_token="
					+ this.token).openStream();
		}catch(Exception e){
			
			logger.debug("sleep time");
			Thread.sleep(60000);
			logger.debug("wakeup");
			searchPomFiles = new URL("https://api.github.com/search/code?q=filename:build.gradle+repo:" + repoFullName + "&access_token="
					+ this.token).openStream();
		}
		BufferedReader searchPomresults = new BufferedReader(new InputStreamReader(searchPomFiles, Charset.forName(UTF8)));
		String jsonSearchPom = readAll(searchPomresults);
		JSONObject pomContentsResults = (JSONObject) JSONValue.parse(jsonSearchPom);
		long i = (long) pomContentsResults.get("total_count");
		if(i > 0){
			JSONArray pomFiles = (JSONArray) pomContentsResults.get("items");
			for (Object object : pomFiles) {
				JSONObject pomFile = (JSONObject) object;
				pomPath.add((String) pomFile.get("path"));
			}
		}
		return getGradleDependencies(client, rep, pomPath);
	}

	private List<String> getGradleDependencies(GitHubClient client, Repository rep, List<String> gradlePath)  {
		List<String> result = new ArrayList<>();
		ContentsService contentService = new ContentsService(client);
		for (String gradleFile : gradlePath) {
			List<RepositoryContents> test;
			try {
				test = contentService.getContents(rep, gradleFile);
				String valueDecoded = "";
				for (RepositoryContents content : test) {
					String fileConent = content.getContent();
					valueDecoded = new String(Base64.decodeBase64(fileConent.getBytes()));
					valueDecoded += System.getProperty("line.separator");
				}
				AstBuilder builder = new AstBuilder();
			    List<ASTNode> nodes = builder.buildFromString( valueDecoded );
			    DependencyVisitor visitor = new DependencyVisitor();
		        walkScript(visitor, nodes );
		        
		        List<String> partialResult = visitor.getDependencies();
		        if(partialResult!=null)
		        	result.addAll(partialResult);
			} catch (Exception e) {logger.error(e.getMessage());}
			
		}
		return result;
	}

	public void walkScript( GroovyCodeVisitor visitor, List<ASTNode> nodes )
    {
        for( ASTNode node : nodes )
        {
            node.visit( visitor );
        }
    }
	
	private List<String> getMavenDependencies(GitHubClient client, Repository rep)
			throws IOException, XmlPullParserException, InterruptedException {
		String repoFullName = rep.getOwner().getLogin() + "/" + rep.getName();
		List<String> pomPath = new ArrayList<>();
		InputStream searchPomFiles = null; 
		try{
			searchPomFiles = new URL("https://api.github.com/search/code?q=filename:pom.xml+repo:" + repoFullName + "&access_token="
					+ this.token).openStream();
		}catch(Exception e){
			
			logger.debug("sleep time");
			Thread.sleep(60000);
			logger.debug("wakeup");
			searchPomFiles = new URL("https://api.github.com/search/code?q=filename:pom.xml+repo:" + repoFullName + "&access_token="
					+ this.token).openStream();
		}
		BufferedReader searchPomresults = new BufferedReader(new InputStreamReader(searchPomFiles, Charset.forName(UTF8)));
		String jsonSearchPom = readAll(searchPomresults);
		JSONObject pomContentsResults = (JSONObject) JSONValue.parse(jsonSearchPom);
		long i = (long) pomContentsResults.get("total_count");
		if(i > 0){
			JSONArray pomFiles = (JSONArray) pomContentsResults.get("items");
			for (Object object : pomFiles) {
				JSONObject pomFile = (JSONObject) object;
				pomPath.add((String) pomFile.get("path"));
			}
		}
		return getMavenDependencies(client, rep, pomPath);
	}
	/*
	 * getDependencies takes several path to pom files
	 */
	private List<String> getMavenDependencies(GitHubClient client, Repository rep, List<String> pomFiles) {
		List<String> result = new ArrayList<>();
		ContentsService contentService = new ContentsService(client);
		for (String pomFile : pomFiles) {
			try {

				List<RepositoryContents> test = contentService.getContents(rep, pomFile);
				String valueDecoded = "";
				for (RepositoryContents content : test) {
					String fileConent = content.getContent();
					valueDecoded = new String(Base64.decodeBase64(fileConent.getBytes()));
					valueDecoded += System.getProperty("line.separator");
				}

				MavenXpp3Reader reader = new MavenXpp3Reader();
				Model model = reader.read(new StringReader(valueDecoded));

				for (Dependency repositoryContents : model.getDependencies()) {
					result.add(repositoryContents.getGroupId() + ":" + repositoryContents.getArtifactId());

				}
				if (model.getDependencyManagement() != null)
					for (Dependency repositoryContents : model.getDependencyManagement().getDependencies()) {
						result.add(repositoryContents.getGroupId() + ":" + repositoryContents.getArtifactId());
					}
				if (model.getParent() != null) {
					String parent = model.getParent().getGroupId() + ":" + model.getParent().getArtifactId() + ":"
							+ model.getParent().getVersion();
					try {
						List<String> deps;
						deps = getMavenParentDependencies(parent);
						result.addAll(deps);
					} catch (DependencyResolutionException | ArtifactDescriptorException e) {
						logger.error(e.getMessage());
					}
				}
			} catch (XmlPullParserException | IOException e) {
				logger.error(e.getMessage());
			} 
		}
		return result;
	}

	private List<String> getMavenParentDependencies(String parent)
			throws DependencyResolutionException, ArtifactDescriptorException {
		List<String> dependencies = new ArrayList<>();
		DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
		RepositorySystem system = newRepositorySystem(locator);
		RepositorySystemSession session = newSession(system);

		RemoteRepository central = new RemoteRepository.Builder("central", "default", "http://repo1.maven.org/maven2/")
				.build();

		org.eclipse.aether.artifact.Artifact artifact = new DefaultArtifact(parent);
		ArtifactDescriptorRequest request = new ArtifactDescriptorRequest(artifact, Arrays.asList(central), null);
		try {
			ArtifactDescriptorResult result = system.readArtifactDescriptor(session, request);
			for (org.eclipse.aether.graph.Dependency dependency : result.getManagedDependencies()) {
				dependencies.add(dependency.getArtifact().getGroupId() + ":" + dependency.getArtifact().getGroupId());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dependencies;

	}

	/*
	 * newSession: relates to pom files and depedecieOs
	 */
	private static RepositorySystemSession newSession(RepositorySystem system) {
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
		LocalRepository localRepo = new LocalRepository("target/local-repo");
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));
		// set possible proxies and mirrors
		session.setProxySelector(new DefaultProxySelector().add(new Proxy(Proxy.TYPE_HTTP, "host", 3625),
				Arrays.asList("localhost", "127.0.0.1")));
		session.setMirrorSelector(
				new DefaultMirrorSelector().add("my-mirror", "http://mirror", "default", false, "external:*", null));
		return session;
	}

	/*
	 * newSession: relates to pom files and depedecies
	 */
	private static RepositorySystem newRepositorySystem(DefaultServiceLocator locator) {
		locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
		locator.addService(TransporterFactory.class, FileTransporterFactory.class);
		locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
		return locator.getService(RepositorySystem.class);
	}

}
