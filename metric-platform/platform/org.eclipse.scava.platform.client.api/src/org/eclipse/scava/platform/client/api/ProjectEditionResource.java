/*******************************************************************************
 * Copyright (c) 2019 Softeam
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
import org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem;
import org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.documentation.gitbased.DocumentationGitBased;
import org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic;
import org.eclipse.scava.repository.model.eclipse.EclipseProject;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;
import org.eclipse.scava.repository.model.github.GitHubRepository;
import org.eclipse.scava.repository.model.gitlab.GitLabRepository;
import org.eclipse.scava.repository.model.gitlab.GitLabTracker;
import org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem;
import org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem;
import org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker;
import org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem;
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
				this.editGithubProject(json, project);
			} else if (project instanceof GitLabRepository) {
				this.editGitlabProject(json, project);
			} else if (project instanceof EclipseProject) {
				this.editEclipseProject(json, project);
			} else {
				this.editProject(json, project);
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
	 * edit generic project
	 */
	private void editProject(JsonNode json, Project project) {
		if (isValidKey(json, "name"))
			project.setName(json.get("name").asText());
		if (isValidKey(json, "description"))
			project.setDescription(json.get("description").asText());
		if (isValidKey(json, "homePage"))
			project.setHomePage(json.get("homePage").asText());

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
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					repo.setUrl(vcsJson.get("url").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
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
				if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof Bugzilla) {
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof SourceForgeBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof RedmineBugIssueTracker) {
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof JiraBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof MantisBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof BitbucketBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				switch (btsJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla":
					buggy = new Bugzilla();
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					break;
				case "org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem":
					buggy = new SourceForgeBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					break;
				case "org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker":
					buggy = new RedmineBugIssueTracker();
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem":
					buggy = new JiraBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem":
					buggy = new MantisBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					break;
				case "org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem":
					buggy = new BitbucketBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					break;
				default:
					break;
				}
				project.getBugTrackingSystems().add(buggy);
			}
		}

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
				if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof NntpNewsGroup) {
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Irc) {
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof SympaMailingList) {
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Mbox) {
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof EclipseForum) {
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof DocumentationSystematic) {
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
						((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
						((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
						((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
						((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
						((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
					}
					exist = true;
					break;
				}
			}
			if (!exist) {
				CommunicationChannel communication = null;
				switch (ccJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup":
					communication = new NntpNewsGroup();
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.irc.Irc":
					communication = new Irc();
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.sympa.SympaMailingList":
					communication = new SympaMailingList();
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.mbox.Mbox":
					communication = new Mbox();
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum":
					communication = new EclipseForum();
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					break;
				case "org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic":
					communication = new DocumentationSystematic();
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						if (!ccJson.has("username") || !ccJson.has("password")) {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
						} else {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
							((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
							((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
							((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
							((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
							((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
						}
					}
					break;
				default:
					break;
				}
				project.getCommunicationChannels().add(communication);
			}
		}

	}

	/*
	 * edit GitHub project
	 */
	private void editGithubProject(JsonNode json, Project project) {
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
				if (repo.getId().equals(vcsJson.get("_id").asText())) {
					repo.setUrl(vcsJson.get("url").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
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
				githubRepository.getVcsRepositories().add(repo);
			}
		}

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
				if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof GitHubBugTracker) {
					buggy.setUrl(btsJson.get("url").asText());
					String token = btsJson.get("token").asText();
					String owner = githubRepository.getFull_name().split("/")[0];
					String repo = githubRepository.getFull_name().split("/")[1];
					((GitHubBugTracker) buggy).setProject(token, owner, repo);
					exist = true;
					break;
				}
				if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof Bugzilla) {
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof SourceForgeBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof RedmineBugIssueTracker) {
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof JiraBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof MantisBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof BitbucketBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				switch (btsJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.github.GitHubBugTracker":
					buggy = new GitHubBugTracker();
					buggy.setUrl(btsJson.get("url").asText());
					String token = btsJson.get("token").asText();
					String owner = githubRepository.getFull_name().split("/")[0];
					String repo = githubRepository.getFull_name().split("/")[1];
					((GitHubBugTracker) buggy).setProject(token, owner, repo);
					break;
				case "org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla":
					buggy = new Bugzilla();
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					break;
				case "org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem":
					buggy = new SourceForgeBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					break;
				case "org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker":
					buggy = new RedmineBugIssueTracker();
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem":
					buggy = new JiraBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem":
					buggy = new MantisBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					break;
				case "org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem":
					buggy = new BitbucketBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					break;
				default:
					break;
				}
				githubRepository.getBugTrackingSystems().add(buggy);
			}
		}
		
		// Delete communication channel elements
		List<CommunicationChannel> tempCcRepo = new ArrayList<CommunicationChannel>();
		for (CommunicationChannel communication : githubRepository.getCommunicationChannels()) {
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
		githubRepository.getCommunicationChannels().removeAll(tempCcRepo);

		// Add & update communication channel elements
		for (JsonNode ccJson : (ArrayNode) json.get("communicationChannels")) {
			boolean exist = false;
			for (CommunicationChannel communication : githubRepository.getCommunicationChannels()) {
				if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof NntpNewsGroup) {
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Irc) {
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof SympaMailingList) {
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Mbox) {
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof EclipseForum) {
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof DocumentationSystematic) {
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
						((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
						((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
						((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
						((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
						((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
					}
					exist = true;
					break;
				}
			}
			if (!exist) {
				CommunicationChannel communication = null;
				switch (ccJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup":
					communication = new NntpNewsGroup();
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.irc.Irc":
					communication = new Irc();
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.sympa.SympaMailingList":
					communication = new SympaMailingList();
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.mbox.Mbox":
					communication = new Mbox();
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum":
					communication = new EclipseForum();
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					break;
				case "org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic":
					communication = new DocumentationSystematic();
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						if (!ccJson.has("username") || !ccJson.has("password")) {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
						} else {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
							((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
							((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
							((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
							((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
							((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
						}
					}
					break;
				default:
					break;
				}
				githubRepository.getCommunicationChannels().add(communication);
			}
		}
	}

	/*
	 * edit GitLab project
	 */
	private void editGitlabProject(JsonNode json, Project project) {
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
				gitlabRepository.getVcsRepositories().add(repo);
			}
		}

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
				if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof GitLabTracker) {
					buggy.setUrl(btsJson.get("url").asText());
					((GitLabTracker) buggy).setPersonal_access_token(btsJson.get("personal_access_token").asText());
					exist = true;
					break;
				}
				if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof Bugzilla) {
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof SourceForgeBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof RedmineBugIssueTracker) {
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof JiraBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof MantisBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof BitbucketBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				switch (btsJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.gitlab.GitLabTracker":
					buggy = new GitLabTracker();
					buggy.setUrl(btsJson.get("url").asText());
					((GitLabTracker) buggy).setPersonal_access_token(btsJson.get("personal_access_token").asText());
					break;
				case "org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla":
					buggy = new Bugzilla();
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					break;
				case "org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem":
					buggy = new SourceForgeBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					break;
				case "org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker":
					buggy = new RedmineBugIssueTracker();
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem":
					buggy = new JiraBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem":
					buggy = new MantisBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					break;
				case "org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem":
					buggy = new BitbucketBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					break;
				default:
					break;
				}
				gitlabRepository.getBugTrackingSystems().add(buggy);
			}
		}
		
		// Delete communication channel elements
		List<CommunicationChannel> tempCcRepo = new ArrayList<CommunicationChannel>();
		for (CommunicationChannel communication : gitlabRepository.getCommunicationChannels()) {
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
		gitlabRepository.getCommunicationChannels().removeAll(tempCcRepo);

		// Add & update communication channel elements
		for (JsonNode ccJson : (ArrayNode) json.get("communicationChannels")) {
			boolean exist = false;
			for (CommunicationChannel communication : gitlabRepository.getCommunicationChannels()) {
				if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof NntpNewsGroup) {
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Irc) {
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof SympaMailingList) {
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Mbox) {
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof EclipseForum) {
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof DocumentationSystematic) {
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
						((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
						((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
						((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
						((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
						((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
					}
					exist = true;
					break;
				}
			}
			if (!exist) {
				CommunicationChannel communication = null;
				switch (ccJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup":
					communication = new NntpNewsGroup();
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.irc.Irc":
					communication = new Irc();
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.sympa.SympaMailingList":
					communication = new SympaMailingList();
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.mbox.Mbox":
					communication = new Mbox();
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum":
					communication = new EclipseForum();
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					break;
				case "org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic":
					communication = new DocumentationSystematic();
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						if (!ccJson.has("username") || !ccJson.has("password")) {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
						} else {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
							((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
							((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
							((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
							((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
							((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
						}
					}
					break;
				default:
					break;
				}
				gitlabRepository.getCommunicationChannels().add(communication);
			}
		}
		
		
	}

	/*
	 * edit Eclipse project
	 */
	private void editEclipseProject(JsonNode json, Project project) {
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
				eclipseProject.getVcsRepositories().add(repo);
			}
		}

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
				if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof Bugzilla) {
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof SourceForgeBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof RedmineBugIssueTracker) {
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof JiraBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof MantisBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					exist = true;
					break;
				} else if (buggy.getId().equals(btsJson.get("_id").asText()) && buggy instanceof BitbucketBugTrackingSystem) {
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					exist = true;
					break;
				}
			}
			if (!exist) {
				BugTrackingSystem buggy = null;
				switch (btsJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla":
					buggy = new Bugzilla();
					buggy.setUrl(btsJson.get("url").asText());
					((Bugzilla) buggy).setProduct(btsJson.get("product").asText());
					((Bugzilla) buggy).setComponent(btsJson.get("component").asText());
					break;
				case "org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem":
					buggy = new SourceForgeBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					break;
				case "org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker":
					buggy = new RedmineBugIssueTracker();
					buggy.setUrl(btsJson.get("url").asText());
					((RedmineBugIssueTracker) buggy).setName(btsJson.get("name").asText());
					((RedmineBugIssueTracker) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem":
					buggy = new JiraBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((JiraBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((JiraBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					((JiraBugTrackingSystem) buggy).setProject(btsJson.get("project").asText());
					break;
				case "org.eclipse.scava.repository.model.mantis.MantisBugTrackingSystem":
					buggy = new MantisBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((MantisBugTrackingSystem) buggy).setToken(btsJson.get("token").asText());
					break;
				case "org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem":
					buggy = new BitbucketBugTrackingSystem();
					buggy.setUrl(btsJson.get("url").asText());
					((BitbucketBugTrackingSystem) buggy).setLogin(btsJson.get("login").asText());
					((BitbucketBugTrackingSystem) buggy).setPassword(btsJson.get("password").asText());
					break;
				default:
					break;
				}
				eclipseProject.getBugTrackingSystems().add(buggy);
			}
		}

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
				if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof NntpNewsGroup) {
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Irc) {
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof SympaMailingList) {
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof Mbox) {
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof EclipseForum) {
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					exist = true;
					break;
				} else if (communication.getId().equals(ccJson.get("_id").asText()) && communication instanceof DocumentationSystematic) {
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
						((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
						((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
						((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
						((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
						((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
					}
					exist = true;
					break;
				}
			}
			if (!exist) {
				CommunicationChannel communication = null;
				switch (ccJson.get("_type").asText()) {
				case "org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup":
					communication = new NntpNewsGroup();
					communication.setUrl(ccJson.get("url").asText());
					((NntpNewsGroup) communication).setName(ccJson.get("name").asText());
					((NntpNewsGroup) communication).setPort(ccJson.get("port").asInt());
					if (ccJson.has("interval")) {
						((NntpNewsGroup) communication).setInterval(ccJson.get("interval").asInt());
					}
					if (ccJson.has("username")) {
						((NntpNewsGroup) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((NntpNewsGroup) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.irc.Irc":
					communication = new Irc();
					communication.setUrl(ccJson.get("url").asText());
					((Irc) communication).setName(ccJson.get("name").asText());
					((Irc) communication).setDescription(ccJson.get("description").asText());
					((Irc) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Irc) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Irc) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.sympa.SympaMailingList":
					communication = new SympaMailingList();
					communication.setUrl(ccJson.get("url").asText());
					((SympaMailingList) communication).setMailingListName(ccJson.get("MailingListName").asText());
					((SympaMailingList) communication).setMailingListDescription(ccJson.get("MailingListDescription").asText());
					((SympaMailingList) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((SympaMailingList) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((SympaMailingList) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.mbox.Mbox":
					communication = new Mbox();
					communication.setUrl(ccJson.get("url").asText());
					((Mbox) communication).setMboxName(ccJson.get("MboxName").asText());
					((Mbox) communication).setMboxDescription(ccJson.get("MboxDescription").asText());
					((Mbox) communication).setCompressedFileExtension(ccJson.get("compressedFileExtension").asText());
					if (ccJson.has("username")) {
						((Mbox) communication).setUsername(ccJson.get("username").asText());
					}
					if (ccJson.has("password")) {
						((Mbox) communication).setPassword(ccJson.get("password").asText());
					}
					break;
				case "org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum":
					communication = new EclipseForum();
					((EclipseForum) communication).setForum_id(ccJson.get("forum_id").asText());
					((EclipseForum) communication).setForum_name(ccJson.get("forum_name").asText());
					((EclipseForum) communication).setClient_id(ccJson.get("client_id").asText());
					((EclipseForum) communication).setClient_secret(ccJson.get("client_secret").asText());
					break;
				case "org.eclipse.scava.repository.model.documentation.systematic.DocumentationSystematic":
					communication = new DocumentationSystematic();
					if (ccJson.get("loginOption").asText().equals("option1")) {
						communication.setUrl(ccJson.get("url").asText());
						if (ccJson.has("executionFrequency")) {
							((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
						}
					} else if (ccJson.get("loginOption").asText().equals("option2")) {
						if (!ccJson.has("username") || !ccJson.has("password")) {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
						} else {
							communication.setUrl(ccJson.get("url").asText());
							if (ccJson.has("executionFrequency")) {
								((DocumentationSystematic) communication).setExecutionFrequency(Integer.parseInt(ccJson.get("executionFrequency").asText()));
							}
							((DocumentationSystematic) communication).setLoginURL(ccJson.get("loginURL").asText());
							((DocumentationSystematic) communication).setUsername(ccJson.get("username").asText());
							((DocumentationSystematic) communication).setUsernameFieldName(ccJson.get("usernameFieldName").asText());
							((DocumentationSystematic) communication).setPassword(ccJson.get("password").asText());
							((DocumentationSystematic) communication).setPasswordFieldName(ccJson.get("passwordFieldName").asText());
						}
					}
					break;
				default:
					break;
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
