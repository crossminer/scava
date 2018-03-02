/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.rascal.test;

import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;

/**
 * This class is purely for illustration purposes and is not intended for release.
 * @author jimmy
 *
 */
public class ProjectCreationUtil {

	public static Project createProjectSvnNntpBugzilla(String name, String shortname, String description, String svnUrl, String bugzillaUrl, String bugzillaProduct, String bugzillaComponent, String nntpName, String nntpUrl, boolean nntpAuth, String usr, String pwd) {
		Project project = new Project();
		project.setName(name);
		project.setShortName(shortname);
		project.setDescription(description);
		
		SvnRepository svn = new SvnRepository();
		svn.setUrl(svnUrl);
		project.getVcsRepositories().add(svn);
		
		Bugzilla bugzilla = new Bugzilla();
		bugzilla.setUrl(bugzillaUrl);
		bugzilla.setProduct(bugzillaProduct); 
		bugzilla.setComponent(bugzillaComponent); 
		project.getBugTrackingSystems().add(bugzilla);
		
		NntpNewsGroup nntp = new NntpNewsGroup();
		nntp.setName(nntpName);
		nntp.setUrl(nntpUrl);
		nntp.setAuthenticationRequired(nntpAuth);
		nntp.setUsername(usr);
		nntp.setPassword(pwd);
		nntp.setPort(119);
//		ooNntp.setInterval(10000);
		nntp.setLastArticleChecked("-1");
		project.getCommunicationChannels().add(nntp);
		return project;
	}
	
	public static Project createProjectWithNewsGroup(String name, String url, String newsGroupName, 
			Boolean authenticationRequired, String username, String password, int port, int interval){
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		
		NntpNewsGroup newsGroup = new NntpNewsGroup();
		newsGroup.setUrl(url);
		newsGroup.setNewsGroupName(newsGroupName);
		newsGroup.setAuthenticationRequired(authenticationRequired);
		newsGroup.setUsername(username);
		newsGroup.setPassword(password);
		newsGroup.setPort(port);
//		newsGroup.setInterval(interval);
		newsGroup.setLastArticleChecked("-1");
		project.getCommunicationChannels().add(newsGroup);
		
		return project;
	}
	
	public static Project createGitProject(String name, String url) {
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		project.setDescription(name);
		
		GitRepository repo = new GitRepository();
		repo.setUrl(url);
		
		project.getVcsRepositories().add(repo);
		return project;
	}
	
	public static Project createProjectWithBugTrackingSystem(String name, String url, String product, String component){
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		
		Bugzilla bugzilla = new Bugzilla();
		bugzilla.setUrl(url);
		bugzilla.setProduct(product);
		if (component!=null) bugzilla.setComponent(component);
		project.getBugTrackingSystems().add(bugzilla);
		
		return project;
	}
	
	public static Project createSvnProject(String name, String url) {
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		SvnRepository svnRepository = new SvnRepository();
		svnRepository.setUrl(url);
		project.getVcsRepositories().add(svnRepository); 
		return project;
	}
	
//	
//	public static Project createGitHubProject(String login, String repository, String url) {
//		GitHubProject project = new GitHubProject();
//		project.setName(login + "-" + repository);
//		
//		GitHubRepository gitHubRepository = new GitHubRepository();
//		gitHubRepository.setName(repository);
//		gitHubRepository.setUrl(url);
//		
//		GitHubUser owner = new GitHubUser();
//		owner.setLogin(login);
//		gitHubRepository.setOwner(owner);
//		
//		project.getVcsRepositories().add(gitHubRepository);
//		return project;
//	}
	
	
//	public static Project createSourceForgeProject(String name) {
//		SourceForgeProject project = new SourceForgeProject();
//		project.setName(name);
//		/* TODO: Why was this code commented out?
//		SvnRepository svnRepository = new SvnRepository();
//		svnRepository.setUrl("http://" + name + ".svn.sourceforge.net/svnroot/" + name);
//		project.getVcsRepositories().add(svnRepository); */
//		return project;
//	}
}
