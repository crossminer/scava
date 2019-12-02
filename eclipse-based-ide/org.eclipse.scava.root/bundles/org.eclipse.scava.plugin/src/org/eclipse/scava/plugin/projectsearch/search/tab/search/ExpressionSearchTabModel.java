/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.tab.search;

import java.util.List;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;

import io.swagger.client.ApiException;
import io.swagger.client.api.ArtifactsRestControllerApi;
import io.swagger.client.model.Artifact;

public class ExpressionSearchTabModel extends SearchTabModel {
	private final String expression;

	public ExpressionSearchTabModel(KnowledgeBaseAccess knowledgeBaseAccess, String expression) {
		super(knowledgeBaseAccess);
		this.expression = expression;
	}

	@Override
	public List<Artifact> getNextPageResults() throws ApiException {
		ArtifactsRestControllerApi artifactsRestControllerApi = knowledgeBaseAccess.getArtifactRestControllerApi();
		List<Artifact> projects = artifactsRestControllerApi.getProjectUsingGET(expression, nextPage, pageSize,
				pageSortAscending ? "ASC" : "DESC");
		nextPage++;
		hasNextPage = projects.size() >= pageSize;
		return projects;
	}

	@Override
	public String getDescription() {
		return "Search results of \"" + expression + "\"";
	}
}
