/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.scava.plugin.webreferenceviewer.reference.WebReferenceView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class WebReferenceViewerView extends CompositeView<IWebReferenceViewerViewEventListener> {
	private ScrolledComposite referencesScrolledComposite;
	private Composite referencesComposite;
	private VerticalList referencesVerticalList;
	private Composite defaultMessageComposite;
	private Label lblDefaultMessage;
	private Composite errorComposite;
	private Label lblError;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public WebReferenceViewerView() {
		super(SWT.NONE);
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Display.getDefault().asyncExec(referencesVerticalList::refreshLayout);
			}
		});
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new StackLayout());

		defaultMessageComposite = new Composite(this, SWT.NONE);
		defaultMessageComposite.setLayout(new GridLayout(1, false));

		lblDefaultMessage = new Label(defaultMessageComposite, SWT.WRAP);
		lblDefaultMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDefaultMessage.setText("Web references will be shown here.");

		referencesComposite = new Composite(this, SWT.NONE);
		GridLayout gl_referencesComposite = new GridLayout(1, false);
		gl_referencesComposite.marginWidth = 0;
		gl_referencesComposite.marginHeight = 0;
		referencesComposite.setLayout(gl_referencesComposite);

		referencesScrolledComposite = new ScrolledComposite(referencesComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		referencesScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		referencesScrolledComposite.setExpandHorizontal(true);
		referencesScrolledComposite.setExpandVertical(true);

		referencesVerticalList = new VerticalList(referencesScrolledComposite, SWT.NONE);
		referencesVerticalList.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		referencesScrolledComposite.setContent(referencesVerticalList);
		referencesScrolledComposite.setMinSize(referencesVerticalList.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		errorComposite = new Composite(this, SWT.NONE);
		errorComposite.setLayout(new GridLayout(1, false));

		lblError = new Label(errorComposite, SWT.WRAP);
		lblError.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblError.setText("New Label");
	}

	@Override
	protected void checkSubclass() {

	}

	public void showReference(WebReferenceView view) {
		Composite composite = view.getComposite();

		referencesVerticalList.add(composite);

		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = referencesComposite;

		view.getComposite().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (referencesVerticalList.getChildren().length == 0)
					layout.topControl = defaultMessageComposite;
			}
		});

		Display.getDefault().asyncExec(referencesVerticalList::refreshLayout);
	}

	public void setDefaultMessage(String message) {
		lblDefaultMessage.setText(message);
	}

	public void showError(String errorMessage) {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = errorComposite;

		lblError.setText(errorMessage);

		requestLayout();
	}
}
