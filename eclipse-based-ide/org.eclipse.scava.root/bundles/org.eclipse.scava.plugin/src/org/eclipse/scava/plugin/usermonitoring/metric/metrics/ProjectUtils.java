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
package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.scava.plugin.properties.Properties;
import org.eclipse.scava.plugin.usermonitoring.ScavaProject;

public class ProjectUtils {

	public static List<ScavaProject> getProjectsForMetricCalculation() throws CoreException {
		List<ScavaProject> projectsForMetricCalculation = new ArrayList<>();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] projects = root.getProjects();
		for (IProject project : projects) {

			if (project.isOpen()) {

				String projectId = project.getPersistentProperty(Properties.PROJECT_GITHUB_URL);
				if (projectId != null && !projectId.isEmpty()) {
					ScavaProject scavaProject = new ScavaProject(projectId, project);
					projectsForMetricCalculation.add(scavaProject);

					if (project.isOpen() && project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
						IJavaProject javaProject = JavaCore.create(project);
						IPackageFragment[] packages = javaProject.getPackageFragments();
						for (IPackageFragment mypackage : packages) {
							if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
								for (ICompilationUnit unit : mypackage.getCompilationUnits()) {

									String packageDeclaration = getPackageDeclaration(unit);
									String fullQualifiedName = packageDeclaration + "." + unit.getElementName();
									scavaProject.addFileToProject(fullQualifiedName);

								}

							}

						}

					}

				}
			}
		}
		return projectsForMetricCalculation;
	}

	private static String getPackageDeclaration(ICompilationUnit compilationUnit) throws JavaModelException {
		IPackageDeclaration[] packageDeclarations = compilationUnit.getPackageDeclarations();
		String packageDeclaration = "";
		for (IPackageDeclaration iPackageDeclaration : packageDeclarations) {
			packageDeclaration = iPackageDeclaration.getElementName();
		}

		return packageDeclaration;
	}

}
