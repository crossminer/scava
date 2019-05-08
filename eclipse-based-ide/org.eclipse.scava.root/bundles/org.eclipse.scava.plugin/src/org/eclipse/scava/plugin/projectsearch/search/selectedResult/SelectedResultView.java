/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.selectedResult;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class SelectedResultView extends CompositeView<ISelectedResultViewEventListener> {
	private Label lblName;
	private Button btnRemove;

	public SelectedResultView() {
		super(SWT.BORDER);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));

		lblName = new Label(this, SWT.WRAP);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblName.setText(
				"Project name comes here, it can be a really really long string because developers give long name for their children");
						new Label(this, SWT.NONE);
				
						btnRemove = new Button(this, SWT.NONE);
						btnRemove.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
						btnRemove.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								eventManager.invoke(l -> l.onRemove());
							}
						});
						btnRemove.setText("Remove");
						new Label(this, SWT.NONE);

		setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		addMouseListenerRecursively(this, new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				eventManager.invoke(l -> l.onOpen());
			}
			
		});
	}

	private void addMouseListenerRecursively(Control control, MouseListener listener) {
		if (control instanceof Button) {
			control.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_ARROW));
			return;
		}

		control.addMouseListener(listener);

		if (control instanceof Composite) {
			Composite composite = (Composite) control;
			for (Control child : composite.getChildren()) {
				addMouseListenerRecursively(child, listener);
			}
		}
	}
	
	@Override
	protected void checkSubclass() {
	}

	public void setName(String value) {
		lblName.setText(value);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
