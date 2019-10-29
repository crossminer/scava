/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.main.page.PageController;
import org.eclipse.scava.plugin.main.page.PageModel;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.usermonitoring.UserMonitor;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class MainController extends ModelController<MainModel> {

	private Map<IWorkbenchPage, PageController> pageControllers = new HashMap<>();
	private EventBus eventBus;
	private UserMonitor userMonitor;

	public MainController(Controller parent, MainModel model, EventBus eventBus) {
		super(parent, model);

		this.eventBus = eventBus;
		eventBus.register(this);
	}

	@Override
	public void init() {
		super.init();

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

		if (preferenceStore.getBoolean(Preferences.USERMONITORING_ENABLED)) {
			System.out.println("Usermonitoring is enabled.");
			userMonitor = new UserMonitor(eventBus);
		} else {
			System.out.println("Usermonitoring is disabled.");
		}

		openHelpMenu();

	}

	private void openHelpMenu() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Display.getDefault().syncExec(() -> {
					IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

					if (preferenceStore.getBoolean(Preferences.HELP_MENU_OPEN)) {
						MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(
								Display.getDefault().getActiveShell(), "Help menu",
								"Would you like to open the help menu?", "Do not show this message again", false,
								preferenceStore, "");

						preferenceStore.setValue(Preferences.HELP_MENU_OPEN, !dialog.getToggleState());

						int yes = 2;
						if (dialog.getReturnCode() == yes) {
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									PlatformUI.getWorkbench().getHelpSystem().displayHelp();
								}
							});
						}
					}
				});
			}
		}, 2000);
	}

	private PageController getOrCreatePageController(IWorkbenchPage page) {
		PageController controller = pageControllers.get(page);

		if (controller == null || controller.isDisposed()) {
			PageModel model = new PageModel(page);
			controller = new PageController(this, model);

			pageControllers.put(page, controller);

			controller.init();
		}

		return controller;
	}

	@Subscribe
	public void onEclipseInterfaceEvents(EclipseInterfaceEvent interfaceEvent) {
		Display.getDefault().asyncExec(() -> {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			getOrCreatePageController(page);

			try {
				modifyEventSource(interfaceEvent);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} finally {
				routeEventToSubControllers(interfaceEvent);
			}
		});
	}

	private void modifyEventSource(EclipseInterfaceEvent interfaceEvent)
			throws NoSuchFieldException, IllegalAccessException {
		Field sourceField = RoutedEvent.class.getDeclaredField("source");
		sourceField.setAccessible(true);
		sourceField.set(interfaceEvent, this);
		sourceField.setAccessible(false);
	}

	@Override
	protected void onReceiveRoutedEventFromSubController(IRoutedEvent routedEvent, Controller forwarderController) {

		if (routedEvent instanceof OpenInExternalBrowserRequestEvent) {
			OpenInExternalBrowserRequestEvent event = (OpenInExternalBrowserRequestEvent) routedEvent;

			openUrlInExternalBrowser(event.getUrl());

			return;
		}

		super.onReceiveRoutedEventFromSubController(routedEvent, forwarderController);
	}

	private void openUrlInExternalBrowser(String url) {
		try {
			PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(url));
		} catch (MalformedURLException e) {
			boolean canCopy = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Error",
					"We could not open the requested url.\nThe following will be copied to your clipboard:\n" + url);
			if (canCopy) {
				Clipboard clipboard = new Clipboard(Display.getCurrent());
				clipboard.setContents(new Object[] { url }, new Transfer[] { TextTransfer.getInstance() });
				clipboard.dispose();
			}
		} catch (PartInitException e) {
			e.printStackTrace();
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
					"Could not open external browser. Please check the preferences.");
		}
	}

	@Override
	protected void disposeController() {
		pageControllers.clear();

		eventBus.unregister(this);

		super.disposeController();
	}

	public UserMonitor getUserMonitor() {
		return userMonitor;
	}
}
