/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.common.eventbus.EventBus;

public class MainModel extends AbstractModel implements IMainModel {
	private final Shell shell;
	private final EventBus eventBus;

	public MainModel(Shell shell, EventBus eventBus) {
		super();
		this.shell = shell;
		this.eventBus = eventBus;
	}

	@Override
	public Shell getShell() {
		return shell;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public IProject getActiveProject() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelection selection = activeWorkbenchWindow.getSelectionService().getSelection();
		
		if (selection instanceof IStructuredSelection) {
			IProject project = null;
			Object element = ((IStructuredSelection) selection).getFirstElement();

			if (element instanceof IResource) {
				project = ((IResource) element).getProject();
			} else if (element instanceof PackageFragmentRootContainer) {
				IJavaProject jProject = ((PackageFragmentRootContainer) element).getJavaProject();
				project = jProject.getProject();
			} else if (element instanceof IJavaElement) {
				IJavaProject jProject = ((IJavaElement) element).getJavaProject();
				project = jProject.getProject();
			}
			return project;
		}else if( selection instanceof ITextSelection ) {
			IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

			IEditorPart activeEditor = activePage.getActiveEditor();

			if (activeEditor != null) {
			   IEditorInput input = activeEditor.getEditorInput();

			   IProject project = input.getAdapter(IProject.class);
			   if (project == null) {
			      IResource resource = input.getAdapter(IResource.class);
			      if (resource != null) {
			         project = resource.getProject();
			         return project;
			      }
			   }
			}
		}
		
		return null;
	}

}
