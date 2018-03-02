/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.scava.commons.ITypeRepresentation;
import org.eclipse.scava.commons.ITypeRepresentedByKind;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
public class Utils {
	
	public static IWorkbenchPage page;
	
	public static IJavaProject getCurrentlyEditedProject() {
		IWorkbench iworkbench = PlatformUI.getWorkbench();
		
		IWorkbenchWindow iworkbenchwindow = iworkbench.getActiveWorkbenchWindow();
		
		IWorkbenchPage iworkbenchpage = iworkbenchwindow.getActivePage();
		page = iworkbenchwindow.getActivePage();
		
		IEditorPart ieditorpart = iworkbenchpage.getActiveEditor();
		
		IEditorInput input = ieditorpart.getEditorInput();
		
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			
			IProject currentProject = file.getProject();
			try {
				if (currentProject.isOpen() && currentProject.hasNature(JavaCore.NATURE_ID)) {
					return JavaCore.create(currentProject);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> filterType(List<? extends ITypeRepresentedByKind> collection, ITypeRepresentation type) {
		return collection.stream().filter(element -> element.getKind() == type).map(element -> (T) element).collect(
				Collectors.toList());
	}
	
	public static IProject getSelectedProjectFromProjectExplorer() {
		ISelectionService selectionService = Workbench.getInstance().getActiveWorkbenchWindow().getSelectionService();
		
		ISelection selection = selectionService.getSelection();
		
		IProject project = null;
		if (selection instanceof IStructuredSelection) {
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
		}
		return project;
	}
	
	public static ICompilationUnit GetCompilationUnitFromFile(IFile file) {
		return JavaCore.createCompilationUnitFrom(file);
	}
	
	public static String getPluginDir(String pluginId) {
		/* get bundle with the specified id */
		Bundle bundle = Platform.getBundle(pluginId);
		if (bundle == null)
			throw new RuntimeException("Could not resolve plugin: " + pluginId + "\r\n"
					+ "Probably the plugin has not been correctly installed.\r\n"
					+ "Running eclipse from shell with -clean option may rectify installation.");
		
		/* resolve Bundle::getEntry to local URL */
		URL pluginURL = null;
		try {
			pluginURL = FileLocator.resolve(bundle.getEntry("/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not get installation directory of the plugin: " + pluginId);
		}
		String pluginInstallDir = pluginURL.getPath().trim();
		if (pluginInstallDir.length() == 0)
			throw new RuntimeException("Could not get installation directory of the plugin: " + pluginId);
		
		/*
		 * since path returned by URL::getPath starts with a forward slash, that
		 * is not suitable to run commandlines on Windows-OS, but for Unix-based
		 * OSes it is needed. So strip one character for windows. There seems to
		 * be no other clean way of doing this.
		 */
		if (Platform.getOS().compareTo(Platform.OS_WIN32) == 0)
			pluginInstallDir = pluginInstallDir.substring(1);
		
		return pluginInstallDir;
	}
	
	public static IFile openFileInProject(IProject project, String targetFile) throws FileNotFoundException {
		IFile file = null;
		
		file = project.getFile(targetFile);
		
		if (!file.exists()) {
			throw new FileNotFoundException("file " + targetFile + " not found in project " + project.getFullPath());
		}
		
		return file;
	}
}
