/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.delta.communicationchannel;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;

import com.mongodb.DB;

public interface ICommunicationChannelManager<T extends CommunicationChannel> {
	
	public boolean appliesTo(CommunicationChannel communicationChannel);
	
	public CommunicationChannelDelta getDelta(DB db, T communicationChannel, Date date) throws Exception;
	
	public String getContents(DB db, T communicationChannel, CommunicationChannelArticle article) throws Exception;
	
	public Date getFirstDate(DB db, T communicationChannel) throws Exception;
	
}
