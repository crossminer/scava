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

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.ApiException;
import io.swagger.client.api.ArtifactsRestControllerApi;
import io.swagger.client.model.Artifact;

public class ExpressionSearchTabModel extends SearchTabModel {
	private final String expression;

	public ExpressionSearchTabModel(String expression) {
		super();
		this.expression = expression;
	}

	@Override
	public List<Artifact> getResults() {
		List<Artifact> results = new ArrayList<>();
		
		try {
			ArtifactsRestControllerApi artifactsRestControllerApi = knowledgeBaseAccess.getArtifactRestControllerApi();
			results = artifactsRestControllerApi.getProjectUsingGET(expression, "0", "10", "asc");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		
		return results;
	}

	@Override
	public String getDescription() {
		return "Search results of \"" + expression + "\"";
	}
}
