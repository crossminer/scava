/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.details;

import org.eclipse.scava.plugin.knowledgebase.access.SimilarityMethod;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

import io.swagger.client.model.Artifact;

public class SearchSimilarsRequestEvent extends RoutedEvent {
	private final Artifact project;
	private final SimilarityMethod similarityMethod;

	public SearchSimilarsRequestEvent(Controller source, Artifact project, SimilarityMethod similarityMethod) {
		super(source);
		this.project = project;
		this.similarityMethod = similarityMethod;
	}

	public Artifact getProject() {
		return project;
	}

	public SimilarityMethod getSimilarityMethod() {
		return similarityMethod;
	}

}
