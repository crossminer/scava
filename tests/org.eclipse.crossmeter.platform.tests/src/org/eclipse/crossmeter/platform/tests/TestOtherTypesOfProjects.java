/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.tests;

import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.github.GitHubRepository;
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
