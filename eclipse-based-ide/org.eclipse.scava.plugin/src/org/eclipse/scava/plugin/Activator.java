/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.scava.plugin.main.MainController;
import org.eclipse.scava.plugin.main.MainModel;
import org.eclipse.scava.plugin.main.MainView;
import org.eclipse.scava.plugin.usermonitoring.event.EventManager;
import org.eclipse.scava.plugin.usermonitoring.event.scava.ScavaEventListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.scava.plugin"; //$NON-NLS-1$

	public static final String IMAGE_SCAVA_ICON_32	= "icons/SCAVA-icon-32.png";
	
	// The shared instance
	private static Activator plugin;

	private Shell activeShell;

	private MainController mainController;
	
	/**
	 * The constructor
	 */
	public Activator() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	
	public static void activityMonitoring() {
		EventManager.enableListeners();
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		getActiveShellWhenItsReady();
	}

	private void getActiveShellWhenItsReady() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
				Shell shell = activeWorkbenchWindow.getShell();
				setActiveShell(shell);

				activityMonitoring();
			}
		});
	}

	private void setActiveShell(Shell activeShell) {
		this.activeShell = activeShell;
		initMainMVC();
	}

	private void initMainMVC() {
		MainModel mainModel = new MainModel();
		MainView mainView = new MainView();
		mainController = new MainController(mainModel, mainView, activeShell);
		
		mainController.addScavaListener(new ScavaEventListener());
	}

	public MainController getMainController() {
		return mainController;
	}

	public Shell getActiveShell() {
		return activeShell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);

		if (mainController != null) {
			mainController.close();
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative
	 * path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		reg.put(IMAGE_SCAVA_ICON_32, imageDescriptorFromPlugin(PLUGIN_ID, IMAGE_SCAVA_ICON_32));
	}
}