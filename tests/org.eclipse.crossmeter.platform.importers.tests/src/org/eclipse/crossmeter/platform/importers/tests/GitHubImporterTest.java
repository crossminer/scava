/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.importers.tests;

import static org.junit.Assert.*;

import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.github.GitHubRepository;
import org.eclipse.crossmeter.repository.model.github.importer.GitHubImporter;
import org.eclipse.crossmeter.repository.model.importer.exception.WrongUrlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.eclipse.crossmeter.metricprovider.trans.importer.github.GitHubImporterProvider;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.Mongo;

public class GitHubImporterTest {
	
	static Mongo mongo;
	static Platform platform;
	static GitHubImporter im;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new GitHubImporter();
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	
	@Test
	public void testValidInput() throws WrongUrlException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		assertNotNull( im.importProjectByUrl("https://github.com/facebook/react", platform));
//		assertNull( im.importProjectByUrl(null, platform)); // This will fail
		assertNotNull( im.importProject("facebook/react", platform));
//		assertNull( im.importRepository(null, platform)); // This will fail
	}

	@Test	
	public void testImportByUrlAndUpdate() throws Exception {
		GitHubRepository project = im.importProjectByUrl("https://github.com/Igalia/libreplan", platform);
		assertNotNull(project);
		PlatformBugTrackingSystemManager manager = new ExtensionPointBugTrackingSystemManager(platform);
		for (BugTrackingSystem bts : project.getBugTrackingSystems()) {
		    manager.getFirstDate(null, bts);
		}
		
	}
	
	@Test(expected = WrongUrlException.class)
	public void testInvalidInput() throws WrongUrlException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		
			assertNull( im.importProjectByUrl("", platform));
			//assertNull( im.importProjectByUrl(null, platform)); // This will fail
			assertNull( im.importProject("", platform));
			
			//assertNull( im.importRepository(null, platform)); // This will fail
		
	}
}
