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

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.scava.plugin.main.MainController;
import org.eclipse.scava.plugin.main.MainModel;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.common.eventbus.EventBus;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "org.eclipse.scava.plugin";

	private static Activator plugin;

	private MainController mainController;
	private EventBus eventBus;

	/**
	 * The constructor
	 */
	public Activator() {
		
		eventBus = new EventBus((exception, context) -> {
			System.out.println("EventBusSubscriberException: " + exception + " | " + "BUS:" + context.getEventBus()
					+ " EVENT: " + context.getEvent() + " SUBSRIBER: " + context.getSubscriber() + " METHOD: "
					+ context.getSubscriberMethod());
			exception.printStackTrace();
		});
		
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

	public EventBus getEventBus() {
		return eventBus;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);

		plugin = this;

		startMainMVC();
	}

	private void startMainMVC() {
		MainModel model = new MainModel();
		mainController = new MainController(
				null, model, eventBus);
		mainController.init();
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;

		mainController.dispose();

		super.stop(context);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {

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
