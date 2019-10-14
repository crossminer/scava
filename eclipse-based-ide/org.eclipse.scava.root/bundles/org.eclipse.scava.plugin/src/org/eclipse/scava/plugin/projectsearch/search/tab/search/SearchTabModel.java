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
import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.ApiException;
import io.swagger.client.model.Artifact;

//TODO needs clarifications about the way the server sends the artifacts. Would be good for us if the results were wrapped in a Page<Artifact> thing
public abstract class SearchTabModel extends Model {
	protected final KnowledgeBaseAccess knowledgeBaseAccess = new KnowledgeBaseAccess();
	protected int pageSize = 10;
	protected int nextPage = 0;
	protected boolean pageSortAscending = true;
	protected boolean hasNextPage = true;
	
	public boolean hasNextPage() {
		return hasNextPage;
	}

	public abstract List<Artifact> getNextPageResults() throws ApiException;
	
	public abstract String getDescription();
}
