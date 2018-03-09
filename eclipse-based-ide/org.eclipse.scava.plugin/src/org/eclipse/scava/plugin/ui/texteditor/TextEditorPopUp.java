/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.texteditor;



import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


public class TextEditorPopUp implements IObjectActionDelegate {

	@Override
	public void run(IAction action) {
		Activator.getDefault().getMainController().generalImprovementsOnCode();
		
		
	}

	

	public void selectionChanged(IAction action, ISelection selection) {

	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}



}
