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
package org.eclipse.scava.plugin.usermonitoring.event.events.launch;

import java.io.IOException;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.event.IProjectRelated;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.swt.widgets.Display;

public class LaunchEvent extends Event implements IProjectRelated {

	private final ILaunch launch;
	private IJavaProject javaProject;
	String launchMode;

	public LaunchEvent(ILaunch launch) {
		this.launch = launch;
		boolean test = false;

		ILaunchConfiguration launchConfiguration = launch.getLaunchConfiguration();
		launchMode = launch.getLaunchMode();

		try {
			String projectName = launchConfiguration.getAttribute("org.eclipse.jdt.launching.PROJECT_ATTR", "default");
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			IProject project = root.getProject(projectName);

			if (project.exists()) {

				javaProject = JavaCore.create(project);

			}

		} catch (CoreException e) {
			e.printStackTrace();
		}

		try {
			test = launchConfiguration.hasAttribute("org.eclipse.jdt.junit.TEST_KIND");
		} catch (CoreException e1) {
		}

		if (test) {
			launchMode = "test";
		}

	}

	@Override
	public String toString() {

		return "Date: " + date + " VertexType: " + VertexType.LAUNCH_EVENT + " EventType: " + launchMode;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {

		isProjectUnderObservation(javaProject);

		Vertex projectVertex = null;
		try {

			projectVertex = allocator.findProjectVertex(javaProject);
		} catch (IOException | CoreException e) {
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);
			e.printStackTrace();
		}

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.LAUNCH_EVENT), new VertexProperty("Type", launchMode), new VertexProperty("TimeStamp", date));

		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.RELATED_PROJECT, projectVertex));
		return eventVertex;

	}
}
