/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.main;

import org.eclipse.scava.plugin.knowledgebase.access.KnowledgeBaseAccess;
import org.eclipse.scava.plugin.mvc.model.Model;

public class MainModel extends Model {
	private KnowledgeBaseAccess knowledgeBaseAccess;

	public void setKnowledgeBaseAccess(KnowledgeBaseAccess knowledgeBaseAccess) {
		this.knowledgeBaseAccess = knowledgeBaseAccess;
	}

	public KnowledgeBaseAccess getKnowledgeBaseAccess() {
		return knowledgeBaseAccess;
	}

	@Override
	public void dispose() {
		if (knowledgeBaseAccess != null) {
			knowledgeBaseAccess.dispose();
		}
		super.dispose();
	}
}
