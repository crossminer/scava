/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.delta.vcs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.crossmeter.platform.Platform;
import org.eclipse.crossmeter.platform.util.ExtensionPointHelper;

public class ExtensionPointVcsManager extends PlatformVcsManager {
	
	public ExtensionPointVcsManager(Platform platform) {
		super(platform);
	}

	protected final String vcsManagerExtensionPointId = "org.eclipse.crossmeter.platform.managers.vcs";
	
	@Override
	public List<IVcsManager> getVcsManagers() {
		if (vcsManagers == null) {
			vcsManagers = new ArrayList<IVcsManager>();
			
			for (IConfigurationElement confElement : ExtensionPointHelper.getConfigurationElementsForExtensionPoint(vcsManagerExtensionPointId)) {
				try {
					vcsManagers.add((IVcsManager) confElement.createExecutableExtension("manager"));
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
//			System.err.println("Registered VCS Managers: ");
//			for (IVcsManager man : vcsManagers) {
//				System.err.println("\t" + man.getClass());
//			}
		}
		
		return vcsManagers;
	}
}
