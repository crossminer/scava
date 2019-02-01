/*******************************************************************************
 * Copyright (c) 2019 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.importers.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.gitlab.GitLabRepository;
import org.eclipse.scava.repository.model.gitlab.importer.GitLabImporter;
import org.eclipse.scava.repository.model.importer.exception.WrongUrlException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.Mongo;

public class GitLabImporterTest {
	
	static Mongo mongo;
	static Platform platform;
	static GitLabImporter im;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new GitLabImporter();
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	
//	@Test
//	public void testValidInput() throws WrongUrlException {
//		// Prints " API rate limit exceeded." message.
//		// TODO: should we throw a InvalidUrlException instead of returning null? 
//		assertNotNull( im.importProjectByUrl("https://github.com/facebook/react", platform));
////		assertNull( im.importProjectByUrl(null, platform)); // This will fail
//		assertNotNull( im.importProject("facebook/react", platform));
////		assertNull( im.importRepository(null, platform)); // This will fail
//	}

	@Test	
	public void testImportByUrlAndUpdate() throws Exception {
		GitLabRepository project = im.importProjectByUrl("https://gitlab.ow2.org/asm/asm", platform);
		assertNotNull(project);
		System.out.println("project="+project);
	}
	
//	@Test(expected = WrongUrlException.class)
//	public void testInvalidInput() throws WrongUrlException {
//		// Prints " API rate limit exceeded." message.
//		// TODO: should we throw a InvalidUrlException instead of returning null? 
//		
//			assertNull( im.importProjectByUrl("", platform));
//			//assertNull( im.importProjectByUrl(null, platform)); // This will fail
//			assertNull( im.importProject("", platform));
//			
//			//assertNull( im.importRepository(null, platform)); // This will fail
//		
//	}
}
