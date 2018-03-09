/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.cache.communicationchannel;

import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;

public interface ICommunicationChannelContentsCache {
	
	public String getCachedContents(CommunicationChannelArticle article);
	
	public void putContents(CommunicationChannelArticle article, String contents);
}
