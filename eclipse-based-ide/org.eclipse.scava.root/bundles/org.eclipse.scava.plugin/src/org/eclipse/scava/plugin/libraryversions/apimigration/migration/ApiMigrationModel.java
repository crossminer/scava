/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

public class ApiMigrationModel extends Model {
	private final KnowledgeBaseAccess knowledgeBaseAccess;
	private final ApiMigrationParameters apiMigrationParameters;

	public ApiMigrationModel(KnowledgeBaseAccess knowledgeBaseAccess, ApiMigrationParameters apiMigrationParameters) {
		super();
		this.knowledgeBaseAccess = knowledgeBaseAccess;
		this.apiMigrationParameters = apiMigrationParameters;
	}

	public KnowledgeBaseAccess getKnowledgeBaseAccess() {
		return knowledgeBaseAccess;
	}

	public ApiMigrationParameters getApiMigrationParameters() {
		return apiMigrationParameters;
	}

}
