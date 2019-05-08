/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.install.installable;

import java.io.File;
import java.util.Optional;

import org.eclipse.scava.plugin.mvc.view.CompositeView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import io.swagger.client.model.Artifact;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class InstallableView extends CompositeView<IInstallableViewEventListener> {
	private Text destinationDirectoryValue;
	private Label lblName;
	private Label lblGitCloneUrlValue;
	private Label lblBranchValue;
	private Button btnBrowse;
	private Button btnInstall;
	private Label lblMessage;
	private ProgressBar progressBar;
	private Button btnUseDefaultDestination;

	private String destinationProjectFolder = "";

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public InstallableView() {
		super(SWT.BORDER);
		setBackgroundMode(SWT.INHERIT_FORCE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);

		Composite header = new Composite(this, SWT.NONE);
		header.setBackground(SWTResourceManager.getColor(8, 117, 184));
		FillLayout fl_header = new FillLayout(SWT.HORIZONTAL);
		fl_header.marginWidth = 15;
		fl_header.marginHeight = 10;
		header.setLayout(fl_header);
		header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		lblName = new Label(header, SWT.WRAP);
		lblName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblName.setSize(388, 40);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblName.setText("Here comes the project name which can be really really long so it has to wrap");

		Composite body = new Composite(this, SWT.NONE);
		body.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		body.setLayout(new GridLayout(2, false));

		Group grpGitProperties = new Group(body, SWT.NONE);
		grpGitProperties.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		grpGitProperties.setSize(388, 59);
		grpGitProperties.setLayout(new GridLayout(2, false));
		grpGitProperties.setText("Git properties");

		Label lblGitCloneUrl = new Label(grpGitProperties, SWT.NONE);
		lblGitCloneUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblGitCloneUrl.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblGitCloneUrl.setText("Clone URL:");

		lblGitCloneUrlValue = new Label(grpGitProperties, SWT.NONE);
		lblGitCloneUrlValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblGitCloneUrlValue.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblGitCloneUrlValue.setText("<dynamic>");

		Label lblBranch = new Label(grpGitProperties, SWT.NONE);
		lblBranch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblBranch.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblBranch.setText("Master Branch:");

		lblBranchValue = new Label(grpGitProperties, SWT.NONE);
		lblBranchValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblBranchValue.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblBranchValue.setText("<dynamic>");

		Group grpDestinationLocation = new Group(body, SWT.NONE);
		grpDestinationLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		grpDestinationLocation.setSize(388, 53);
		grpDestinationLocation.setLayout(new GridLayout(2, false));
		grpDestinationLocation.setText("Destination location");

		btnUseDefaultDestination = new Button(grpDestinationLocation, SWT.CHECK);
		btnUseDefaultDestination.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				eventManager.invoke(l -> l.onUseCustomDestination(!btnUseDefaultDestination.getSelection()));
				setEnableDestiantionPathEditors(true);
			}
		});
		btnUseDefaultDestination.setSelection(true);
		btnUseDefaultDestination.setText("Use default destination location");
		new Label(grpDestinationLocation, SWT.NONE);

		destinationDirectoryValue = new Text(grpDestinationLocation, SWT.BORDER);
		destinationDirectoryValue.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				eventManager.invoke(l -> l.onDestinationChanged(destinationDirectoryValue.getText()));
			}
		});
		destinationDirectoryValue.setEnabled(false);
		destinationDirectoryValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnBrowse = new Button(grpDestinationLocation, SWT.NONE);
		btnBrowse.setEnabled(false);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onBrowse();
			}
		});
		btnBrowse.setText("Browse");

		lblMessage = new Label(body, SWT.WRAP);
		lblMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		lblMessage.setSize(0, 15);

		progressBar = new ProgressBar(body, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnInstall = new Button(body, SWT.NONE);
		btnInstall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnInstall.setEnabled(false);
				if (btnInstall.getText().equals("Install")) {
					eventManager.invoke(l -> l.onDestinationChanged(destinationDirectoryValue.getText()));
					eventManager.invoke(l -> l.onInstall());
				} else {
					eventManager.invoke(l -> l.onCancel());
				}
			}
		});
		GridData gd_btnInstall = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnInstall.widthHint = 73;
		btnInstall.setLayoutData(gd_btnInstall);
		btnInstall.setSize(43, 25);
		btnInstall.setText("Install");
	}

	private void onBrowse() {
		DirectoryDialog directoryDialog = new DirectoryDialog(getShell());

		File file = new File(destinationDirectoryValue.getText());
		while (file != null) {
			if (file.exists() && file.isDirectory()) {
				directoryDialog.setFilterPath(file.getAbsolutePath());
				break;
			}
			file = file.getParentFile();
		}

		String path = directoryDialog.open();
		if (path != null) {
			eventManager.invoke(l -> l.onDestinationChanged(path + File.separator + destinationProjectFolder));
		}
	}

	@Override
	protected void checkSubclass() {

	}

	public void setDestinationProjectFolder(String destinationProjectFolder) {
		this.destinationProjectFolder = destinationProjectFolder;
	}

	public void setProject(Artifact project) {
		lblName.setText(Optional.ofNullable(project.getFullName()).orElse("-"));
		lblGitCloneUrlValue.setText(Optional.ofNullable(project.getCloneUrl()).orElse("-"));
		lblBranchValue.setText(Optional.ofNullable(project.getMasterBranch()).orElse("-"));
	}

	public void setDestinationPath(String path) {
		Point selection = destinationDirectoryValue.getSelection();
		destinationDirectoryValue.setText(path);
		destinationDirectoryValue.setSelection(selection);
	}

	private void setEnableDestiantionPathEditors(boolean enabled) {
		btnUseDefaultDestination.setEnabled(enabled);

		boolean enabledCustom = enabled && !btnUseDefaultDestination.getSelection();
		destinationDirectoryValue.setEnabled(enabledCustom);
		btnBrowse.setEnabled(enabledCustom);
	}

	private void showMessage(String message, Color color) {
		lblMessage.setText(message);
		lblMessage.setForeground(color);

		if (getParent() != null) {
			getParent().layout();
		} else {
			layout();
		}
	}

	private void clearMessage() {
		showMessage("", SWTResourceManager.getColor(SWT.COLOR_BLACK));
	}

	public void readyToInstall() {
		clearMessage();

		btnInstall.setText("Install");
		btnInstall.setEnabled(true);

		setEnableDestiantionPathEditors(true);
	}

	public void notReadyToInstall(String message) {
		setInstallProgress("Cancelled", 0);
		showMessage(message, SWTResourceManager.getColor(SWT.COLOR_RED));

		btnInstall.setText("Install");
		btnInstall.setEnabled(false);

		setEnableDestiantionPathEditors(true);
	}

	public void installStarted() {
		btnInstall.setText("Cancel");
		btnInstall.setEnabled(true);

		setEnableDestiantionPathEditors(false);

		setInstallProgress("Starting...", 0);
	}

	public void setInstallProgress(String progress, int percent) {
		showMessage(progress, SWTResourceManager.getColor(SWT.COLOR_BLACK));
		progressBar.setSelection(percent);
	}

	public void installCancelled(String message) {
		setInstallProgress("Cancelled", 0);
		showMessage(message, SWTResourceManager.getColor(SWT.COLOR_RED));

		btnInstall.setText("Install");
		btnInstall.setEnabled(true);

		setEnableDestiantionPathEditors(true);
	}

	public void installFinished() {
		setInstallProgress("Finished", 100);
		showMessage("Installed successfully", SWTResourceManager.getColor(0, 153, 51));

		btnInstall.setText("Finished");
		btnInstall.setEnabled(false);

		setEnableDestiantionPathEditors(false);
	}

	public void alreadyInstalled() {
		setInstallProgress("Finished", 100);
		showMessage("Project is already installed", SWTResourceManager.getColor(0, 153, 51));

		btnInstall.setText("Install");
		btnInstall.setEnabled(false);

		setEnableDestiantionPathEditors(true);
	}
}
