/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.model.Artifact;

public class SearchModel extends Model {
	private final Set<Artifact> selectedProjects = new HashSet<>();
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public SearchModel(KnowledgeBaseAccess knowledgeBaseAccess) {
		super();
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}
	
	public KnowledgeBaseAccess getKnowledgeBaseAccess() {
		return knowledgeBaseAccess;
	}

	public void addSelectedProject(Artifact project) {
		selectedProjects.add(project);
	}

	public void removeSelectedProject(Artifact project) {
		selectedProjects.remove(project);
	}

	public Set<Artifact> getSelectedProjects() {
		return Collections.unmodifiableSet(selectedProjects);
	}
}
