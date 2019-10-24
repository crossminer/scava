/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.wb.swt.SWTResourceManager;

public class General extends PreferencePage implements IWorkbenchPreferencePage {

	public General() {

	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID));
		setDescription("General settings and informations related to the CROSSMINER Eclipse-based IDE");
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite top = new Composite(parent, SWT.LEFT);
		top.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout gl_top = new GridLayout();
		gl_top.numColumns = 2;
		top.setLayout(gl_top);

		Label lblCurrentVersion = new Label(top, SWT.NONE);
		lblCurrentVersion.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblCurrentVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblCurrentVersion.setText("Version:");

		Label lblVersionNumber = new Label(top, SWT.NONE);
		lblVersionNumber.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		try {
			String version = Activator.getDefault().getBundle().getHeaders().get("Bundle-Version");
			lblVersionNumber.setText(version);
		} catch (Exception e) {
			MessageDialog.openError(getShell(), "Error",
					"Something went wrong during requesting the plug-in's version number:\n\n" + e);
			e.printStackTrace();
		}

		return top;
	}

}
