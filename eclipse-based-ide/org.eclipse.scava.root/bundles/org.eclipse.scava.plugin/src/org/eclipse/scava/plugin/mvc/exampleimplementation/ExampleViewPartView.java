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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.scava.plugin.mvc.view.ViewPartView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ExampleViewPartView extends ViewPartView<IExampleViewEventListener> implements IExampleView {

	public static final String ID = "org.eclipse.scava.plugin.newmvc.exampleimplementation.ExampleViewPartView"; //$NON-NLS-1$
	private Text text;
	private Label lblTarget;

	public ExampleViewPartView() {

	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		{
			Label lblOpen = new Label(container, SWT.NONE);
			lblOpen.setText("Open:");
		}
		{
			Button btnNewViewpart = new Button(container, SWT.NONE);
			btnNewViewpart.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					eventManager.invoke(l -> l.onNewViewPartView());
				}
			});
			btnNewViewpart.setText("New ViewPart");
		}
		{
			Button btnNewTitleareadialog = new Button(container, SWT.NONE);
			btnNewTitleareadialog.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					eventManager.invoke(l -> l.onNewTitleAreaDialogView());
				}
			});
			btnNewTitleareadialog.setText("New TitleAreaDialog");
		}
		new Label(container, SWT.NONE);
		{
			Label lblEvent = new Label(container, SWT.NONE);
			lblEvent.setText("Event:");
		}
		{
			Button btnToparent = new Button(container, SWT.NONE);
			btnToparent.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					eventManager.invoke(l -> l.onRaiseEventToParentRequest());
				}
			});
			btnToparent.setText("to ParentCotnroller");
		}
		{
			Button btnToSubcontroller = new Button(container, SWT.NONE);
			btnToSubcontroller.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					eventManager.invoke(l -> l.onRaiseEventToSubControllersRequest());
				}
			});
			btnToSubcontroller.setText("to SubController");
		}
		new Label(container, SWT.NONE);
		{
			Label lblActions = new Label(container, SWT.NONE);
			lblActions.setText("Actions:");
		}
		{
			Button btnRequestTime = new Button(container, SWT.NONE);
			btnRequestTime.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					eventManager.invoke(l -> l.onTimeRequest());
				}
			});
			btnRequestTime.setText("Request Time");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			text = new Text(container, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		}
		{
			Button btnProcessText = new Button(container, SWT.NONE);
			btnProcessText.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					eventManager.invoke(l -> l.onProcessTextRequest(text.getText()));
				}
			});
			btnProcessText.setText("Process text");
		}
		{
			Label lblResult = new Label(container, SWT.NONE);
			lblResult.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
			lblResult.setText("Result:");
		}
		{
			lblTarget = new Label(container, SWT.NONE);
			lblTarget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
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

	@Override
	public void Show(String result) {
		lblTarget.setText(result);
	}

}
