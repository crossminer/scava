/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.ui.recommendationaccept;

import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.scava.commons.recommendation.Recommendation;
import org.eclipse.scava.plugin.event.notifier.INotifierEventSubscriber;
import org.eclipse.scava.plugin.event.notifier.NotifierEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.wb.swt.ResourceManager;

public class RecommendationAcceptDisplay extends TitleAreaDialog implements IDisposable {
	private final NotifierEvent recommendationsAccepted = new NotifierEvent();
	private final NotifierEvent recommendationsCancelled = new NotifierEvent();
	
	private List list;
	
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public RecommendationAcceptDisplay(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setBlockOnOpen(false);
		setShellStyle(SWT.SHELL_TRIM | SWT.BORDER | SWT.APPLICATION_MODAL);
		
		open();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Recommendation accept");
		
		newShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event e) {
				System.out.println("RecAccClose event");
				e.doit = false;
				recommendationsCancelled.invoke();
			}
		});
	}
	
	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Accept recommendations to apply them");
		setTitleImage(ResourceManager.getPluginImage("org.scava.plugin", "icons/SCAVA-logo-small.png"));
		setTitle("List of suggested recommendations");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		list = new List(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		
		return area;
	}
	
	public INotifierEventSubscriber getRecommendationsAccepted() {
		return recommendationsAccepted;
	}
	
	public INotifierEventSubscriber getRecommendationsCancelled() {
		return recommendationsCancelled;
	}
	
	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Accept", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(662, 456);
	}
	
	public void showRecommendations(java.util.List<Recommendation> recommendations) {
		String[] recommendationStrings = recommendations.stream().map(r -> r.toString()).collect(Collectors.toList())
				.toArray(new String[0]);
		list.setItems(recommendationStrings);
	}
	
	@Override
	protected void okPressed() {
		recommendationsAccepted.invoke();
	}
	
	@Override
	protected void cancelPressed() {
		recommendationsCancelled.invoke();
	}
	
	@Override
	public boolean close() {
		return false;
	}
	
	@Override
	public void dispose() {
		recommendationsAccepted.dispose();
		recommendationsCancelled.dispose();
		super.close();
	}
}
