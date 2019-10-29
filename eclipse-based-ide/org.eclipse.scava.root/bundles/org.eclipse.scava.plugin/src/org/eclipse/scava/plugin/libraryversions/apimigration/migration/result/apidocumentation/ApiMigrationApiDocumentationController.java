/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.apidocumentation;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scava.plugin.async.api.ApiAsyncRequestController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultView;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.webreferenceviewer.WebReferenceViewerController;
import org.eclipse.scava.plugin.webreferenceviewer.WebReferenceViewerModel;
import org.eclipse.scava.plugin.webreferenceviewer.WebReferenceViewerView;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.ApiException;
import io.swagger.client.model.Recommendation;
import io.swagger.client.model.RecommendationItem;

public class ApiMigrationApiDocumentationController
		extends ApiMigrationResultController<ApiMigrationApiDocumentationModel, ApiMigrationResultView> {

	private final ApiAsyncRequestController<Recommendation> recommendationRequestController;
	private WebReferenceViewerController webReferenceController;

	public ApiMigrationApiDocumentationController(Controller parent, ApiMigrationApiDocumentationModel model,
			ApiMigrationResultView view) {
		super(parent, model, view);

		recommendationRequestController = new ApiAsyncRequestController<>(this,
				getModel().requestRecommendationAsync().onSuccess(this::onRecommendationSuccess)
						.onFail(this::onRecommendationFail).executeWith(Display.getDefault()::asyncExec));
	}

	protected void onRecommendationSuccess(Recommendation results) {
		WebReferenceViewerModel model = new WebReferenceViewerModel();
		WebReferenceViewerView view = new WebReferenceViewerView();
		webReferenceController = new WebReferenceViewerController(this, model, view);
		webReferenceController.init();

		getView().showResults(view);

		List<RecommendationItem> recommendationItems = results.getRecommendationItems();
		List<String> postIds = recommendationItems.stream().map(RecommendationItem::getApiDocumentationLink)
				.collect(Collectors.toList());
		webReferenceController.showStackOverflowReferences(postIds);
	}

	protected void onRecommendationFail(ApiException exception) {
		getView().showLoadingFailed(ErrorHandler.logAndGetMessage(exception));
	}

	@Override
	protected void loadResults() {
		getView().showLoadingResults();

		recommendationRequestController.execute();
	}
}
