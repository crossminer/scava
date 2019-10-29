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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.async.api.ApiAsyncRequestController;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.webreferenceviewer.WebReferenceViewerController;
import org.eclipse.scava.plugin.webreferenceviewer.WebReferenceViewerModel;
import org.eclipse.scava.plugin.webreferenceviewer.WebReferenceViewerView;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.ApiException;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;

public class ApiDocumentationController extends ModelViewController<ApiDocumentationModel, ApiDocumentationView>
		implements IApiDocumentationViewEventListener {
	private final ApiAsyncRequestController<Recommendation> recommendationRequestController;
	private WebReferenceViewerController webReferenceController;

	public ApiDocumentationController(Controller parent, ApiDocumentationModel model, ApiDocumentationView view) {
		super(parent, model, view);

		recommendationRequestController = new ApiAsyncRequestController<>(this,
				b -> b.onSuccess(this::onSuccess).onFail(this::onFail).executeWith(Display.getDefault()::asyncExec));
	}

	@Override
	public void init() {
		super.init();

		WebReferenceViewerModel model = new WebReferenceViewerModel();
		WebReferenceViewerView view = new WebReferenceViewerView();
		webReferenceController = new WebReferenceViewerController(this, model, view);
		webReferenceController.init();

		getView().showWebReferences(view);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public void request(String methodCode) {
		recommendationRequestController.execute(getModel().requestApiDocumentationAsync(methodCode));
	}

	private void onSuccess(Recommendation recommendation) {
		List<RecommendationItem> recommendationItems = recommendation.getRecommendationItems();
		List<String> postIds = recommendationItems.stream().map(RecommendationItem::getApiDocumentationLink)
				.collect(Collectors.toList());

		webReferenceController.clearResults();
		webReferenceController.showStackOverflowReferences(postIds);
	}

	private void onFail(ApiException e) {
		webReferenceController.showError(ErrorHandler.logAndGetMessage(e));
	}

}
