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
package org.eclipse.crossmeter.platform.delta.bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointBugTrackingSystemManager extends PlatformBugTrackingSystemManager {
	
	public ExtensionPointBugTrackingSystemManager(Platform platform) {
		super(platform);
	}

	protected final String bugTrackingSystemExtensionPointId = "org.eclipse.crossmeter.platform.managers.bugtracking";
	
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
