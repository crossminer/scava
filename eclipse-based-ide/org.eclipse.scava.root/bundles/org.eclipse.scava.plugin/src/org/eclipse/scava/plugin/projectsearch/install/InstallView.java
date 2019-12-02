/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.install;

import java.io.File;
import java.nio.file.Paths;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.scava.plugin.mvc.view.TitleAreaDialogView;
import org.eclipse.scava.plugin.projectsearch.install.installable.InstallableView;
import org.eclipse.scava.plugin.ui.scrolledcomposite.ScrolledComposites;
import org.eclipse.scava.plugin.ui.verticalList.VerticalList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;

public class InstallView extends TitleAreaDialogView<IInstallViewEventListener> {
	private VerticalList installablesList;
	private ScrolledComposite installablesScrolledComposite;
	private Group grpBasePath;
	private Button btnBrowse;
	private Label lblBasePath;
	private Group grpProjects;
	private Button btnFinish;
	private Button btnInstallAll;
	private Button btnBack;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public InstallView(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setBlockOnOpen(false);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitleImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/logo-titleimage-titlearedialog.png"));
		setMessage("You can install all the following projects at once to their specified destination location or manually one-by-one.");
		setTitle("Project install");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		container.setBackgroundMode(SWT.INHERIT_FORCE);
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		grpBasePath = new Group(container, SWT.NONE);
		grpBasePath.setLayout(new GridLayout(2, false));
		grpBasePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpBasePath.setText("Base path");

		lblBasePath = new Label(grpBasePath, SWT.WRAP);
		lblBasePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblBasePath.setText("C:/egyszer/volt/hol/nem/volt");

		btnBrowse = new Button(grpBasePath, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onBrowseBasePath();
			}
		});
		btnBrowse.setText("Change base path");
		
		grpProjects = new Group(container, SWT.NONE);
		grpProjects.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpProjects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpProjects.setText("Projects");

		installablesScrolledComposite = new ScrolledComposite(grpProjects, SWT.BORDER | SWT.V_SCROLL);
		installablesScrolledComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(installablesScrolledComposite);
			}
		});
		installablesScrolledComposite.setAlwaysShowScrollBars(true);
		installablesScrolledComposite.setExpandHorizontal(true);
		installablesScrolledComposite.setExpandVertical(true);

		installablesList = new VerticalList(installablesScrolledComposite, SWT.NONE);
		GridLayout gridLayout = (GridLayout) installablesList.getLayout();
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 10;
		gridLayout.verticalSpacing = 30;
		installablesList.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				ScrolledComposites.updateOnlyVerticalScrollableComposite(installablesScrolledComposite);
			}
		});
		installablesScrolledComposite.setContent(installablesList);
		installablesScrolledComposite.setMinSize(installablesList.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return area;
	}

	protected void onBrowseBasePath() {
		DirectoryDialog directoryDialog = new DirectoryDialog(getShell());

		File file = new File(lblBasePath.getText());
		while (file != null) {
			if (file.exists() && file.isDirectory()) {
				directoryDialog.setFilterPath(file.getAbsolutePath());
				break;
			}
			file = file.getParentFile();
		}

		String path = directoryDialog.open();
		if (path != null) {
			String normalizedPath = Paths.get(path).toString();
			eventManager.invoke(l -> l.onChangeBasePath(normalizedPath));
		}
	}

	public void setBasePath(String path) {
		lblBasePath.setText(path);
		getComposite().layout();
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnBack = createButton(parent, IDialogConstants.BACK_ID, "Back", false);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onBack());
			}
		});
		btnInstallAll = createButton(parent, IDialogConstants.OK_ID, "Install all", false);
		btnInstallAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onInstallAll());
			}
		});
		btnFinish = createButton(parent, IDialogConstants.CANCEL_ID, "Finish", false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setImage(ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/features/crossminer_project_search_16x16.png"));
		newShell.setToolTipText("");
		newShell.setText("Project search");

		newShell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(559, 509);
	}

	public void addInstallable(InstallableView view) {
		installablesList.add(view.getComposite());
		ScrolledComposites.updateOnlyVerticalScrollableComposite(installablesScrolledComposite);
	}
	
	public void setEnabledInstall(boolean enabled) {
		btnInstallAll.setEnabled(enabled);
	}
	
	public void setEnabledChangeBasePath(boolean enabled) {
		btnBrowse.setEnabled(enabled);
	}
	
	public void setEnabledClose(boolean enabled) {
		btnFinish.setEnabled(enabled);
		btnBack.setEnabled(enabled);
	}

	@Override
	protected void okPressed() {
		eventManager.invoke(l -> l.onInstallAll());
	}
	
}
