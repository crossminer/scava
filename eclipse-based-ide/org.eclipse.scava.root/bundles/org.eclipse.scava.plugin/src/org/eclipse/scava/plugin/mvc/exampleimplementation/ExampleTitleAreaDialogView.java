/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.mvc.exampleimplementation;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.mvc.view.TitleAreaDialogView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

public class ExampleTitleAreaDialogView extends TitleAreaDialogView<IExampleViewEventListener> implements IExampleView {
	private Text text;
	private Label lblTarget;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ExampleTitleAreaDialogView(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.SHELL_TRIM);
		setBlockOnOpen(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label lblOpen = new Label(container, SWT.NONE);
		lblOpen.setText("Open:");

		Button btnNewViewpart = new Button(container, SWT.NONE);
		btnNewViewpart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onNewViewPartView());
			}
		});
		btnNewViewpart.setText("new ViewPart");

		Button btnNewTitleareadialog = new Button(container, SWT.NONE);
		btnNewTitleareadialog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onNewTitleAreaDialogView());
			}
		});
		btnNewTitleareadialog.setText("new TitleAreaDialog");
		new Label(container, SWT.NONE);

		Label lblEvent = new Label(container, SWT.NONE);
		lblEvent.setText("Event:");

		Button btnToParentcontroller = new Button(container, SWT.NONE);
		btnToParentcontroller.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onRaiseEventToParentRequest());
			}
		});
		btnToParentcontroller.setText("to ParentController");

		Button btnToSubcontroller = new Button(container, SWT.NONE);
		btnToSubcontroller.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onRaiseEventToSubControllersRequest());
			}
		});
		btnToSubcontroller.setText("to SubControllers");
		new Label(container, SWT.NONE);

		Label lblAction = new Label(container, SWT.NONE);
		lblAction.setText("Action:");

		Button btnRequestTime = new Button(container, SWT.NONE);
		btnRequestTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onTimeRequest());
			}
		});
		btnRequestTime.setText("request Time");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Button btnProcessText = new Button(container, SWT.NONE);
		btnProcessText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onProcessTextRequest(text.getText()));
			}
		});
		btnProcessText.setText("process Text");

		Label lblResult = new Label(container, SWT.NONE);
		lblResult.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblResult.setText("Result:");

		lblTarget = new Label(container, SWT.NONE);
		lblTarget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 383);
	}

	@Override
	public void Show(String result) {
		lblTarget.setText(result);
	}
}
