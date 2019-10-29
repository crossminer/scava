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
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.jface.preference.DirectoryFieldEditor;

public class Database extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
	boolean enabledUserMonitoring = preferenceStore.getBoolean(Preferences.USERMONITORING_ENABLED);
	private DirectoryFieldEditor directoryFieldEditor;
	private boolean dirtyState = false;
	private boolean restartRequired = false;
	private BooleanFieldEditor userMonitoringEnabled;
	// private BooleanFieldEditor booleanFieldEditor;

	public Database() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.PLUGIN_ID));

	}

	@Override
	protected void createFieldEditors() {

		StringFieldEditor stringFieldEditor = new StringFieldEditor(Preferences.USER_AUTHENTICATION_TOKEN, "User token", -1, StringFieldEditor.VALIDATE_ON_KEY_STROKE, getFieldEditorParent());
		stringFieldEditor.setEmptyStringAllowed(true);
		addField(stringFieldEditor);

		userMonitoringEnabled = new BooleanFieldEditor(Preferences.USERMONITORING_ENABLED, "Enable usermonitoring", BooleanFieldEditor.DEFAULT, getFieldEditorParent());
		userMonitoringEnabled.setEnabled(!preferenceStore.getString(Preferences.USER_AUTHENTICATION_TOKEN).isEmpty(), getFieldEditorParent());
		addField(userMonitoringEnabled);

//		booleanFieldEditor = new BooleanFieldEditor(Preferences.ERASRE_DATABASE_AFTER_METRIC_CALCULATION, "Remove events from database after metric calculation", BooleanFieldEditor.DEFAULT,
//				getFieldEditorParent());
//		booleanFieldEditor.setEnabled(enabledUserMonitoring, getFieldEditorParent());
//		addField(booleanFieldEditor);

		directoryFieldEditor = new DirectoryFieldEditor(Preferences.DATABASE_PATH, "Database path", getFieldEditorParent());
		directoryFieldEditor.setEmptyStringAllowed(!enabledUserMonitoring);
		directoryFieldEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		directoryFieldEditor.setEnabled(enabledUserMonitoring, getFieldEditorParent());
		addField(directoryFieldEditor);

		// ButtonFieldEditor buttonFieldEditor = new ButtonFieldEditor("id", "Database storage", getFieldEditorParent());
		// buttonFieldEditor.setEnabled(false, getFieldEditorParent());
		// addField(buttonFieldEditor);

	}

	public void requestRestart() {

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				PlatformUI.getWorkbench().restart();
			}
		});

	}

	@Override
	public boolean performOk() {

		if (dirtyState) {
			boolean openConfirm = MessageDialog.openConfirm(Activator.getDefault().getWorkbench().getDisplay().getActiveShell(), "Restart", "This action need an eclipse restart.");
			if (openConfirm) {
				restartRequired = true;
			} else {
				return false;
			}
		}
		boolean performOk = super.performOk();
		if (restartRequired) {
			preferenceStore.setValue(Preferences.DATABASE_PATH, directoryFieldEditor.getStringValue());
			String string = preferenceStore.getString(Preferences.DATABASE_PATH);
			requestRestart();
		}
		return performOk;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		boolean boolean1 = preferenceStore.getBoolean(Preferences.USERMONITORING_ENABLED);
		String string = preferenceStore.getString(Preferences.DATABASE_PATH);
		String string2 = preferenceStore.getString(Preferences.USER_AUTHENTICATION_TOKEN);
		boolean boolean2 = preferenceStore.getBoolean(Preferences.ERASRE_DATABASE_AFTER_METRIC_CALCULATION);

		System.out.println("USERMONITORING_ENABLED " + boolean1);
		System.out.println("ERASRE_DATABASE_AFTER_METRIC_CALCULATION " + boolean2);
		System.out.println("DATABASE_PATH " + string);
		System.out.println("USER_AUTHENTICATION_TOKEN " + string2);

	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {


		Object source = event.getSource();

		if (source.equals(userMonitoringEnabled)) {

			if (((BooleanFieldEditor) source).getBooleanValue()) {
				directoryFieldEditor.setEnabled(true, getFieldEditorParent());
			} else {
				directoryFieldEditor.setEnabled(false, getFieldEditorParent());
			}

			if (!event.getNewValue().equals(preferenceStore.getBoolean(Preferences.USERMONITORING_ENABLED))) {
				dirtyState = true;
			} else {
				dirtyState = false;
			}

		}
		if (source instanceof DirectoryFieldEditor) {
			if (!(event.getNewValue().equals(preferenceStore.getDefaultString(Preferences.DATABASE_PATH)) && event.getNewValue().equals(preferenceStore.getString(Preferences.DATABASE_PATH)))) {
				dirtyState = true;
			} else {
				dirtyState = false;
			}
		}

		if (source instanceof StringFieldEditor) {

			if (!event.getNewValue().toString().isEmpty()) {
				userMonitoringEnabled.setEnabled(true, getFieldEditorParent());
				directoryFieldEditor.setEnabled(true, getFieldEditorParent());
			} else {
				userMonitoringEnabled.setEnabled(false, getFieldEditorParent());
				directoryFieldEditor.setEnabled(false, getFieldEditorParent());
			}
		}
		super.propertyChange(event);
	}

}
