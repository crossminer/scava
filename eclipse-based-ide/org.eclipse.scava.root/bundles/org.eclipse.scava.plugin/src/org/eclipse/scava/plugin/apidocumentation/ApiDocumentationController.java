/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.apidocumentation;

import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.apidocumentation.result.ApiDocumentation;
import org.eclipse.scava.plugin.apidocumentation.result.ApiDocumentationResultController;
import org.eclipse.scava.plugin.apidocumentation.result.ApiDocumentationResultModel;
import org.eclipse.scava.plugin.apidocumentation.result.ApiDocumentationResultView;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.ApiException;

public class ApiDocumentationController extends ModelViewController<ApiDocumentationModel, ApiDocumentationView>
		implements IApiDocumentationViewEventListener {

	public ApiDocumentationController(Controller parent, ApiDocumentationModel model, ApiDocumentationView view) {
		super(parent, model, view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public void request(String methodCode) {
		getSubControllers().forEach(Controller::dispose);

		try {
			Collection<ApiDocumentation> apiDocumentations = getModel().getApiDocumentations(methodCode);

			apiDocumentations.forEach(apiDocumentation -> {
				ApiDocumentationResultModel model = new ApiDocumentationResultModel(apiDocumentation);
				ApiDocumentationResultView view = new ApiDocumentationResultView();
				ApiDocumentationResultController controller = new ApiDocumentationResultController(this, model, view);

				controller.init();

				getView().showResult(view);
			});
		} catch (ApiException e) {
			e.printStackTrace();
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
					"Unexpected error during requesting api documentations:\n\n" + e);
		}
	}
}
