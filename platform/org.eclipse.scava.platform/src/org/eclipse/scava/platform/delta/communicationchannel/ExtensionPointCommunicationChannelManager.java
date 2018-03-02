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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.util.ExtensionPointHelper;

public class ExtensionPointCommunicationChannelManager extends PlatformCommunicationChannelManager {
	
	public ExtensionPointCommunicationChannelManager(Platform platform) {
		super(platform);
	}

	protected final String communicationChannelManagerExtensionPointId = "org.eclipse.scava.platform.managers.communicationchannel";
	
	public List<ICommunicationChannelManager> getCommunicationChannelManagers() {
		if (communicationChannelManagers == null) {
			communicationChannelManagers = new ArrayList<ICommunicationChannelManager>();
			
			for (IConfigurationElement confElement : 
				ExtensionPointHelper.getConfigurationElementsForExtensionPoint(communicationChannelManagerExtensionPointId)) {
				try {
					communicationChannelManagers.add((ICommunicationChannelManager) 
							confElement.createExecutableExtension("manager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
//			System.err.println("Registered communication channel managers: ");
//			for (ICommunicationChannelManager man : communicationChannelManagers) {
//				System.err.println("\t" + man.getClass());
//			}
		}
		return communicationChannelManagers;
	}
}
