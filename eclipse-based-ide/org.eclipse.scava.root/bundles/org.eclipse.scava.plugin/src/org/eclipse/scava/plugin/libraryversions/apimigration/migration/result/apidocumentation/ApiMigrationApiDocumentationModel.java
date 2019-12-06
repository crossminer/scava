/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.apidocumentation;

import org.eclipse.scava.plugin.async.api.ApiAsyncBuilder;
import org.eclipse.scava.plugin.async.api.IApiAsyncBuilder;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationParameters;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultModel;
import org.eclipse.scava.plugin.preferences.Preferences;

import io.swagger.client.api.ApiMigrationRestControllerApi;
import io.swagger.client.model.Recommendation;

public class ApiMigrationApiDocumentationModel extends ApiMigrationResultModel {

	public ApiMigrationApiDocumentationModel(KnowledgeBaseAccess knowledgeBaseAccess,
			ApiMigrationParameters apiMigrationParameters) {
		super(knowledgeBaseAccess, apiMigrationParameters);
	}

	public IApiAsyncBuilder<Recommendation> requestRecommendationAsync() {
		return ApiAsyncBuilder.build(apiCallback -> {
			ApiMigrationRestControllerApi apiMigrationRestController = knowledgeBaseAccess
					.getApiMigrationRestController(Preferences.TIMEOUT_APIMIGRATION_RECOMMENDATIONS);
			return apiMigrationRestController.getDocumentationUsingGETAsync(
					getApiMigrationParameters().getOldLibraryVersion().toMavenCoord(),
					getApiMigrationParameters().getNewLibraryVersion().toMavenCoord(), apiCallback);
		});

	}

}
