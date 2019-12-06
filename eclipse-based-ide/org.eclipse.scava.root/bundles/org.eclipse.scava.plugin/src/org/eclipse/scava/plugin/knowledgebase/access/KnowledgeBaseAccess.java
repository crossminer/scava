/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.knowledgebase.access;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ApiMigrationRestControllerApi;
import io.swagger.client.api.ArtifactsRestControllerApi;
import io.swagger.client.api.RecommenderRestControllerApi;

public class KnowledgeBaseAccess {
	private final ApiClient apiClient;

	private final IPropertyChangeListener propertyChangeListener;

	public KnowledgeBaseAccess() {
		apiClient = new ApiClient();
		configureBasePath(apiClient);

		propertyChangeListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (Preferences.KNOWLEDGEBASE_SERVER_ADDRESS.equals(property)
						|| Preferences.KNOWLEDGEBASE_SERVER_PORT.equals(property)) {
					configureBasePath(apiClient);
				}
			}
		};

		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.addPropertyChangeListener(propertyChangeListener);
	}

	private void configureBasePath(ApiClient apiClient) {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		String basePath = "";

		// Host address
		String serverAddress = preferences.getString(Preferences.KNOWLEDGEBASE_SERVER_ADDRESS);

		// Port
		int serverPort = preferences.getInt(Preferences.KNOWLEDGEBASE_SERVER_PORT);

		basePath = serverAddress + ":" + serverPort;

		apiClient.setBasePath(basePath);
	}

	public ArtifactsRestControllerApi getArtifactRestControllerApi(String timeoutPreferenceKey) {
		setTimeout(apiClient, timeoutPreferenceKey);
		return new ArtifactsRestControllerApi(apiClient);
	}

	public RecommenderRestControllerApi getRecommenderRestController(String timeoutPreferenceKey) {
		setTimeout(apiClient, timeoutPreferenceKey);
		return new RecommenderRestControllerApi(apiClient);
	}

	public ApiMigrationRestControllerApi getApiMigrationRestController(String timeoutPreferenceKey) {
		setTimeout(apiClient, timeoutPreferenceKey);
		return new ApiMigrationRestControllerApi(apiClient);
	}
	
	private void setTimeout(ApiClient apiClient, String timeoutPreferenceKey) {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		int timeoutValue = preferences.getInt(timeoutPreferenceKey);
		apiClient.setConnectTimeout(timeoutValue);
		apiClient.setReadTimeout(timeoutValue);
		apiClient.setWriteTimeout(timeoutValue);
	}

	public void dispose() {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		preferences.removePropertyChangeListener(propertyChangeListener);
	}
}
