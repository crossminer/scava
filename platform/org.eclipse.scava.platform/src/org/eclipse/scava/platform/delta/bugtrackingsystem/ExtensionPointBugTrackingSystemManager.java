/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.scava.platform.Platform;
import org.eclipse.scava.platform.util.ExtensionPointHelper;

public class ExtensionPointBugTrackingSystemManager extends PlatformBugTrackingSystemManager {
	
	public ExtensionPointBugTrackingSystemManager(Platform platform) {
		super(platform);
	}

	protected final String bugTrackingSystemExtensionPointId = "org.eclipse.scava.platform.managers.bugtracking";
	
	public List<IBugTrackingSystemManager> getBugTrackingSystemManagers() {
		if (bugTrackingSystemManagers == null) {
			bugTrackingSystemManagers = new ArrayList<IBugTrackingSystemManager>();
			
			for (IConfigurationElement confElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(bugTrackingSystemExtensionPointId)) {
				try {
					bugTrackingSystemManagers.add((IBugTrackingSystemManager) confElement.createExecutableExtension("manager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
//			System.err.println("Registered bug tracking managers: ");
//			for (IBugTrackingSystemManager man : bugTrackingSystemManagers) {
//				System.err.println("\t" + man.getClass());
//			}
		}
		
		return bugTrackingSystemManagers;
		
	}
}
