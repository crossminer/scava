/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.preview;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class CodeRecommendationPreviewView extends CompositeView<ICodeRecommendationPreviewViewEventListener> {
	private Text txtSourceCode;
	private Label lblPatternName;
	private Button btnInsertAtCursor;
	private Label lblNewLabel;
	private Label lblFeedback;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CodeRecommendationPreviewView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 4;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		lblPatternName = new Label(this, SWT.WRAP);
		lblPatternName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblPatternName.setText("<name of the pattern>");

		btnInsertAtCursor = new Button(this, SWT.NONE);
		btnInsertAtCursor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onInsertAtCursor());
			}
		});
		btnInsertAtCursor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnInsertAtCursor.setText("Insert snippet at cursor");

		lblFeedback = new Label(this, SWT.NONE);
		lblFeedback.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/feedbackIcon_32_32.png"));
		lblFeedback.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onLeaveFeedback());
			}

		});
		lblFeedback.setToolTipText("Send feedback");
		lblFeedback.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));

		lblNewLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		lblNewLabel.setText("New Label");
		new Label(this, SWT.NONE);

		txtSourceCode = new Text(this, SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtSourceCode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		new Label(this, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setTitle(String value) {
		lblPatternName.setText(value);
	}

	public void setContent(String value) {
		txtSourceCode.setText(value);
	}

	public void setEnableInserAtCursor(boolean enabled) {
		btnInsertAtCursor.setEnabled(enabled);
	}

	public void setShowInsertAtCursor(boolean show) {
		GridData data = (GridData) btnInsertAtCursor.getLayoutData();
		data.exclude = !show;
	}
}
