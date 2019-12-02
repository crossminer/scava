/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.focus.codesnippet;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class FocusCodeSnippetRecommendationView extends ViewPartView<IFocusCodeSnippetRecommendationViewEventListener> {

	public static final String ID = "org.eclipse.scava.plugin.focus.codesnippetrecommendation.FocusRecommendation"; //$NON-NLS-1$
	private Composite defaultMessageComposite;
	private Composite resultsComposite;
	private Composite errorMessageComposite;
	private Label lblErrorMessage;
	private Composite loadingComposite;
	private Label lblLoading;
	private FocusCodeSnippetViewContentComposite focusCodeSnippetContent;

	public FocusCodeSnippetRecommendationView() {
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/features/crossminer_focus_code_snippet_recommendation_16x16.png"));
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

		loadingComposite = new Composite(getComposite(), SWT.NONE);
		loadingComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		loadingComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		loadingComposite.setLayout(new GridLayout(1, false));

		lblLoading = new Label(loadingComposite, SWT.NONE);
		lblLoading.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblLoading.setText("Loading results...");

		errorMessageComposite = new Composite(getComposite(), SWT.NONE);
		errorMessageComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		errorMessageComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		errorMessageComposite.setLayout(new GridLayout(1, false));

		lblErrorMessage = new Label(errorMessageComposite, SWT.NONE);
		lblErrorMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblErrorMessage.setText("Error messages will be shown here.");

		defaultMessageComposite = new Composite(getComposite(), SWT.NONE);
		defaultMessageComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		defaultMessageComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		defaultMessageComposite.setLayout(new GridLayout(1, false));

		Label lblDefaultMessage = new Label(defaultMessageComposite, SWT.NONE);
		lblDefaultMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDefaultMessage.setText("Results of FOCUS Code Snippet recommendation will be shown here.");

		layout.topControl = defaultMessageComposite;

		resultsComposite = new Composite(getComposite(), SWT.NONE);
		resultsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		focusCodeSnippetContent = new FocusCodeSnippetViewContentComposite(resultsComposite, SWT.NONE);

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

	public void showErrorMessage(String message) {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = errorMessageComposite;

		lblErrorMessage.setText(message);
		getComposite().requestLayout();

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});
	}

	public void showLoading() {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = loadingComposite;

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});
	}

	public void showCodeSnippets(List<String> snippets) {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = resultsComposite;

		focusCodeSnippetContent.showCodeSnippetList(snippets);

		Display.getDefault().asyncExec(() -> {
			getComposite().requestLayout();
		});

	}
}
