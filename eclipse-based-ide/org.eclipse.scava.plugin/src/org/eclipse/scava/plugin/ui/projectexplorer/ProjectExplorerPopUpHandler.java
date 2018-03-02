/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.projectexplorer;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.utils.Utils;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ProjectExplorerPopUpHandler implements IObjectActionDelegate {

	@Override
	public void run(IAction action) {
		IProject project = Utils.getSelectedProjectFromProjectExplorer();
		IJavaProject javaProject = JavaCore.create(project);
		Activator.getDefault().getMainController().showLibraryUpdateDialog(javaProject);

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	
	}

}
