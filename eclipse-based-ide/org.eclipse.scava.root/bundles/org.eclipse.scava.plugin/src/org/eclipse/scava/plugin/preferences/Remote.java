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
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class Remote extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public Remote() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID));
		setDescription("Remote access settings");
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(Preferences.KNOWLEDGEBASE_SERVER_ADDRESS, "KnowledgeBase server address", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.KNOWLEDGEBASE_SERVER_PORT, "KnowledgeBase server port", getFieldEditorParent()));
		addField(new StringFieldEditor(Preferences.WEBDASHBOARD_BASE_PATH, "WebDashboard base path", getFieldEditorParent()));
	}

}
