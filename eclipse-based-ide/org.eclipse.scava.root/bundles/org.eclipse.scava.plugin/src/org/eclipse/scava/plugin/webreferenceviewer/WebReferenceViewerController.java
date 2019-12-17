/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.webreferenceviewer;

import java.util.Collection;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.webreferenceviewer.reference.WebReferenceController;
import org.eclipse.scava.plugin.webreferenceviewer.reference.WebReferenceView;
import org.eclipse.scava.plugin.webreferenceviewer.reference.sites.StackOverflowReferenceModel;
import org.eclipse.swt.widgets.Display;

public class WebReferenceViewerController extends ModelViewController<WebReferenceViewerModel, WebReferenceViewerView>
		implements IWebReferenceViewerViewEventListener {

	public WebReferenceViewerController(Controller parent, WebReferenceViewerModel model, WebReferenceViewerView view) {
		super(parent, model, view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public void clearResults() {
		getSubControllers(WebReferenceController.class).forEach(Controller::dispose);
	}

	public void showStackOverflowReferences(Collection<WebReferenceWithFeedback> references) {
		Display display = Display.getCurrent();
		if (references.isEmpty()) {
			display.asyncExec(() -> getView().showError("No results to show"));
		} else {
			StackOverflowReferenceModel.getModelsAsync(references, model -> {
				display.asyncExec(() -> {
					WebReferenceView view = new WebReferenceView();
					WebReferenceController controller = new WebReferenceController(this, model, view);
					controller.init();

					getView().showReference(view);
				});
			}, e -> display.asyncExec(() -> getView().showError(ErrorHandler.logAndGetMessage(e))));
		}
	}

	public void showError(String errorMessage) {
		getView().showError(errorMessage);
	}
}
