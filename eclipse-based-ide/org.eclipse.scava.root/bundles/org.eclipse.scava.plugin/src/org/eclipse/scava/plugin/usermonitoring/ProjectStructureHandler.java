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
package org.eclipse.scava.plugin.usermonitoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.properties.Properties;
import org.eclipse.scava.plugin.usermonitoring.event.events.projectstructure.ProjectStructureBuilderEvent;

public class ProjectStructureHandler {

	public static void addProjectToAnalise(IProject project) throws JavaModelException, CoreException {
		printProjectInfo(project);
	}

	public static void exploreProjects() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] projects = root.getProjects();
		for (IProject project : projects) {
			try {
				printProjectInfo(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

	}

	private static void printProjectInfo(IProject project) throws CoreException, JavaModelException {
		if (project.isOpen() && project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			String persistentProperty = project.getPersistentProperty(Properties.PROJECT_GITHUB_URL);

			if (persistentProperty == null || persistentProperty.equals("")) {
				return;
			}

			IJavaProject javaProject = JavaCore.create(project);
			printPackageInfos(javaProject);
		}
	}

	private static void printPackageInfos(IJavaProject javaProject) throws CoreException {
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				printICompilationUnitInfo(mypackage);

			}

		}
	}

	private static void printICompilationUnitInfo(IPackageFragment mypackage) throws CoreException {
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			printCompilationUnitDetails(unit);

		}
	}

	private static void printCompilationUnitDetails(ICompilationUnit unit) throws CoreException {

		Activator.getDefault().getEventBus().post(new ProjectStructureBuilderEvent(unit));

	}

}
