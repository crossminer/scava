/*******************************************************************************
 * Copyright (c) 2018 University of York and Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.repository.model.CommunicationChannel;

public class CommunicationChannelDelta  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	transient protected CommunicationChannel communicationChannel;
	protected List<CommunicationChannelArticle> articles = new ArrayList<CommunicationChannelArticle>();

	
	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}
	
	public void setCommunicationChannel(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
	}
	
	public void setNewsgroup(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
	}
	
	public List<CommunicationChannelArticle> getArticles() {
		return articles;
	}
	
}
