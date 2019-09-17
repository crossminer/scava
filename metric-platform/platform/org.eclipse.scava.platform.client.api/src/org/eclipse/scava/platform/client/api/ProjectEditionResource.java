/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.ProjectRepository;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla;
import org.eclipse.scava.repository.model.cc.forum.Forum;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.eclipse.EclipseProject;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;
import org.eclipse.scava.repository.model.github.GitHubRepository;
import org.eclipse.scava.repository.model.gitlab.GitLabRepository;
import org.eclipse.scava.repository.model.gitlab.GitLabTracker;
import org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ProjectEditionResource extends ServerResource {

	@Put
	public Representation editProject(Representation entity) {
		try {
			Platform platform = Platform.getInstance();
			ObjectMapper mapper = new ObjectMapper();

			String j = entity.getText();

			JsonNode json = mapper.readTree(j);
			String sortName = json.get("shortName").asText();
			
			// Retrieve the older project
			ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
			Project project = projectRepo.getProjects().findOneByShortName(sortName);
			if (project instanceof GitHubRepository) {
				this.importGithubProject(json, project);
			} else if (project instanceof GitLabRepository) {
				this.importGitlabProject(json, project);
			} else if (project instanceof EclipseProject) {
				this.importEclipseProject(json, project);
			}  else {
				this.importProject(json, project);
			}

			platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();

			getResponse().setStatus(Status.SUCCESS_CREATED);
			return new StringRepresentation(project.getDbObject().toString());
			
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation(
					"{\"status\":\"error\", \"message\" : \"" + e.getMessage() + "\"}");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
	}

	/*
	 * import generic project
	 */
	private void importProject(JsonNode json, Project project) {
		if (isValidKey(json, "name"))
			project.setName(json.get("name").asText());
		if (isValidKey(json, "description"))
			project.setDescription(json.get("description").asText());
		if (isValidKey(json, "homePage"))
			project.setHomePage(json.get("homePage").asText());
		
//		int i = 0;
//		for (JsonNode vcs : (ArrayNode) json.get("vcsRepositories")) {
//			VcsRepository repo = project.getVcsRepositories().get(i);			
//			if (isValidKey(vcs, "url"))
//				repo.setUrl(vcs.get("url").asText());
//			i++;
//		}
		
		// Delete vcs elements
		List<VcsRepository> tempVcsRepo = new ArrayList<VcsRepository>();
		for (VcsRepository repo : project.getVcsRepositories()) {
			boolean exist = false;
			for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempVcsRepo.add(repo);
			}
		}
		project.getVcsRepositories().removeAll(tempVcsRepo);
		
		// Add & update vcs elements
		for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
			boolean exist = false;
			for (VcsRepository repo : project.getVcsRepositories()) {
				if (repo.getId().equals(vcsJson.get("_id").asText()) ) {
					repo.setUrl(vcsJson.get("url").asText());
					exist = true;
					break;
				}
			}
			if(!exist) {
				VcsRepository repo = null;
				switch (vcsJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.vcs.git.GitRepository":
					repo = new GitRepository();
					repo.setUrl(vcsJson.get("url").asText());
					break;
				case "org.eclipse.scava.repository.model.documentation.gitbased.GitRepository":
					repo = new DocumentationGitBased();
					repo.setUrl(vcsJson.get("url").asText());
					break;
				case "org.eclipse.scava.repository.model.vcs.svn.SvnRepository":
					repo = new SvnRepository();
					repo.setUrl(vcsJson.get("url").asText());
					break;
				default:
					break;
				}
				project.getVcsRepositories().add(repo);
			}
		}	
		
//		int j = 0;
//		for (JsonNode bts : (ArrayNode) json.get("bugTrackingSystems")) {
//			BugTrackingSystem buggy = project.getBugTrackingSystems().get(j);
//			if (isValidKey(bts, "url"))
//				buggy.setUrl(bts.get("url").asText());
//			if (isValidKey(bts, "login"))
//				((JiraBugTrackingSystem) buggy).setLogin(bts.get("login").asText());
//			if (isValidKey(bts, "password"))
//				((JiraBugTrackingSystem) buggy).setPassword(bts.get("password").asText());
//			if (isValidKey(bts, "project"))
//				((JiraBugTrackingSystem) buggy).setProject(bts.get("project").asText());
//			j++;		
//		}
		
		// Delete bts elements
		List<BugTrackingSystem> tempBtsRepo = new ArrayList<BugTrackingSystem>();
		for (BugTrackingSystem buggy : project.getBugTrackingSystems()) {
			boolean exist = false;
			for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempBtsRepo.add(buggy);
			}
		}
		project.getBugTrackingSystems().removeAll(tempBtsRepo);
		
		// Add & update bts elements
		for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
			boolean exist = false;
			for (BugTrackingSystem buggy : project.getBugTrackingSystems()) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				if (btsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.jira.BugTrackingSystem")) {
					buggy = new JiraBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
				}
				project.getBugTrackingSystems().add(buggy);
			}
		}
		
