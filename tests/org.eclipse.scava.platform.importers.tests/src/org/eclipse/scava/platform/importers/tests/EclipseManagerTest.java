/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.importers.tests;

import static org.junit.Assert.*;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.vcs.AbstractVcsManager;
import org.eclipse.scava.platform.vcs.git.GitManager;
import org.eclipse.scava.platform.vcs.svn.SvnManager;
import org.eclipse.scava.repository.model.*;
import org.eclipse.scava.repository.model.eclipse.EclipseProject;
import org.eclipse.scava.repository.model.eclipse.importer.EclipseProjectImporter;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.Mongo;

public class EclipseManagerTest {
	static Mongo mongo;
	static Platform platform;
	static EclipseProjectImporter im;
	AbstractVcsManager manager;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new EclipseProjectImporter();
		//repository.setUrl("https://code.google.com/p/super-awesome-fighter");
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	
	@Test
	public void testEclipseBTS()
	{
		fail("Not yet implement");
	}
	
	
	@Test
	public void testEclipseVCSRepository() throws Exception {
		ProjectCollection pc = platform.getProjectRepositoryManager().getProjectRepository().getProjects();
		boolean test = true;
		int countSVN = 0;
		int countGit = 0;
		for (Project project : pc) 
		{
			
			if (project instanceof EclipseProject)
			{
				for (VcsRepository vcs : project.getVcsRepositories()) {
					
					if (vcs instanceof GitRepository)
					{
						try
						{
							countGit ++;
							manager = new GitManager();
							GitRepository bb = (GitRepository)vcs;
							manager.getFirstRevision(bb);
						} catch (Exception e) {
							System.out.println("Manager exception when call GIT getFirstRevision for Project: " + project.getShortName());
							test = false;
						}
					}
					if (vcs instanceof SvnRepository)
					{
						try
						{
							countSVN ++;
							manager = new SvnManager();
							SvnRepository bb = (SvnRepository)vcs;
							manager.getFirstRevision(bb);
						} catch (Exception e) {
							System.out.println("Manager exception when call SVN getFirstRevision for Project: " + project.getShortName());
							test = false;
						}
					}
				}
			}
		}
		System.out.println("Total Git VCS = " + countGit );
		System.out.println("Total SVN VCS = " + countSVN );
		assertTrue(test);
	}
}
