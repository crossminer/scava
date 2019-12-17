/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer.reference;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class WebReferenceView extends CompositeView<IWebReferenceViewEventListener> {
	private Label lblTitle;
	private Text txtUrl;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public WebReferenceView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));

		lblTitle = new Label(this, SWT.WRAP);
		lblTitle.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblTitle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblTitle.setText("Title of web reference");

		lblTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onOpenUrl());
			}
		});

		Label lblFeedback = new Label(this, SWT.NONE);
		lblFeedback.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/feedbackIcon_32_32.png"));
		lblFeedback.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		lblFeedback.setToolTipText("Send feedback");
		lblFeedback.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblFeedback.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onLeaveFeedback());
			};
		});

		txtUrl = new Text(this, SWT.WRAP);
		txtUrl.setText("url");
		txtUrl.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
		new Label(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setTitle(String title) {
		lblTitle.setText(title);

		if (getParent() instanceof VerticalList) {
			VerticalList parent = (VerticalList) getParent();
			parent.refreshLayout();

		}
	}

	public void setUrl(String url) {
		txtUrl.setText(url);
	}
}
