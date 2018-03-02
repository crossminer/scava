/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.vcs.git.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsCommitItem;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.vcs.git.GitManager;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;
import org.junit.Before;
import org.junit.Test;

public class TestGitManager {
	GitManager manager;
	GitRepository repository;
	
	@Before
	public void setup() {
		manager = new GitManager();
		repository = new GitRepository();
		repository.setUrl("https://code.google.com/p/super-awesome-fighter");
	}

	@Test
	public void testAppliesTo() {
		assertTrue(manager.appliesTo(mock(GitRepository.class)));
		assertFalse(manager.appliesTo(mock(SvnRepository.class)));
	}
	
	@Test
	public void testCurrentRevision() throws Exception {
		// FIXME: this is not a good test..
		assertEquals("2c2dec1adacabe3f81e5309b7ef11998215b13d3", manager.getCurrentRevision(repository));
	}

	@Test 	
	public void testGetDelta() throws Exception {
		// Will use getCurrentRevision() lookup
		VcsRepositoryDelta delta = manager.getDelta(repository, "2c2dec1adacabe3f81e5309b7ef11998215b13d3");
		assertEquals(1, delta.getCommits().size());
		
		delta = manager.getDelta(repository, "056482c6f3a4360e9ae6c1584790faf2f5d3fdc8", "2c2dec1adacabe3f81e5309b7ef11998215b13d3");
		assertEquals(6, delta.getCommits().size());
		
		delta = manager.getDelta(repository, "056482c6f3a4360e9ae6c1584790faf2f5d3fdc8", "6884a0f30f147923898fbabac2ba08b20d48ec46");
		assertEquals(5, delta.getCommits().size());
		
		delta = manager.getDelta(repository, "3856e357fc8b6f0ddf4dd8173352d0d80dba34f0", "2c2dec1adacabe3f81e5309b7ef11998215b13d3");
		assertEquals(7, delta.getCommits().size());
	}
	
	@Test
	public void testGetFirstRevision() throws Exception {
		String rev = manager.getFirstRevision(repository);
		assertEquals("3856e357fc8b6f0ddf4dd8173352d0d80dba34f0", rev);
	}

	@Test
	public void testGetRevisionsForDate() throws Exception {
		Date date = new Date("20130218");
		String[] revs = manager.getRevisionsForDate(repository, date);
		
		assertEquals(3, revs.length);
		assertEquals("bb9072b4ab97f2a744f32f4939472739c62b0c40", revs[0]);
		assertEquals("44b13da06c6a5934fe1e776e57be3300f58cfce8", revs[1]);
		assertEquals("ce9fbab0690349a1caa2845719e2db8b820b421e", revs[2]);
	}
	
	@Test
	public void testGetDateForRevision() throws Exception {
		Date date = manager.getDateForRevision(repository, "bb9072b4ab97f2a744f32f4939472739c62b0c40");
		assertEquals("20130218", date.toString());
	}
	
	@Test
	public void testCompareVersions() throws Exception {
		String v1 = "bb9072b4ab97f2a744f32f4939472739c62b0c40"; // Newer
		String v2 = "ce9fbab0690349a1caa2845719e2db8b820b421e"; // Older
		
		assertEquals(1, manager.compareVersions(repository, v1, v2));
		assertEquals(0, manager.compareVersions(repository, v1, v1));
		assertEquals(-1, manager.compareVersions(repository, v2, v1));
	}
	
	
	@Test
	public void testGetContents() throws Exception {
		VcsRepositoryDelta delta = manager.getDelta(repository, "2c2dec1adacabe3f81e5309b7ef11998215b13d3");
		for (VcsCommit commit : delta.getCommits()) {
			for (VcsCommitItem item: commit.getItems()) {
				System.out.println(manager.getContents(item));
//				break;
			}
		}
	}
	
	@Test
	public void testLsRemote() throws Exception {
		GitManager m = new GitManager();
		assertTrue(m.validRepository(repository));
		
		VcsRepository repo = new GitRepository();
		repo.setUrl("fooooo");
		assertFalse(m.validRepository(repo));
	}
}
