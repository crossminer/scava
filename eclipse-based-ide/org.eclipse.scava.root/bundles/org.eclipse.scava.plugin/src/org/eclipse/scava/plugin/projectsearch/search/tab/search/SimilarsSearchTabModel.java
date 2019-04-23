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

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;

import io.swagger.client.ApiException;
import io.swagger.client.api.RecommenderRestControllerApi;
import io.swagger.client.model.Artifact;

public class SimilarsSearchTabModel extends SearchTabModel {
	private final Artifact referenceProject;
	private final SimilarityMethod method;

	public SimilarsSearchTabModel(Artifact referenceProject, SimilarityMethod method) {
		super();
		this.referenceProject = referenceProject;
		this.method = method;
	}

	@Override
	public List<Artifact> getResults() {
		List<Artifact> results = new ArrayList<>();
		
		try {
			RecommenderRestControllerApi recommenderRestController = knowledgeBaseAccess.getRecommenderRestController();
			results = recommenderRestController.getSimilarProjectUsingGET(method.name(), referenceProject.getId(), 10);
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return results;
	}

	@Override
	public String getDescription() {
		return "Similars to \"" + referenceProject.getFullName() + "\" by " + method.name();
	}

}
