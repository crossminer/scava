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

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.platform.vcs.git.GitManager;
import org.eclipse.scava.repository.model.vcs.git.GitRepository;
import org.junit.Before;
import org.junit.Test;

public class TestGitManager {
	GitManager manager;
	GitRepository repository;
	
	@Before
	public void setup() {
		manager = new GitManager();
		repository = new GitRepository();
		repository.setUrl("https://github.com/crossminer/scava");
	}

	@Test
	public void testGetDelta() throws Exception {
		VcsRepositoryDelta delta;
		
		delta = manager.getDelta(repository, "30237476f5b853ef435d8786fd4f27abc711a94b", "3355c18c3b04f5230778aa32fd0087624f3876f3");
		assertEquals(4, delta.getCommits().size());
		
		delta = manager.getDelta(repository, "cb7662732e6f385e4f1945691c8499a77043cc9e", "b1e3562fd28b0b5117b0fc67a504002addb3c4dc");
		assertEquals(11, delta.getCommits().size());
		
		delta = manager.getDelta(repository, "988f81474f7ea01c3f8bf5d481e651261855408f", "ad1a2d5d3aaf2ff3b2d620dc35721a95449ff665");
		assertEquals(2, delta.getCommits().size());

		delta = manager.getDelta(repository, "ad1a2d5d3aaf2ff3b2d620dc35721a95449ff665", "ad1a2d5d3aaf2ff3b2d620dc35721a95449ff665");
		assertEquals(1, delta.getCommits().size());
	}
	
	@Test
	public void testGetFirstRevision() throws Exception {
		String rev = manager.getFirstRevision(repository);
		assertEquals("c92238203809da53f36c2f41444b95de770e7133", rev);
	}

	@Test
	public void testGetRevisionsForDate() throws Exception {
		String[] revs;

		revs = manager.getRevisionsForDate(repository, new Date("20181128"));
		assertEquals(1, revs.length);
		assertEquals("ffcfac883c01af1955e20e0051d11db1fc8fc06b", revs[0]);

		revs = manager.getRevisionsForDate(repository, new Date("20181012"));
		assertEquals(4, revs.length);
		assertEquals("ad1a2d5d3aaf2ff3b2d620dc35721a95449ff665", revs[0]);
		assertEquals("280e3772f035a339d75c6874ccbecd5739679f3b", revs[1]);
		assertEquals("c6d8856bf2aed803f570f1738987d8e4a4de93c0", revs[2]);
		assertEquals("211fd5871e9f8ac2b331ec27f12dcd720bc7172d", revs[3]);

		revs = manager.getRevisionsForDate(repository, new Date("20181009"));
		assertEquals(0, revs.length);

		revs = manager.getRevisionsForDate(repository, new Date("20180917"));
		assertEquals(7, revs.length);
	}
	
	@Test
	public void testGetDateForRevision() throws Exception {
		assertEquals("20180918", manager.getDateForRevision(repository, "a3139586a958bd5efb9f599e3329e2326bc858c8").toString());
		assertEquals("20181012", manager.getDateForRevision(repository, "ad1a2d5d3aaf2ff3b2d620dc35721a95449ff665").toString());
		assertEquals("20181015", manager.getDateForRevision(repository, "e8cdc23b9feedd3838c4c097da4f0ecc76fee906").toString());
	}
	
	@Test
	public void testCompareVersions() throws Exception {
		String v1 = "ffcfac883c01af1955e20e0051d11db1fc8fc06b"; // Newer
		String v2 = "280e3772f035a339d75c6874ccbecd5739679f3b"; // Older

		assertEquals(1, manager.compareVersions(repository, v1, v2));
		assertEquals(0, manager.compareVersions(repository, v1, v1));
		assertEquals(0, manager.compareVersions(repository, v2, v2));
		assertEquals(-1, manager.compareVersions(repository, v2, v1));
	}
}
