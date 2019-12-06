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
import org.eclipse.scava.plugin.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class Timeout extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public Timeout() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID));
		setDescription("Request timeout settings (in milliseconds)");
	}

	@Override
	protected void createFieldEditors() {
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_PROJECTSEARCH, "Timeout of Project Search", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_LIBRARYSEARCH, "Timeout of Library Update search", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_CODERECOMMENDATION, "Timeout of Code Recommendation", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_APIDOCUMENTATION, "Timeout of API Documentation and Q&&A posts", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_APIMIGRATION_LIBRARY_SEARCH, "Timeout of API Migration Library search", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_APIMIGRATION_M3, "Timeout of API Migration M3 model request", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_APIMIGRATION_RECOMMENDATIONS, "Timeout of API Migration recommendations", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_FOCUS_APICALL, "Timeout of FOCUS API Call", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_FOCUS_CODESNIPPET, "Timeout of FOCUS Code snippet", getFieldEditorParent()));
		addField(new IntegerFieldEditor(Preferences.TIMEOUT_UPLOAD_METRICS, "Timeout of Upload Metrics", getFieldEditorParent()));
	}

}
