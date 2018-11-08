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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.scava.plugin.Activator;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		
		preferences.setDefault(Preferences.KNOWLEDGEBASE_SERVER_ADDRESS, "http://localhost");
		preferences.setDefault(Preferences.KNOWLEDGEBASE_SERVER_PORT, 8080);
		preferences.setDefault(Preferences.WEBDASHBOARD_BASE_PATH, "https://crossminer.biterg.io");
	}

}
