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
import org.eclipse.scava.plugin.apidocumentation.result.ApiDocumentationResultView;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApiDocumentationView extends ViewPartView<IApiDocumentationViewEventListener> {

	public static final String ID = "org.eclipse.scava.plugin.apidocumentation.ApiDocumentation"; //$NON-NLS-1$
	private VerticalList resultsVerticalList;
	private ScrolledComposite resultsScrolledComposite;
	private Composite defaultMessageComposite;

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

		resultsScrolledComposite = new ScrolledComposite(getComposite(), SWT.BORDER | SWT.V_SCROLL);
		resultsScrolledComposite.setAlwaysShowScrollBars(true);
		resultsScrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		resultsScrolledComposite.setExpandHorizontal(true);
		resultsScrolledComposite.setExpandVertical(true);

		resultsVerticalList = new VerticalList(resultsScrolledComposite, SWT.NONE);
		resultsVerticalList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		resultsScrolledComposite.setContent(resultsVerticalList);
		resultsScrolledComposite.setMinSize(resultsVerticalList.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		layout.topControl = defaultMessageComposite;

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

	public void showResult(ApiDocumentationResultView view) {
		Composite composite = view.getComposite();

		resultsVerticalList.add(composite);

		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = resultsScrolledComposite;

		view.getComposite().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (resultsVerticalList.getChildren().length == 0)
					layout.topControl = defaultMessageComposite;
			}
		});
	}
}
