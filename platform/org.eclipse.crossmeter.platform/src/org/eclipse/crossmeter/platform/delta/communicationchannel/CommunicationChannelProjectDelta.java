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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;

import com.mongodb.DB;


public class CommunicationChannelProjectDelta {
	
	protected List<CommunicationChannelDelta> communicationChannelDeltas = new ArrayList<CommunicationChannelDelta>();
	
	public CommunicationChannelProjectDelta(DB db, Project project, Date date, ICommunicationChannelManager communicationChannelManager) throws Exception {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			CommunicationChannelDelta delta = communicationChannelManager.getDelta(db, communicationChannel, date);
			if (delta != null) communicationChannelDeltas.add(delta);
		}
	}
	
	public List<CommunicationChannelDelta> getCommunicationChannelSystemDeltas() {
		return communicationChannelDeltas;
	}
	
	public void setRepoDeltas(List<CommunicationChannelDelta> communicationChannelDeltas) {
		this.communicationChannelDeltas = communicationChannelDeltas;
	}
}
