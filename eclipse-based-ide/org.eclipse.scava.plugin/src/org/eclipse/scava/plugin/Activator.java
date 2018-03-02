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
import org.eclipse.scava.plugin.main.MainController;
import org.eclipse.scava.plugin.main.MainModel;
import org.eclipse.scava.plugin.main.MainView;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.idarav.plugins.asttest2"; //$NON-NLS-1$
	
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
	}
	
	public MainController getMainController() {
		return mainController;
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
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
