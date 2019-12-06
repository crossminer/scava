/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.scava.plugin.coderecommendation.preview.CodeRecommendationPreviewView;
import org.eclipse.scava.plugin.coderecommendation.results.CodeRecommendationResultsView;
import org.eclipse.scava.plugin.coderecommendation.results.ICodeRecommendationResultsViewEventListener;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class CodeRecommendationView extends ViewPartView<ICodeRecommendationResultsViewEventListener> {

	public static final String ID = "org.eclipse.scava.plugin.coderecommendation.CodeRecommendationView"; //$NON-NLS-1$
	private Composite resultsComposite;
	private Composite previewComposite;
	private Label lblSelectAPattern;

	public CodeRecommendationView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		Composite container = new Composite(parent, SWT.NONE);
		container.setBackgroundMode(SWT.INHERIT_FORCE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		container.setLayout(new GridLayout(1, false));
		{
			SashForm sashForm = new SashForm(container, SWT.NONE);
			sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			{
				resultsComposite = new Composite(sashForm, SWT.NONE);
				resultsComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				resultsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
				{
					Label lblInitiateACode = new Label(resultsComposite, SWT.NONE);
					lblInitiateACode.setText("Initiate a code recommendation request first, to see the results here");
				}
			}
			{
				previewComposite = new Composite(sashForm, SWT.NONE);
				StackLayout layout = new StackLayout();
				previewComposite.setLayout(layout);
				lblSelectAPattern = new Label(previewComposite, SWT.CENTER);
				lblSelectAPattern.setEnabled(false);
				lblSelectAPattern.setText("Select a pattern to open in preview");
				layout.topControl = lblSelectAPattern;
			}
			sashForm.setWeights(new int[] { 1, 1 });
		}

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

	public void showResults(CodeRecommendationResultsView view) {
		for (Control c : resultsComposite.getChildren().clone()) {
			c.dispose();
		}

		view.getComposite().setParent(resultsComposite);

		Display.getDefault().asyncExec(() -> {
			getComposite().pack();
			getComposite().requestLayout();
		});
	}

	public void showPreview(CodeRecommendationPreviewView view) {
		view.getComposite().setParent(previewComposite);

		StackLayout layout = (StackLayout) previewComposite.getLayout();
		layout.topControl = view.getComposite();

		view.getComposite().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				layout.topControl = lblSelectAPattern;
			}
		});

		Display.getDefault().syncExec(() -> {
			previewComposite.pack();
			previewComposite.requestLayout();
		});
	}
}
