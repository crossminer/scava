/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main.page;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;
import org.eclipse.ui.IWorkbenchPage;

public class PageModel extends Model {
	private final IWorkbenchPage page;
	private final KnowledgeBaseAccess knowledgeBaseAccess;

	public PageModel(IWorkbenchPage page, KnowledgeBaseAccess knowledgeBaseAccess) {
		super();
		this.page = page;
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}

	public KnowledgeBaseAccess getKnowledgeBaseAccess() {
		return knowledgeBaseAccess;
	}

	public IWorkbenchPage getPage() {
		return page;
	}
}
