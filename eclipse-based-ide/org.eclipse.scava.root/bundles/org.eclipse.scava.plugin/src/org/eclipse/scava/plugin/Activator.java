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

import org.eclipse.scava.plugin.main.IMainController;
import org.eclipse.scava.plugin.main.IMainModel;
import org.eclipse.scava.plugin.main.MainController;
import org.eclipse.scava.plugin.main.MainModel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.common.eventbus.EventBus;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.scava.plugin";
	public static final String IMAGE_SCAVA_ICON_32 = "icons/SCAVA-icon-32.png";

	private static Activator plugin;

	private IMainController mainController;

	/**
	 * The constructor
	 */
	public Activator() {
		
		setLookAndFeel();
	}

	private void setLookAndFeel() {
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

		initMainMVC();
	}

	private class ShellRequester implements Runnable {
		private Shell shell;

		@Override
		public void run() {
			shell = Display.getDefault().getActiveShell();
		}

		public Shell getShell() {
			return shell;
		}

	}

	private void initMainMVC() {
		ShellRequester shellRequester = new ShellRequester();
		Display.getDefault().syncExec(shellRequester);
		Shell activeShell = shellRequester.getShell();

		EventBus eventBus = new EventBus((exception, context) -> {
			System.out.println("EvenBusSubscriberException: " + exception + " | " + "BUS:" + context.getEventBus()
					+ " EVENT: " + context.getEvent() + " SUBSRIBER: " + context.getSubscriber() + " METHOD: "
					+ context.getSubscriberMethod());
			exception.printStackTrace();
		});

		IMainModel mainModel = new MainModel(activeShell, eventBus);

		mainController = new MainController(mainModel);
		mainController.startUserMonitoring();
	}

	public IMainController getMainController() {
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

		mainController.dispose();

		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
