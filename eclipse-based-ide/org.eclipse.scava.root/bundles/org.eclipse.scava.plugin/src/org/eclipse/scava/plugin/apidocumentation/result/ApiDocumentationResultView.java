/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.apidocumentation.result;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApiDocumentationResultView extends CompositeView<IApiDocumentationResultViewEventListener> {
	private CLabel lblLabel;
	private Text textUrl;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ApiDocumentationResultView() {
		super(SWT.BORDER);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		setLayout(gridLayout);

		lblLabel = new CLabel(this, SWT.NONE);
		lblLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblLabel.setText("<Title of the API documentation>");
		new Label(this, SWT.NONE);

		textUrl = new Text(this, SWT.READ_ONLY | SWT.WRAP);
		textUrl.setEditable(false);
		textUrl.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		textUrl.setText("<url>");
		textUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void showApiDocumentation(ApiDocumentation apiDocumentation) {
		lblLabel.setText(apiDocumentation.getLabel());
		lblLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onOpenUrl());
			}
		});
		lblLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblLabel.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));

		textUrl.setText(apiDocumentation.getUrl());
		/*
		 * textUrl.addMouseListener(new MouseAdapter() { public void
		 * mouseDown(MouseEvent e) { textUrl.selectAll(); }; });
		 */
	}
}
