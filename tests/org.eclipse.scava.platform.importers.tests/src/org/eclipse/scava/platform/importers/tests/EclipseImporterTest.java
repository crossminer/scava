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
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.eclipse.importer.EclipseProjectImporter;
import org.eclipse.scava.repository.model.importer.exception.ProjectUnknownException;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.eclipse.scava.metricprovider.trans.importer.eclipse.EclipseImporterProvider;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.Mongo;

public class EclipseImporterTest {
	
	static Mongo mongo;
	static Platform platform;
	static EclipseProjectImporter im;
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new EclipseProjectImporter();
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	@Rule public ExpectedException expected = ExpectedException.none();
	@Test
	public void eclipseInvalidInput() throws WrongUrlException, ProjectUnknownException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		expected.expect(WrongUrlException.class);
			assertNull( im.importProjectByUrl("", platform));
			assertNull( im.importProjectByUrl(null, platform)); // This will fail
			//assertNotNull( im.importProject("birt", platform));
			//assertNull( im.importRepository(null, platform)); // This will fail
		
	}
	@Test
	public void testImportProjectByUrl() throws WrongUrlException, ProjectUnknownException
	{
		Project project = im.importProjectByUrl("https://projects.eclipse.org/projects/modeling.mdt.modisco", platform);
		assertNotNull(project);
	}
	@Test
	public void eclipseValidInput() throws WrongUrlException, ProjectUnknownException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		//assertNull( im.importProjectByUrl(null, platform)); // This will fail
		assertNotNull( im.importProject("birt", platform));
		// Now update
		Project project = im.importProjectByUrl("http://projects.eclipse.org/projects/birt", platform);
		EclipseImporterProvider mp = new EclipseImporterProvider();
		PongoDB db = mp.adapt(platform.getProjectRepositoryManager().getDb());
		mp.measure(project, null, db);
		Project p = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(project.getName());
		assertNotEquals(p.getDbObject().toString(), project.getDbObject().toString());			
		
		
		//assertNull( im.importRepository(null, platform)); // This will fail
		
	}
	@Test
	public void eclipseEmptyNNTPCommunicationChannel()
	{
		
		Project p = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName("birt");
		boolean b = false;
		for (CommunicationChannel i : p.getCommunicationChannels()) {
			if (i instanceof NntpNewsGroup)
				b = true;
		}
		assertTrue(b);
	}
	

}
