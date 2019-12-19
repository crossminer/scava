/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration;

import java.io.File;

import org.eclipse.scava.plugin.async.api.ApiAsyncBuilder;
import org.eclipse.scava.plugin.async.api.IApiAsyncBuilder;
import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.scava.plugin.preferences.Preferences;

import io.swagger.client.api.ApiMigrationRestControllerApi;

public class ApiMigrationCenterModel extends Model {
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public ApiMigrationCenterModel(KnowledgeBaseAccess knowledgeBaseAccess) {
		super();
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}

	public KnowledgeBaseAccess getKnowledgeBaseAccess() {
		return knowledgeBaseAccess;
	}

	public IApiAsyncBuilder<File> getM3FromJarAsync(File jar) {
		return ApiAsyncBuilder.build(apiCallback -> {
			ApiMigrationRestControllerApi apiMigrationRestController = knowledgeBaseAccess
					.getApiMigrationRestController(Preferences.TIMEOUT_APIMIGRATION_M3);

			return apiMigrationRestController.getM3ModelUsingPOSTAsync(jar, apiCallback);
		});
	}

}
