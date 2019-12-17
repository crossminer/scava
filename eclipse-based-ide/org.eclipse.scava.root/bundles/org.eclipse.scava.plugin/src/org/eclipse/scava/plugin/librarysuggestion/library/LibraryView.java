/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.librarysuggestion.library;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public class LibraryView extends CompositeView<ILibraryViewEventListener> {
	private Label lblArtifactId;
	private Label lblGroupId;
	private Label lblVersion;
	private CLabel lblUseForSearch;
	private CLabel lblDoNotUseForSearch;
	private CLabel lblInstall;
	private CLabel lblDoNotInstall;
	private Label lblMaven;
	private Label lblGroupId_1;
	private Label lblVersion_1;
	private Composite composite;
	private CLabel lblFeedback;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LibraryView() {
		super(SWT.BORDER);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gridLayout = new GridLayout(8, false);
		gridLayout.verticalSpacing = 4;
		setLayout(gridLayout);

		lblMaven = new Label(this, SWT.NONE);
		lblMaven.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/sites/maven_icon_40x40.png"));

		composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));

		lblArtifactId = new Label(composite, SWT.WRAP);
		lblArtifactId.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblArtifactId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onOpenInBrowser());
			}
		});
		lblArtifactId.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblArtifactId.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblArtifactId.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblArtifactId.setText("Artifact ID");

		lblGroupId_1 = new Label(composite, SWT.NONE);
		lblGroupId_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblGroupId_1.setText("Group ID:");

		lblGroupId = new Label(composite, SWT.WRAP);
		lblGroupId.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblGroupId.setText("Group ID");

		lblVersion_1 = new Label(composite, SWT.NONE);
		lblVersion_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblVersion_1.setText("Version:");

		lblVersion = new Label(composite, SWT.WRAP);
		lblVersion.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblVersion.setText("1.24.53.rev-23");
		new Label(this, SWT.NONE);

		lblUseForSearch = new CLabel(this, SWT.NONE);
		GridData gd_lblUseForSearch = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblUseForSearch.verticalIndent = 1;
		lblUseForSearch.setLayoutData(gd_lblUseForSearch);
		lblUseForSearch.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblUseForSearch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onUseForSearch());
			}

		});
		lblUseForSearch.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/use_for_search_32x32.png"));
		lblUseForSearch.setText("");

		lblInstall = new CLabel(this, SWT.NONE);
		lblInstall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onPickForInstall());
			}
		});
		GridData gd_lblInstall = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblInstall.verticalIndent = 1;
		lblInstall.setLayoutData(gd_lblInstall);
		lblInstall.setText("");
		lblInstall.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/install_icon_32x32.png"));
		lblInstall.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));

		lblDoNotUseForSearch = new CLabel(this, SWT.NONE);
		GridData gd_lblDoNotUseForSearch = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDoNotUseForSearch.verticalIndent = 1;
		lblDoNotUseForSearch.setLayoutData(gd_lblDoNotUseForSearch);
		lblDoNotUseForSearch.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		lblDoNotUseForSearch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onDoNotUseForSearch());
			}

		});
		lblDoNotUseForSearch.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin",
				"icons/control/use_not_for_search_32x32.png"));
		lblDoNotUseForSearch.setText("");

		lblDoNotInstall = new CLabel(this, SWT.NONE);
		lblDoNotInstall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onUnpickForInstall());
			}
		});
		GridData gd_lblDoNotInstall = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblDoNotInstall.verticalIndent = 1;
		lblDoNotInstall.setLayoutData(gd_lblDoNotInstall);
		lblDoNotInstall.setText("");
		lblDoNotInstall.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/install_not_icon_32x32.png"));
		lblDoNotInstall.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));

		GridData gd_lblFeedback = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblFeedback.verticalIndent = 1;

		lblFeedback = new CLabel(this, SWT.NONE);
		lblFeedback.setLayoutData(gd_lblFeedback);
		lblFeedback.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				eventManager.invoke(l -> l.onLeaveFeedback());
			}
		});

		lblFeedback.setToolTipText("Send feedback");
		lblFeedback.setImage(
				ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/control/feedbackIcon_32_32.png"));
		lblFeedback.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void setArtifactId(String value) {
		lblArtifactId.setText(value);
	}

	public void setGroupId(String value) {
		lblGroupId.setText(value);
	}

	public void setVersion(String value) {
		lblVersion.setText(value);
	}

	private void setEnableUseForSearch(boolean value) {
		GridData layoutData = (GridData) lblUseForSearch.getLayoutData();
		layoutData.exclude = !value;
	}

	private void setEnableDoNotUseForSearch(boolean value) {
		GridData layoutData = (GridData) lblDoNotUseForSearch.getLayoutData();
		layoutData.exclude = !value;
	}

	private void setEnablePickForInstall(boolean value) {
		GridData layoutData = (GridData) lblInstall.getLayoutData();
		layoutData.exclude = !value;
	}

	private void setEnableUnpickForInstall(boolean value) {
		GridData layoutData = (GridData) lblDoNotInstall.getLayoutData();
		layoutData.exclude = !value;
	}

	private void setEnableFeedback(boolean value) {
		GridData layoutData = (GridData) lblFeedback.getLayoutData();
		layoutData.exclude = !value;
	}

	private void setHighlight(Color color) {
		setBackground(color);
	}

	public void setLibraryType(LibraryType type) {
		switch (type) {
		case PickedForInstall:
			setEnableDoNotUseForSearch(false);
			setEnablePickForInstall(false);
			setEnableUnpickForInstall(true);
			setEnableFeedback(true);
			setEnableUseForSearch(false);
			setHighlight(SWTResourceManager.getColor(217, 234, 242));
			break;
		case Suggested:
			setEnableDoNotUseForSearch(false);
			setEnablePickForInstall(true);
			setEnableUnpickForInstall(false);
			setEnableFeedback(true);
			setEnableUseForSearch(false);
			setHighlight(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			break;
		case UsedForSearch:
			setEnableDoNotUseForSearch(true);
			setEnablePickForInstall(false);
			setEnableUnpickForInstall(false);
			setEnableFeedback(false);
			setEnableUseForSearch(false);
			setHighlight(SWTResourceManager.getColor(217, 234, 242));
			break;
		case UsedInProject:
			setEnableDoNotUseForSearch(false);
			setEnablePickForInstall(false);
			setEnableUnpickForInstall(false);
			setEnableFeedback(false);
			setEnableUseForSearch(true);
			setHighlight(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			break;
		default:
			break;
		}
	}
}
