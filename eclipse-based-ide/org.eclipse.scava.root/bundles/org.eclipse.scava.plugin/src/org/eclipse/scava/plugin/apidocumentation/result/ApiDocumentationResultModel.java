/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.apidocumentation.result;

import org.eclipse.scava.plugin.mvc.model.Model;

public class ApiDocumentationResultModel extends Model {
	private final ApiDocumentation apiDocumentation;

	public ApiDocumentationResultModel(ApiDocumentation apiDocumentation) {
		super();
		this.apiDocumentation = apiDocumentation;
	}

	public ApiDocumentation getApiDocumentation() {
		return apiDocumentation;
	}
}
