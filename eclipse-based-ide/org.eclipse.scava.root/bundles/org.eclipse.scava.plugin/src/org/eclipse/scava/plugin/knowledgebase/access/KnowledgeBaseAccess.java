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
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ArtifactsRestControllerApi;
import io.swagger.client.api.RecommenderRestControllerApi;

public class KnowledgeBaseAccess {
	private final ApiClient apiClient;

	public KnowledgeBaseAccess() {
		apiClient = new ApiClient();
		configureBasePath(apiClient);
	}

	private void configureBasePath(ApiClient apiClient) {
		String basePath = "";
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

		// Host address
		String serverAddress = preferences.getString(Preferences.KNOWLEDGEBASE_SERVER_ADDRESS);

		// Port
		int serverPort = preferences.getInt(Preferences.KNOWLEDGEBASE_SERVER_PORT);

		basePath = serverAddress + ":" + serverPort;
		
		apiClient.setBasePath(basePath);
	}

	public ArtifactsRestControllerApi getArtifactRestControllerApi() {
		return new ArtifactsRestControllerApi(apiClient);
	}

	public RecommenderRestControllerApi getRecommenderRestController() {
		return new RecommenderRestControllerApi(apiClient);
	}
}
