/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration;

import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.scava.plugin.mvc.view.IView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class ApiMigrationView extends CompositeView<IApiMigrationViewEventListener> {
	private CTabItem tbtmApiDocumentationAnd;
	private CTabItem tbtmRecommendations;
	private CTabFolder tabFolder;
	private Label lblMigratingFromVersion;

	private CTabItem tabReference;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ApiMigrationView() {
		super(SWT.NONE);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(1, false));

		lblMigratingFromVersion = new Label(this, SWT.WRAP);
		lblMigratingFromVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblMigratingFromVersion
				.setText("Migrating <name of the library> from version <old version number> to <new version number>");

		tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		tbtmApiDocumentationAnd = new CTabItem(tabFolder, SWT.NONE);
		tbtmApiDocumentationAnd.setText("API documentation and Q&&A posts");

		tbtmRecommendations = new CTabItem(tabFolder, SWT.NONE);
		tbtmRecommendations.setText("Recommendations");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void showMigrationInfo(Library oldVersion, Library newVersion) {
		lblMigratingFromVersion.setText("Migrating library " + oldVersion.toMavenCoordWithoutVersion()
				+ " from version " + oldVersion.getVersion() + " to " + newVersion.getVersion());
	}

	public void showApiDocumentation(IView<?> view) {
		setContentOfTab(view, tbtmApiDocumentationAnd);
	}

	public void showDetections(IView<?> view) {
		setContentOfTab(view, tbtmRecommendations);
	}

	private void setContentOfTab(IView<?> view, CTabItem tab) {
		Composite composite = view.getComposite();
		composite.setParent(tabFolder);
		tab.setControl(composite);
		tabFolder.setSelection(tab);

		requestLayout();
	}

	public void focusOn(IView<?> view) {
		CTabItem[] items = tabFolder.getItems();
		for (int i = 0; i < items.length; i++) {
			CTabItem tab = items[i];
			if (tab.getControl() == view) {
				tabFolder.setSelection(tab);
			}
		}
	}

	public void setTabReference(CTabItem tab) {
		tab.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				eventManager.invoke(l -> l.requestViewClose());
			}
		});
	}
}
