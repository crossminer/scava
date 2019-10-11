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
package org.eclipse.scava.plugin.usermonitoring.event;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.scava.plugin.usermonitoring.event.events.classpath.ClasspathChangeListener;
import org.eclipse.scava.plugin.usermonitoring.event.events.eclipse.EclipseCloseEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.events.launch.LaunchEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.events.resource.ResourceEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.events.search.SearchEventListener;
import org.eclipse.scava.plugin.usermonitoring.event.events.window.WindowEventListener;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class ListenerManager {

	public void enableListeners() {

		JavaCore.addElementChangedListener(new ClasspathChangeListener(), ElementChangedEvent.POST_CHANGE);

		subscribeResourceListener();
		subscribeWindowListener();
		subscribeEclipseCloseListener();
		subscribeLaunchListener();
		subscribeSearchListener();
	}

	private void subscribeEclipseCloseListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWorkbenchListener(new EclipseCloseEventListener());
	}
	
	private void subscribeSearchListener() {
		NewSearchUI.addQueryListener(new SearchEventListener());
	}

	private void subscribeLaunchListener() {

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		manager.addLaunchListener(new LaunchEventListener());
	}

	private void subscribeWindowListener() {
		IWorkbench workbench = PlatformUI.getWorkbench();

		WindowEventListener windowListener = new WindowEventListener();
		windowListener.windowOpened(workbench.getWorkbenchWindows()[0]);

		workbench.addWindowListener(windowListener);

	}

	private void subscribeResourceListener() {
		ResourceEventListener listener = new ResourceEventListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener, IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE
				| IResourceChangeEvent.PRE_BUILD | IResourceChangeEvent.POST_BUILD | IResourceChangeEvent.POST_CHANGE);
	}
}
