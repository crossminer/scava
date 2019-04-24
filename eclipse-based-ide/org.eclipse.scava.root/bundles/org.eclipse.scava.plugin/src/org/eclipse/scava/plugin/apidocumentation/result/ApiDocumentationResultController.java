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

import org.eclipse.scava.plugin.main.OpenInExternalBrowserRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;

public class ApiDocumentationResultController
		extends ModelViewController<ApiDocumentationResultModel, ApiDocumentationResultView>
		implements IApiDocumentationResultViewEventListener {

	public ApiDocumentationResultController(Controller parent, ApiDocumentationResultModel model,
			ApiDocumentationResultView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		getView().showApiDocumentation(getModel().getApiDocumentation());
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	public void onOpenUrl() {
		String url = getModel().getApiDocumentation().getUrl();
		routeEventToParentController(new OpenInExternalBrowserRequestEvent(this, url));
	}

}
