/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.focus.codesnippet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class FocusCodeSnippetViewContentComposite extends Composite {
	private Text textCodeSnippetPreview;
	private List snippetList;
	private Composite previewDefaultComposite;
	private Composite previewCodeSnippetComposite;
	private Composite previewComposite;
	private Composite composite;
	private Label lblRecommendedCodeSnippets;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FocusCodeSnippetViewContentComposite(Composite parent, int style) {
		super(parent, style);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(this, SWT.NONE);

		composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		lblRecommendedCodeSnippets = new Label(composite, SWT.NONE);
		lblRecommendedCodeSnippets.setText("Recommended code snippets");

		snippetList = new List(composite, SWT.BORDER | SWT.V_SCROLL);
		snippetList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		snippetList.setSize(148, 300);
		snippetList.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (snippetList.getSelectionCount() > 0) {
					String selectedSnippet = snippetList.getSelection()[0];
					showSnippetInPreview(selectedSnippet);
				} else {
					showDefaultPreview();
				}
			}
		});

		previewComposite = new Composite(sashForm, SWT.NONE);
		previewComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		previewComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		previewComposite.setLayout(new StackLayout());

		previewDefaultComposite = new Composite(previewComposite, SWT.NONE);
		previewDefaultComposite.setLayout(new GridLayout(1, false));
		new Label(previewDefaultComposite, SWT.NONE);

		Label lblSelectARecomennded = new Label(previewDefaultComposite, SWT.WRAP | SWT.CENTER);
		lblSelectARecomennded.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSelectARecomennded.setText("Select a recommended code snippet to list here");
		lblSelectARecomennded.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));

		previewCodeSnippetComposite = new Composite(previewComposite, SWT.NONE);
		previewCodeSnippetComposite.setLayout(new GridLayout(1, false));

		Label lblPreviewOfThe = new Label(previewCodeSnippetComposite, SWT.NONE);
		lblPreviewOfThe.setText("Preview of the selected code snippet");

		Label label = new Label(previewCodeSnippetComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("New Label");

		textCodeSnippetPreview = new Text(previewCodeSnippetComposite,
				SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textCodeSnippetPreview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sashForm.setWeights(new int[] { 1, 1 });

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void showCodeSnippetList(java.util.List<String> snippets) {
		snippetList.setItems(snippets.toArray(new String[snippets.size()]));
		showDefaultPreview();
	}

	private void showSnippetInPreview(String snippet) {
		StackLayout layout = (StackLayout) previewComposite.getLayout();
		layout.topControl = previewCodeSnippetComposite;

		textCodeSnippetPreview.setText(snippet);

		Display.getDefault().asyncExec(() -> {
			previewComposite.requestLayout();
		});
	}

	private void showDefaultPreview() {
		StackLayout layout = (StackLayout) previewComposite.getLayout();
		layout.topControl = previewDefaultComposite;

		Display.getDefault().asyncExec(() -> {
			previewComposite.requestLayout();
		});
	}
}
