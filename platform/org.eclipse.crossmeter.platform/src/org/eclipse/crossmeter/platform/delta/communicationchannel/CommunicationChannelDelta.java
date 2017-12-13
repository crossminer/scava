/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.crossmeter.repository.model.CommunicationChannel;

public class CommunicationChannelDelta  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	transient protected CommunicationChannel communicationChannel;
	protected List<CommunicationChannelArticle> articles = new ArrayList<CommunicationChannelArticle>();
//	protected String latestArticleId;
	
	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}
	
	public void setNewsgroup(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
	}
	
	public List<CommunicationChannelArticle> getArticles() {
		return articles;
	}
	
//	public String getLatestArticleId() {
//		return latestArticleId;
//	}
	
//	public void setLatestArticleId(String latestArticleId) {
//		this.latestArticleId = latestArticleId;
//	} //TODO THIS NEEDS SETTING ON CREATION
	
}
