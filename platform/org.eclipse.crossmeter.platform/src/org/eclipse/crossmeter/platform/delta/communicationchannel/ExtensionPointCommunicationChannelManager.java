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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointCommunicationChannelManager extends PlatformCommunicationChannelManager {
	
	public ExtensionPointCommunicationChannelManager(Platform platform) {
		super(platform);
	}

	protected final String communicationChannelManagerExtensionPointId = "org.eclipse.crossmeter.platform.managers.communicationchannel";
	
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
