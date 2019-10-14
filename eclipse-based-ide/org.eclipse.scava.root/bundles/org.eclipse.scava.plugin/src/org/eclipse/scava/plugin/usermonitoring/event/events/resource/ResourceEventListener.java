/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event.events.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.scava.plugin.usermonitoring.ProjectStructureHandler;
import org.eclipse.scava.plugin.usermonitoring.event.IEventListener;

public class ResourceEventListener implements IEventListener, IResourceChangeListener {

	public ResourceEventListener() {

	}

	public void resourceChanged(final IResourceChangeEvent event) {
		if (event == null || event.getDelta() == null) {
			return;
		}

		try {
			event.getDelta().accept(new IResourceDeltaVisitor() {
				public boolean visit(final IResourceDelta delta) throws CoreException {
					IResource resource = delta.getResource();

					if (event.getType() == 16 && ((resource.getType() & IResource.PROJECT) != 0) && resource.getProject().isOpen()
							&& ((delta.getKind() == IResourceDelta.CHANGED) || delta.getKind() == IResourceDelta.ADDED) && ((delta.getFlags() & IResourceDelta.OPEN) != 0)) {

						IProject project = (IProject) resource;
						projectOpened(project);
					}
					return true;
				}

			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void projectOpened(IProject project) throws JavaModelException, CoreException {

		ProjectStructureHandler.addProjectToAnalise(project);

	}
}
