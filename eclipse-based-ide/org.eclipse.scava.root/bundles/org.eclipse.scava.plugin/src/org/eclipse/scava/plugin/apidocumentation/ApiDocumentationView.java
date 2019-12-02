/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.apidocumentation;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.scava.plugin.mvc.view.IView;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;

public class ApiDocumentationView extends ViewPartView<IApiDocumentationViewEventListener> {

	public static final String ID = "org.eclipse.scava.plugin.apidocumentation.ApiDocumentation"; //$NON-NLS-1$
	private Composite defaultMessageComposite;
	private Composite resultsComposite;

	public ApiDocumentationView() {
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

		Label lblNewLabel = new Label(defaultMessageComposite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setText("Results of API documentation and Q&&A posts will be shown here.");

		layout.topControl = defaultMessageComposite;

		resultsComposite = new Composite(getComposite(), SWT.NONE);
		resultsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

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

	public void showWebReferences(IView<?> view) {
		Composite composite = view.getComposite();

		composite.setParent(resultsComposite);

		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = resultsComposite;

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});

	}
}
