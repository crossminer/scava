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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.cache.communicationchannel.CommunicationChannelContentsCache;
import org.eclipse.scava.platform.cache.communicationchannel.CommunicationChannelDeltaCache;
import org.eclipse.scava.platform.cache.communicationchannel.ICommunicationChannelContentsCache;
import org.eclipse.scava.platform.cache.communicationchannel.ICommunicationChannelDeltaCache;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.junit.Test;

public class TestCachesCommunicationChannel {

	@Test 
	public void testMissPutHit_Delta_MapDB() {
		missPutHit_Delta(new CommunicationChannelDeltaCache());
	}
	
	@Test 
	public void testMissPutHit_Contents_MapDB() {
		missPutHit_Contents(new CommunicationChannelContentsCache());
	}

	protected void missPutHit_Delta(ICommunicationChannelDeltaCache cache) {
		NntpNewsGroup newsgroup = mock(NntpNewsGroup.class);
		when(newsgroup.getUrl()).thenReturn("url/name");
		CommunicationChannelDelta delta = mock(CommunicationChannelDelta.class);
		
		// Miss
		assertNull(cache.getCachedDelta(newsgroup.getUrl(), new Date()));
		
		// Put
		cache.putDelta(newsgroup.getUrl(), new Date(), delta);
		
		// Hit
		assertNotNull(cache.getCachedDelta(newsgroup.getUrl(), new Date()));
	}

	protected void missPutHit_Contents(ICommunicationChannelContentsCache cache) {
		NntpNewsGroup newsgroup = mock(NntpNewsGroup.class);
		when(newsgroup.getUrl()).thenReturn("url/name");
		CommunicationChannelArticle article = mock(CommunicationChannelArticle.class);
		when(article.getCommunicationChannel()).thenReturn(newsgroup);

		when(article.getArticleNumber()).thenReturn(117);
		
		String contents = "The quick brown fox jumped over the lazy dog.";		
		
		// Miss
		assertNull(cache.getCachedContents(article));
		
		// Put
		cache.putContents(article, contents);
		
		// Hit
		assertEquals(contents, cache.getCachedContents(article));
	}
}
