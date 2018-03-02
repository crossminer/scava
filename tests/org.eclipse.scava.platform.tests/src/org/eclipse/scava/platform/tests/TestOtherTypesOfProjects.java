/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.tests;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.github.GitHubRepository;
import org.junit.Test;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class TestOtherTypesOfProjects {
	
	@Test
	public void testCreateGitProject() throws Exception {
		
		GitHubRepository project = new GitHubRepository();
		
		Platform platform = new Platform(new Mongo());
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
	}
	
	@Test
	public void testGetGitProject() throws Exception {
		
		PongoFactory.getInstance().clear();
		
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(new Mongo());
		for (Project project : platform.getProjectRepositoryManager().getProjectRepository().getProjects()) {
			System.err.println(project);
		}
		
	}
	
}
