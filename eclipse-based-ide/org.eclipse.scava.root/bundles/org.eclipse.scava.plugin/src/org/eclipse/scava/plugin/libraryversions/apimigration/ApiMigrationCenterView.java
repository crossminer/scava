/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationView;
import org.eclipse.scava.plugin.mvc.view.IView;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApiMigrationCenterView extends ViewPartView<IApiMigrationCenterViewEventListener> {

	public static final String ID = "org.eclipse.scava.plugin.apimigration.ApiMigration"; //$NON-NLS-1$
	private Composite defaultMessageComposite;
	private CTabFolder tabFolder;
	private Composite migrationsComposite;

	public ApiMigrationCenterView() {
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/features/crossminer_code_api_recommend_16x16.png"));
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		super.createPartControl(container);

		StackLayout layout = new StackLayout();
		getComposite().setLayout(layout);

		defaultMessageComposite = new Composite(getComposite(), SWT.NONE);
		defaultMessageComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		defaultMessageComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		defaultMessageComposite.setLayout(new GridLayout(1, false));

		Label lblDefaultMessage = new Label(defaultMessageComposite, SWT.NONE);
		lblDefaultMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDefaultMessage.setText("Results of API Migration will be shown here.");

		layout.topControl = defaultMessageComposite;

		migrationsComposite = new Composite(getComposite(), SWT.NONE);
		migrationsComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		migrationsComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		migrationsComposite.setLayout(new GridLayout(1, false));

		tabFolder = new CTabFolder(migrationsComposite, SWT.BORDER | SWT.CLOSE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	public void showMigration(ApiMigrationView view, String title) {
		Composite composite = view.getComposite();

		CTabItem tab = new CTabItem(tabFolder, SWT.NONE);
		tab.setText(title);

		composite.setParent(tabFolder);
		tab.setControl(composite);

		tabFolder.setSelection(tab);

		view.setTabReference(tab);

		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = migrationsComposite;

		tab.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (tabFolder.getItemCount() == 0) {
					layout.topControl = defaultMessageComposite;
				}
			}
		});

		getComposite().requestLayout();
	}

	public void focusOnMigration(IView<?> view) {
		CTabItem[] items = tabFolder.getItems();
		for (int i = 0; i < items.length; i++) {
			CTabItem tab = items[i];
			if (tab.getControl() == view) {
				tabFolder.setSelection(tab);
			}
		}
	}
}
