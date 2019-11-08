/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.scava.plugin.mvc.view.IView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApiMigrationResultView extends CompositeView<IApiMigrationResultViewEventListener> {
	private Composite loadingComposite;
	private Composite failedComposite;
	private Composite resultsComposite;
	private Label lblPleaseTryAgain;
	private Label lblFailMessage;
	private Label lblLoading;
	private Composite messageComposite;
	private Label lblMessage;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ApiMigrationResultView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new StackLayout());

		loadingComposite = new Composite(this, SWT.NONE);
		loadingComposite.setLayout(new GridLayout(1, false));

		lblLoading = new Label(loadingComposite, SWT.NONE);
		lblLoading.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblLoading.setText("Loading results...\nThis may take a while. Please be patient.");

		failedComposite = new Composite(this, SWT.NONE);
		failedComposite.setLayout(new GridLayout(2, false));

		Label lblFailed = new Label(failedComposite, SWT.NONE);
		lblFailed.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		lblFailed.setText("Loading results failed.");

		lblFailMessage = new Label(failedComposite, SWT.WRAP);
		lblFailMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		lblPleaseTryAgain = new Label(failedComposite, SWT.NONE);
		lblPleaseTryAgain.setText("Please try again later.");

		Button btnNewButton = new Button(failedComposite, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onTryAgainLoadResults());
			}
		});
		btnNewButton.setText("Load results");

		resultsComposite = new Composite(this, SWT.NONE);
		resultsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		messageComposite = new Composite(this, SWT.NONE);
		messageComposite.setLayout(new GridLayout(1, false));

		lblMessage = new Label(messageComposite, SWT.WRAP);
		lblMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblMessage.setText("New Label");

	}

	@Override
	protected void checkSubclass() {

	}

	public void showLoadingResults() {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = loadingComposite;

		requestLayout();
	}

	public void showLoadingFailed(String message) {
		lblFailMessage.setText(message);
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = failedComposite;

		requestLayout();
	}

	public void showResults(IView<?> results) {
		Composite composite = results.getComposite();

		composite.setParent(resultsComposite);

		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = resultsComposite;

		requestLayout();
	}

	public void showMessage(String message) {
		StackLayout layout = (StackLayout) getComposite().getLayout();
		layout.topControl = messageComposite;

		lblMessage.setText(message);

		requestLayout();
	}
}
