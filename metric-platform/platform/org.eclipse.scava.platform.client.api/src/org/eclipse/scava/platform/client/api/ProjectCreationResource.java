/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.client.api;

import java.io.IOException;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
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
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProjectCreationResource extends ServerResource {

	@Post
	public Representation createProject(Representation entity) {
		try {
			Platform platform = Platform.getInstance();
			ObjectMapper mapper = new ObjectMapper();
			
			// WARNING: This is a _DESTRUCTIVE_ read. It took me AGES to realise this and it isn't in the Restlet javadoc. I hate you Restlet.
			String j = entity.getText(); 
			
			JsonNode json = mapper.readTree(j);
			
			// Translate into a Project object. FIXME
			Project project = new Project();
			project.setName(json.get("name").asText());
			project.setHomePage(json.get("homePage").asText());
			project.setDescription(json.get("description").asText());
			
			for (JsonNode vcs : (ArrayNode)json.get("vcsRepositories")) {
				VcsRepository repo = null;
				if (vcs.get("type").asText().equals("git")) {
					repo = new GitRepository();
				} 
				else if (vcs.get("type").asText().equals("gitbased")) {
					repo = new DocumentationGitBased();
				}
				else if (vcs.get("type").asText().equals("svn")) {
					repo = new SvnRepository();
				}
//				repo.setName(vcs.get("name").asText());
				repo.setUrl(vcs.get("url").asText());
				project.getVcsRepositories().add(repo);
			}
			for (JsonNode cc : (ArrayNode)json.get("communication_channels")) {
				CommunicationChannel channel = null;
				switch (cc.get("type").asText()) {
					case "nntp":
						NntpNewsGroup newsgroup = new NntpNewsGroup();
						newsgroup.setNewsGroupName(cc.get("name").asText());
						newsgroup.setUrl(cc.get("url").asText());
						newsgroup.setPort(Integer.parseInt(cc.get("port").asText()));
						if (cc.get("interval").asText() != null && !cc.get("interval").asText().equals("")) {
							newsgroup.setInterval(Integer.parseInt(cc.get("interval").asText()));
						}
						if (cc.get("username").asText() != null && !cc.get("username").asText().equals("")) {
							newsgroup.setUsername(cc.get("username").asText());
						}
						if (cc.get("password").asText() != null && !cc.get("password").asText().equals("")) {
							newsgroup.setPassword(cc.get("password").asText());
						}
						channel = newsgroup;
						break;
					case "irc":
						Irc irc = new Irc();
						irc.setUrl(cc.get("url").asText());
						irc.setName(cc.get("name").asText());
						irc.setDescription(cc.get("description").asText());
						irc.setCompressedFileExtension(cc.get("compressedFileExtension").asText());
						if (cc.get("username").asText() != null && !cc.get("username").asText().equals("")) {
							irc.setUsername(cc.get("username").asText());
						}
						if (cc.get("password").asText() != null && !cc.get("password").asText().equals("")) {
							irc.setPassword(cc.get("password").asText());
						}
						channel = irc;
						break;
					case "sympa":
						SympaMailingList sympa = new SympaMailingList();
						sympa.setUrl(cc.get("url").asText());
						sympa.setMailingListName(cc.get("mailingListName").asText());
						sympa.setMailingListDescription(cc.get("mailingListDescription").asText());
						sympa.setCompressedFileExtension(cc.get("compressedFileExtension").asText());
						if (cc.get("username").asText() != null && !cc.get("username").asText().equals("")) {
							sympa.setUsername(cc.get("username").asText());
						}
						if (cc.get("password").asText() != null && !cc.get("password").asText().equals("")) {
							sympa.setPassword(cc.get("password").asText());
						}
						channel = sympa;
						break;
					case "mbox":
						Mbox mbox = new Mbox();
						mbox.setUrl(cc.get("url").asText());
						mbox.setMboxName(cc.get("mboxName").asText());
						mbox.setMboxDescription(cc.get("mboxDescription").asText());
						mbox.setCompressedFileExtension(cc.get("compressedFileExtension").asText());
						if (cc.get("username").asText() != null && !cc.get("username").asText().equals("")) {
							mbox.setUsername(cc.get("username").asText());
						}
						if (cc.get("password").asText() != null && !cc.get("password").asText().equals("")) {
							mbox.setPassword(cc.get("password").asText());
						}
						channel = mbox;
						break;
					case "eclipseForum":
						EclipseForum eclipseForum = new EclipseForum();
						eclipseForum.setForum_id(cc.get("forumId").asText());
						eclipseForum.setForum_name(cc.get("forumName").asText());
						eclipseForum.setClient_id(cc.get("clientId").asText());
						eclipseForum.setClient_secret(cc.get("clientSecret").asText());
						channel = eclipseForum;
						break;
					case "documentation systematic":
						DocumentationSystematic systematic = new DocumentationSystematic();
						if (cc.get("loginOption").asText().equals("option1")) {
							systematic.setUrl(cc.get("url").asText());
							if (cc.get("executionFrequency").asText() != null && !cc.get("executionFrequency").asText().equals("")) {
								systematic.setExecutionFrequency(Integer.parseInt(cc.get("executionFrequency").asText()));
							}
						} else if (cc.get("loginOption").asText().equals("option2")) {
							if (cc.get("username").asText().equals("") || cc.get("password").asText().equals("")) {
								systematic.setUrl(cc.get("url").asText());
								if (cc.get("executionFrequency").asText() != null && !cc.get("executionFrequency").asText().equals("")) {
									systematic.setExecutionFrequency(Integer.parseInt(cc.get("executionFrequency").asText()));
								}
							} else {
								systematic.setUrl(cc.get("url").asText());
								if (cc.get("executionFrequency").asText() != null && !cc.get("executionFrequency").asText().equals("")) {
									systematic.setExecutionFrequency(Integer.parseInt(cc.get("executionFrequency").asText()));
								}
								systematic.setLoginURL(cc.get("loginURL").asText());
								systematic.setUsername(cc.get("username").asText());
								systematic.setUsernameFieldName(cc.get("usernameFieldName").asText());
								systematic.setPassword(cc.get("password").asText());
								systematic.setPasswordFieldName(cc.get("passwordFieldName").asText());
							}
						}
						channel = systematic;
						break;
					default:
						continue;
				}
				project.getCommunicationChannels().add(channel);
			}
			for (JsonNode bts : (ArrayNode)json.get("bts")) {
				BugTrackingSystem buggy = null;
				switch (bts.get("type").asText()) {
					case "bugzilla":
						Bugzilla bugs = new Bugzilla();
						bugs.setProduct(bts.get("product").asText());
						bugs.setComponent(bts.get("component").asText());
						buggy = bugs;
						break;
					case "sourceforge":
						SourceForgeBugTrackingSystem sf = new SourceForgeBugTrackingSystem();
						buggy = sf;
						break;
					case "redmine":
						RedmineBugIssueTracker red = new RedmineBugIssueTracker();
						red.setProject(bts.get("project").asText());
						red.setName(bts.get("name").asText());
						buggy = red;
						break;
					case "jira":
						JiraBugTrackingSystem jira = new JiraBugTrackingSystem();
						jira.setProject(bts.get("project").asText());
						jira.setLogin(bts.get("login").asText());
						jira.setPassword(bts.get("password").asText());
						buggy = jira;
						break;
					case "mantis":
						MantisBugTrackingSystem mantis = new MantisBugTrackingSystem();
						mantis.setToken(bts.get("token").asText());
						buggy = mantis;
						break;
					case "bitbucket":
						BitbucketBugTrackingSystem bitbucket = new BitbucketBugTrackingSystem();
						bitbucket.setLogin(bts.get("login").asText());
						bitbucket.setPassword(bts.get("password").asText());
						buggy = bitbucket;
						break;
					default:
						continue;

				}
				buggy.setUrl(bts.get("url").asText());
				project.getBugTrackingSystems().add(buggy);
			}	// TODO: Validate all channels.

			
			String shortName = platform.getProjectRepositoryManager().generateUniqueId(project);
			project.setShortName(shortName);
			
			// TODO: Check it doesn't already exist - how?
			System.out.println("Adding new project");
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
			
			// Query it for returning
			project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(shortName);
			
			getResponse().setStatus(Status.SUCCESS_CREATED);
			return new StringRepresentation(project.getDbObject().toString());

		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("{\"status\":\"error\", \"message\" : \""+e.getMessage()+"\"}");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
	}
	
	@JsonFilter("foofilter")
	class Foo {}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
}
