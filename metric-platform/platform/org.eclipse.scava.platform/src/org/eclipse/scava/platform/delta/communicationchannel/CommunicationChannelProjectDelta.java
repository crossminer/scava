/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.communicationchannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.storage.communicationchannel.CommunicationChannelDataManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;


public class CommunicationChannelProjectDelta {
	
	protected List<CommunicationChannelDelta> communicationChannelDeltas = new ArrayList<CommunicationChannelDelta>();
	
	public CommunicationChannelProjectDelta(DB db, Project project, Date date, ICommunicationChannelManager communicationChannelManager) throws Exception {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			//Temporal solution and horrible solution
			//TODO: IMPROVE THIS
			if(communicationChannel.getCommunicationChannelType().equals("Mbox"))
			{
				File tempDir = createDataStorage(project, communicationChannel.getCommunicationChannelType());
				CommunicationChannelDataManager.intializeStorage(communicationChannel.getOSSMeterId(), tempDir.toPath());
			}
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
	
	private static File createDataStorage(Project project, String type)
	{
		File projectStorage = new File(project.getExecutionInformation().getStorage().getPath());
		File dataStorage = new File(projectStorage, type+"Data");
		if(!dataStorage.exists())
			dataStorage.mkdirs();
		return dataStorage;
	}
}
