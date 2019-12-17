/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.feedback;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.mvc.view.TitleAreaDialogView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class FeedbackView extends TitleAreaDialogView<IFeedbackViewEventListener> {
	private Text feedbackField;
	private StarRating rating;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public FeedbackView(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setToolTipText("");
		newShell.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/features/feedback_icon_16x16.png"));
		newShell.setText("Feedback");

		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	protected void okPressed() {
		eventManager.invoke(l -> l.onLeaveFeedback(feedbackField.getText(), rating.getSelection()));
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setTitleImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/logo-titleimage-titlearedialog.png"));
		setTitle("Feedback");
		setMessage("Send feedback");

		Composite container = (Composite) super.createDialogArea(parent);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.verticalSpacing = 0;
		gl_composite.marginRight = 5;
		gl_composite.marginLeft = 5;
		gl_composite.marginHeight = 10;
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);

		Label lblRateTheRecommended = new Label(composite, SWT.NONE);
		lblRateTheRecommended.setText("Rate the recommended element:");

		rating = new StarRating(composite, SWT.NONE);
		rating.setToolTipText("");
		rating.setAlpha(100);
		rating.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridData gd_rating = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_rating.verticalIndent = 1;
		rating.setLayoutData(gd_rating);

		rating.setNrOfStars(5);
		rating.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/ratingstar_32x32.png"));
		rating.setVertical(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label lblWriteDownYour = new Label(composite, SWT.NONE);
		lblWriteDownYour.setText("Write down your thoughts about it:");
		new Label(composite, SWT.NONE);

		feedbackField = new Text(composite,
				SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		feedbackField.setTextLimit(512);
		feedbackField.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		feedbackField.setMessage("Please share your experiences");
		feedbackField.setToolTipText("");
		feedbackField.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		feedbackField.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		feedbackField.setFocus();

		GridData gd_txtAsdasdasd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_txtAsdasdasd.verticalIndent = 10;
		feedbackField.setLayoutData(gd_txtAsdasdasd);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label label = new Label(composite, SWT.HORIZONTAL);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_label.heightHint = 6;
		label.setLayoutData(gd_label);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		parent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(545, 444);
	}
}
