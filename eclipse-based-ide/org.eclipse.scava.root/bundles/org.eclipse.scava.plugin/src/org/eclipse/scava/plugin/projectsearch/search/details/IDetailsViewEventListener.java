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
import org.eclipse.scava.plugin.mvc.view.IViewEventListener;

public interface IDetailsViewEventListener extends IViewEventListener {
	void onCheckOnGithub();

	void onCheckOnWeb();

	void onCheckOnWebDashboard();

	void onSearchSimilars(SimilarityMethod method);
	
	void onOpenInBrowserRequest(String url);
}
