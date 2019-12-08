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

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;

import com.google.gson.Gson;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {	
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

		preferences.setDefault(Preferences.KNOWLEDGEBASE_SERVER_ADDRESS, "http://localhost");
		preferences.setDefault(Preferences.KNOWLEDGEBASE_SERVER_PORT, 8080);
		preferences.setDefault(Preferences.WEBDASHBOARD_BASE_PATH, "https://crossminer.biterg.io");
		preferences.setDefault(Preferences.USERMONITORING_DISABLEMENTS, new Gson().toJson(new UserActivityDisablements()));
		preferences.setDefault(Preferences.ERASRE_DATABASE_AFTER_METRIC_CALCULATION, true);
		preferences.setDefault(Preferences.USERMONITORING_ENABLED, false);
		preferences.setDefault(Preferences.USER_AUTHENTICATION_TOKEN, "");
		preferences.setDefault(Preferences.HELP_MENU_OPEN, true);
		
		preferences.setDefault(Preferences.TIMEOUT_PROJECTSEARCH, 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_LIBRARYSEARCH, 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_CODERECOMMENDATION, 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_APIDOCUMENTATION, 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_APIMIGRATION_LIBRARY_SEARCH, 15 * 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_APIMIGRATION_M3, 15 * 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_APIMIGRATION_RECOMMENDATIONS, 15 * 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_FOCUS_APICALL, 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_FOCUS_CODESNIPPET, 60 * 1000);
		preferences.setDefault(Preferences.TIMEOUT_UPLOAD_METRICS, 60 * 1000);

		if (existDirectory(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString() + File.separator + "OrientDataBase")) {
			preferences.setDefault(Preferences.DATABASE_PATH, ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString() + File.separator + "OrientDataBase");
		} else {
			preferences.setDefault(Preferences.DATABASE_PATH, "");
		}

	}

	private boolean existDirectory(String path) {

		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdir();
		}

		return directory.exists();
	}

}