//		int k = 0;
//		for (JsonNode cc : (ArrayNode) json.get("communicationChannels")) {
//			CommunicationChannel comunication = project.getCommunicationChannels().get(k);
//			if (isValidKey(cc, "url"))
//				comunication.setUrl(cc.get("url").asText());
//			if (isValidKey(cc, "MailingListName"))
//				((SympaMailingList) comunication).setMailingListName(cc.get("MailingListName").asText());
//			if (isValidKey(cc, "MailingListDescription"))
//				((SympaMailingList) comunication).setMailingListDescription(cc.get("MailingListDescription").asText());
//			if (isValidKey(cc, "compressedFileExtension"))
//				((SympaMailingList) comunication).setCompressedFileExtension(cc.get("compressedFileExtension").asText());
//			if (isValidKey(cc, "username"))
//				((SympaMailingList) comunication).setUsername(cc.get("username").asText());
//			if (isValidKey(cc, "password"))
//				((SympaMailingList) comunication).setPassword(cc.get("password").asText());
//			k++;
//		}
		
		// Delete communication channel elements
		List<CommunicationChannel> tempCcRepo = new ArrayList<CommunicationChannel>();
		for (CommunicationChannel communication : project.getCommunicationChannels()) {
			boolean exist = false;
			for (JsonNode ccJson : (ArrayNode) json.get("communicationChannels")) {
				if (communication.getId().equals(ccJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempCcRepo.add(communication);
			}
		}
		project.getCommunicationChannels().removeAll(tempCcRepo);
		
		// Add & update communication channel elements
		for (JsonNode ccJson : (ArrayNode) json.get("communicationChannels")) {
			boolean exist = false;
			for (CommunicationChannel communication : project.getCommunicationChannels()) {
				if (communication.getId().equals(ccJson.get("_id").asText())) {
					communication.setUrl(ccJson.get("url").asText());
			   		((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
			   		((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
			   		((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
			   		((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
			   		((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				CommunicationChannel communication = null;
				if (ccJson.get("_type").asText().equals("org.eclipse.scava.repository.model.cc.sympa.CommunicationChannel")) {
					communication = new SympaMailingList();
					communication.setUrl(ccJson.get("url").asText());
			   		((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
			   		((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
			   		((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
			   		((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
			   		((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
				}
				project.getCommunicationChannels().add(communication);
			}
		}
		
	}
	
	/*
	 * import GitHub project
	 */
	private void importGithubProject(JsonNode json, Project project) {
		GitHubRepository githubRepository = (GitHubRepository) project;
		
		if (isValidKey(json, "full_name"))
			githubRepository.setFull_name(json.get("full_name").asText());
		if (isValidKey(json, "name"))
			githubRepository.setName(json.get("name").asText());
		if (isValidKey(json, "description"))
			githubRepository.setDescription(json.get("description").asText());
		if (isValidKey(json, "homePage"))
			githubRepository.setHomePage(json.get("homePage").asText());
		if (isValidKey(json, "token"))
			githubRepository.setToken(json.get("token").asText());
		if (isValidKey(json, "ssh_url"))
			githubRepository.setSsh_url(json.get("ssh_url").asText());
		if (isValidKey(json, "git_url"))
			githubRepository.setGit_url(json.get("git_url").asText());
		if (isValidKey(json, "svn_url"))
			githubRepository.setSvn_url(json.get("svn_url").asText());
		if (isValidKey(json, "clone_url"))
			githubRepository.setClone_url(json.get("clone_url").asText());
		
//		int i = 0;
//		for (JsonNode vcs : (ArrayNode) json.get("vcsRepositories")) {
//			VcsRepository repo = githubRepository.getVcsRepositories().get(i);			
//			if (isValidKey(vcs, "url"))
//				repo.setUrl(vcs.get("url").asText());
//			i++;
//		}
		
		// Delete vcs elements
		List<VcsRepository> tempVcsRepo = new ArrayList<VcsRepository>();
		for (VcsRepository repo : githubRepository.getVcsRepositories()) {
			boolean exist = false;
			for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempVcsRepo.add(repo);
			}
		}
		githubRepository.getVcsRepositories().removeAll(tempVcsRepo);
		
		// Add & update vcs elements
		for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
			boolean exist = false;
			for (VcsRepository repo : githubRepository.getVcsRepositories()) {
				if (repo.getId().equals(vcsJson.get("_id").asText()) ) {
					repo.setUrl(vcsJson.get("url").asText());
					exist = true;
					break;
				}
			}
			if(!exist) {
				VcsRepository repo = null;
				if (vcsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.vcs.git.GitRepository")) {
					repo = new GitRepository();
					repo.setUrl(vcsJson.get("url").asText());
				}
				githubRepository.getVcsRepositories().add(repo);
			}
		}
		
//		int j = 0;
//		for (JsonNode bts : (ArrayNode) json.get("bugTrackingSystems")) {
//			BugTrackingSystem buggy = githubRepository.getBugTrackingSystems().get(j);
//			if (isValidKey(bts, "url")) {
//				buggy.setUrl(bts.get("url").asText());
//			}
//			if (isValidKey(bts, "token")) {
//				String token = bts.get("token").asText();
//				String owner = githubRepository.getFull_name().split("/")[0];
//				String repo = githubRepository.getFull_name().split("/")[1];
//				((GitHubBugTracker) buggy).setProject(token, owner, repo);
//			}
//			if (isValidKey(bts, "login"))
//				((JiraBugTrackingSystem) buggy).setLogin(bts.get("login").asText());
//			if (isValidKey(bts, "password"))
//				((JiraBugTrackingSystem) buggy).setPassword(bts.get("password").asText());
//			if (isValidKey(bts, "project"))
//				((JiraBugTrackingSystem) buggy).setProject(bts.get("project").asText());
//			j++;
//		}
		
		// Delete bts elements
		List<BugTrackingSystem> tempBtsRepo = new ArrayList<BugTrackingSystem>();
		for (BugTrackingSystem buggy : githubRepository.getBugTrackingSystems()) {
			boolean exist = false;
			for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempBtsRepo.add(buggy);
			}
		}
		githubRepository.getBugTrackingSystems().removeAll(tempBtsRepo);

		// Add & update bts elements
		for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
			boolean exist = false;
			for (BugTrackingSystem buggy : githubRepository.getBugTrackingSystems()) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					buggy.setUrl(btsJson.get("url").asText());
					String token = btsJson.get("token").asText();
					String owner = githubRepository.getFull_name().split("/")[0];
					String repo = githubRepository.getFull_name().split("/")[1];
					((GitHubBugTracker) buggy).setProject(token, owner, repo);
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				if (btsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.github.GitHubBugTracker")) {
					buggy = new GitHubBugTracker();
					buggy.setUrl(btsJson.get("url").asText());
					String token = btsJson.get("token").asText();
					String owner = githubRepository.getFull_name().split("/")[0];
					String repo = githubRepository.getFull_name().split("/")[1];
					((GitHubBugTracker) buggy).setProject(token, owner, repo);
				}
				githubRepository.getBugTrackingSystems().add(buggy);
			}
		}

//		int k = 0;
//		for (JsonNode cc : (ArrayNode) json.get("communicationChannels")) {
//			CommunicationChannel comunication = githubRepository.getCommunicationChannels().get(k);
//			
//			if (isValidKey(cc, "url"))
//				comunication.setUrl(cc.get("url").asText());
//			k++;
//		}
	}
	
	/*
	 * import GitLab project
	 */
	private void importGitlabProject(JsonNode json, Project project) {
		GitLabRepository gitlabRepository = (GitLabRepository) project;
		
		if (isValidKey(json, "full_name"))
			gitlabRepository.setFull_name(json.get("full_name").asText());
		if (isValidKey(json, "name"))
			gitlabRepository.setName(json.get("name").asText());
		if (isValidKey(json, "description"))
			gitlabRepository.setDescription(json.get("description").asText());
		if (isValidKey(json, "homePage"))
			gitlabRepository.setHomePage(json.get("homePage").asText());
		if (isValidKey(json, "git_url"))
			gitlabRepository.setGit_url(json.get("git_url").asText());
		if (isValidKey(json, "clone_url"))
			gitlabRepository.setClone_url(json.get("clone_url").asText());
		
//		int i = 0;
//		for (JsonNode vcs : (ArrayNode) json.get("vcsRepositories")) {
//			VcsRepository repo = gitlabRepository.getVcsRepositories().get(i);
//			if (isValidKey(vcs, "url"))
//				repo.setUrl(vcs.get("url").asText());
//			i++;
//		}
		
		// Delete vcs elements
		List<VcsRepository> tempVcsRepo = new ArrayList<VcsRepository>();
		for (VcsRepository repo : gitlabRepository.getVcsRepositories()) {
			boolean exist = false;
			for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempVcsRepo.add(repo);
			}
		}
		gitlabRepository.getVcsRepositories().removeAll(tempVcsRepo);

		// Add & update vcs elements
		for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
			boolean exist = false;
			for (VcsRepository repo : gitlabRepository.getVcsRepositories()) {
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					repo.setUrl(vcsJson.get("url").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				VcsRepository repo = null;
				if (vcsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.vcs.git.GitRepository")) {
					repo = new GitRepository();
					repo.setUrl(vcsJson.get("url").asText());
				}
				gitlabRepository.getVcsRepositories().add(repo);
			}
		}
		
//		int j = 0;
//		for (JsonNode bts : (ArrayNode) json.get("bugTrackingSystems")) {
//			BugTrackingSystem buggy = gitlabRepository.getBugTrackingSystems().get(j);
//			if (isValidKey(bts, "url"))
//				buggy.setUrl(bts.get("url").asText());
//			if (isValidKey(bts, "personal_access_token"))
//				((GitLabTracker)buggy).setPersonal_access_token(bts.get("personal_access_token").asText());
//			j++;			
//		}
		
		// Delete bts elements
		List<BugTrackingSystem> tempBtsRepo = new ArrayList<BugTrackingSystem>();
		for (BugTrackingSystem buggy : gitlabRepository.getBugTrackingSystems()) {
			boolean exist = false;
			for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempBtsRepo.add(buggy);
			}
		}
		gitlabRepository.getBugTrackingSystems().removeAll(tempBtsRepo);
		
		// Add & update bts elements
		for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
			boolean exist = false;
			for (BugTrackingSystem buggy : gitlabRepository.getBugTrackingSystems()) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					buggy.setUrl(btsJson.get("url").asText());
					((GitLabTracker)buggy).setPersonal_access_token(btsJson.get("personal_access_token").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				if (btsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.gitlab.GitLabTracker")) {
					buggy = new GitLabTracker();
					buggy.setUrl(btsJson.get("url").asText());
					((GitLabTracker)buggy).setPersonal_access_token(btsJson.get("personal_access_token").asText());
				}
				gitlabRepository.getBugTrackingSystems().add(buggy);
			}
		}
		
//		int k = 0;
//		for (JsonNode cc : (ArrayNode) json.get("communicationChannels")) {
//			CommunicationChannel comunication = gitlabRepository.getCommunicationChannels().get(k);
//			if (isValidKey(cc, "url"))
//				comunication.setUrl(cc.get("url").asText());
//			k++;
//		}	
	}
	
	/*
	 * import Eclipse project
	 */
	private void importEclipseProject(JsonNode json, Project project) {
		EclipseProject eclipseProject = (EclipseProject) project;
		
		if (isValidKey(json, "name"))
			eclipseProject.setName(json.get("name").asText());
		if (isValidKey(json, "description"))
			eclipseProject.setDescription(json.get("description").asText());
		if (isValidKey(json, "homePage"))
			eclipseProject.setHomePage(json.get("homePage").asText());
		if (isValidKey(json, "state"))
			eclipseProject.setState(json.get("state").asText());
		if (isValidKey(json, "downloadsUrl"))
			eclipseProject.setDownloadsUrl(json.get("downloadsUrl").asText());
		if (isValidKey(json, "projectplanUrl"))
			eclipseProject.setProjectplanUrl(json.get("projectplanUrl").asText());
		
//		int i = 0;
//		for (JsonNode vcs : (ArrayNode) json.get("vcsRepositories")) {
//			VcsRepository repo = eclipseProject.getVcsRepositories().get(i);
//			if (isValidKey(vcs, "url"))
//				repo.setUrl(vcs.get("url").asText());
//			if (isValidKey(vcs, "name"))
//				repo.setUrl(vcs.get("name").asText());
//			i++;
//		}

		// Delete vcs elements
		List<VcsRepository> tempVcsRepo = new ArrayList<VcsRepository>();
		for (VcsRepository repo : eclipseProject.getVcsRepositories()) {
			boolean exist = false;
			for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempVcsRepo.add(repo);
			}
		}
		eclipseProject.getVcsRepositories().removeAll(tempVcsRepo);

		// Add & update vcs elements
		for (JsonNode vcsJson : (ArrayNode) json.get("vcsRepositories")) {
			boolean exist = false;
			for (VcsRepository repo : eclipseProject.getVcsRepositories()) {
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					repo.setUrl(vcsJson.get("url").asText());
					repo.setName(vcsJson.get("name").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				VcsRepository repo = null;
				if (vcsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.vcs.git.GitRepository")) {
					repo = new GitRepository();
					repo.setUrl(vcsJson.get("url").asText());
					repo.setName(vcsJson.get("name").asText());
				}
				eclipseProject.getVcsRepositories().add(repo);
			}
		}
		
//		int j = 0;
//		for (JsonNode bts : (ArrayNode) json.get("bugTrackingSystems")) {
//			BugTrackingSystem buggy = eclipseProject.getBugTrackingSystems().get(j);
//			if (isValidKey(bts, "url"))
//				buggy.setUrl(bts.get("url").asText());
//			if (isValidKey(bts, "product"))
//				((Bugzilla)buggy).setProduct(bts.get("product").asText());
//			if (isValidKey(bts, "component"))
//				((Bugzilla)buggy).setComponent(bts.get("component").asText());
//			if (isValidKey(bts, "cgiQueryProgram"))
//				((Bugzilla)buggy).setCgiQueryProgram(bts.get("cgiQueryProgram").asText());
//			j++;			
//		}
		
		// Delete bts elements
		List<BugTrackingSystem> tempBtsRepo = new ArrayList<BugTrackingSystem>();
		for (BugTrackingSystem buggy : eclipseProject.getBugTrackingSystems()) {
			boolean exist = false;
			for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempBtsRepo.add(buggy);
			}
		}
		eclipseProject.getBugTrackingSystems().removeAll(tempBtsRepo);

		// Add & update bts elements
		for (JsonNode btsJson : (ArrayNode) json.get("bugTrackingSystems")) {
			boolean exist = false;
			for (BugTrackingSystem buggy : eclipseProject.getBugTrackingSystems()) {
				if (buggy.getId().equals(btsJson.get("_id").asText())) {
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla)buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla)buggy).setComponent(btsJson.get("component").asText());
					((Bugzilla)buggy).setCgiQueryProgram(btsJson.get("cgiQueryProgram").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				if (btsJson.get("_type").asText().equals("org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla")) {
					buggy = new Bugzilla();
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla)buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla)buggy).setComponent(btsJson.get("component").asText());
					((Bugzilla)buggy).setCgiQueryProgram(btsJson.get("cgiQueryProgram").asText());
				}
				eclipseProject.getBugTrackingSystems().add(buggy);
			}
		}
		
//		int k = 0;
//		for (JsonNode cc : (ArrayNode) json.get("communicationChannels")) {
//			CommunicationChannel comunication = eclipseProject.getCommunicationChannels().get(k);
//			if (isValidKey(cc, "url"))
//				comunication.setUrl(cc.get("url").asText());
//			if (isValidKey(cc, "name"))
//				((Forum) comunication).setName(cc.get("name").asText());
//			if (isValidKey(cc, "description"))
//				((Forum) comunication).setDescription(cc.get("description").asText());
//			k++;
//		}
		
		// Delete communication channel elements
		List<CommunicationChannel> tempCcRepo = new ArrayList<CommunicationChannel>();
		for (CommunicationChannel communication : eclipseProject.getCommunicationChannels()) {
			boolean exist = false;
			for (JsonNode ccJson : (ArrayNode) json.get("communicationChannels")) {
				if (communication.getId().equals(ccJson.get("_id").asText())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				tempCcRepo.add(communication);
			}
		}
		eclipseProject.getCommunicationChannels().removeAll(tempCcRepo);

		// Add & update communication channel elements
		for (JsonNode ccJson : (ArrayNode) json.get("communicationChannels")) {
			boolean exist = false;
			for (CommunicationChannel communication : eclipseProject.getCommunicationChannels()) {
				if (communication.getId().equals(ccJson.get("_id").asText())) {
					communication.setUrl(ccJson.get("url").asText());
					((Forum) communication).setName(ccJson.get("name").asText());
					((Forum) communication).setDescription(ccJson.get("description").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				CommunicationChannel communication = null;
				if (ccJson.get("_type").asText().equals("org.eclipse.scava.repository.model.cc.forum.Forum")) {
					communication = new Forum();
					communication.setUrl(ccJson.get("url").asText());
					((Forum) communication).setName(ccJson.get("name").asText());
					((Forum) communication).setDescription(ccJson.get("description").asText());
				}
				eclipseProject.getCommunicationChannels().add(communication);
			}
		}
	}
	
	public boolean isValidKey(JsonNode json, String key) {
		if (json.has(key) && json.get(key).asText() != null && !json.get(key).asText().equals(""))
			return true;
		return false;
	}
}
