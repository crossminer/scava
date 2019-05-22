/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.searchresult;

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

public class SearchResultView extends CompositeView<ISearchResultViewEventListener> {
	private Label lblYearValue;
	private Label lblActiveValue;
	private Label lblDependenciesValue;
	private Label lblStarredValue;
	private Label lblName;
	private Button btnAdd;
	private Composite infosComposite;

	public SearchResultView() {
		super(SWT.BORDER);
		setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));

		lblName = new Label(this, SWT.WRAP);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblName.setText(
				"Project name comes here, it can be a really really long string because developers give long name for their children");
		
		infosComposite = new Composite(this, SWT.NONE);
		GridLayout gl_infosComposite = new GridLayout(2, false);
		gl_infosComposite.verticalSpacing = 1;
		gl_infosComposite.marginWidth = 0;
		infosComposite.setLayout(gl_infosComposite);
		infosComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		
				Label lblStarred = new Label(infosComposite, SWT.NONE);
				lblStarred.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
				lblStarred.setSize(386, 12);
				lblStarred.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
				lblStarred.setText("Starred:");
				
						lblStarredValue = new Label(infosComposite, SWT.NONE);
						lblStarredValue.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
						lblStarredValue.setSize(0, 12);
						lblStarredValue.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
						
								Label lblDependencies = new Label(infosComposite, SWT.NONE);
								lblDependencies.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
								lblDependencies.setSize(386, 12);
								lblDependencies.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
								lblDependencies.setText("Dependencies:");
								
										lblDependenciesValue = new Label(infosComposite, SWT.NONE);
										lblDependenciesValue.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
										lblDependenciesValue.setSize(0, 12);
										lblDependenciesValue.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
										
												Label lblActive = new Label(infosComposite, SWT.NONE);
												lblActive.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
												lblActive.setSize(386, 12);
												lblActive.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
												lblActive.setText("Active:");
												
														lblActiveValue = new Label(infosComposite, SWT.NONE);
														lblActiveValue.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
														lblActiveValue.setSize(0, 12);
														lblActiveValue.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
														
																Label lblYear = new Label(infosComposite, SWT.NONE);
																lblYear.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
																lblYear.setSize(386, 12);
																lblYear.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
																lblYear.setText("Year:");
																
																		lblYearValue = new Label(infosComposite, SWT.NONE);
																		lblYearValue.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
																		lblYearValue.setSize(0, 12);
																		lblYearValue.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		
				btnAdd = new Button(this, SWT.NONE);
				btnAdd.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_ARROW));
				btnAdd.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
				btnAdd.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						eventManager.invoke(l -> l.onAdd());
					}
				});
				btnAdd.setText("Add");
			
		addMouseListenerRecursively(this, new MouseAdapter() {
			 @Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onOpen());
			}
		});
	}
	
	private void addMouseListenerRecursively(Control control, MouseListener listener) {
		if (control instanceof Button) {
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

	public void setStarred(int value) {
		lblStarredValue.setText(Integer.toString(value));
	}

	public void setDependencies(int value) {
		lblDependenciesValue.setText(Integer.toString(value));
	}

	public void setActive(boolean value) {
		lblActiveValue.setText(value ? "yes" : "no");
	}

	public void setYear(int value) {
		lblYearValue.setText(Integer.toString(value));
	}
	
	public void setAvailable(boolean value) {
		btnAdd.setEnabled(value);
	}
}
