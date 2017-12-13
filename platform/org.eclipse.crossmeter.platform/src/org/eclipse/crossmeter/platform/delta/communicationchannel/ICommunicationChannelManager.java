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

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;

import com.mongodb.DB;

public interface ICommunicationChannelManager<T extends CommunicationChannel> {
	
	public boolean appliesTo(CommunicationChannel communicationChannel);
	
	public CommunicationChannelDelta getDelta(DB db, T communicationChannel, Date date) throws Exception;
	
	public String getContents(DB db, T communicationChannel, CommunicationChannelArticle article) throws Exception;
	
	public Date getFirstDate(DB db, T communicationChannel) throws Exception;
	
}
