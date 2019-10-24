/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.scava.plugin.async.IAsyncHandler;
import org.eclipse.scava.plugin.async.api.ApiAsyncRequestController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ReparseRequestEvent;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultView;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.RecommendationTreeController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.RecommendationTreeModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.RecommendationTreeView;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.ApiException;
import io.swagger.client.model.Detection;

public class ApiMigrationRecommendationController
		extends ApiMigrationResultController<ApiMigrationRecommendationModel, ApiMigrationResultView> {

	private ApiAsyncRequestController<List<Detection>> detectionRequestController;
	private ApiAsyncRequestController<Map<String, List<String>>> snippetsRequestController;

	public ApiMigrationRecommendationController(Controller parent, ApiMigrationRecommendationModel model,
			ApiMigrationResultView view) {
		super(parent, model, view);

		detectionRequestController = new ApiAsyncRequestController<>(this,
				getModel().requestDetectionsAsync().onSuccess(this::onDetectionsSuccess).onFail(this::onFail));
		snippetsRequestController = new ApiAsyncRequestController<>(this,
				getModel().requestSnippetsAsync().onSuccess(this::onSnippetsSuccess).onFail(this::onFail));
	}

	@Override
	protected void loadResults() {
		getView().showLoadingResults();

		detectionRequestController.execute();
		snippetsRequestController.execute();
	}

	void onDetectionsSuccess(List<Detection> detections) {
		IAsyncHandler<Map<String, List<String>>> snippetsHandler = snippetsRequestController.getHandler();
		if (snippetsHandler != null && snippetsHandler.hasFinished()) {
			onAllSuccess(detections, snippetsHandler.getLastResult());
		}
	}

	void onSnippetsSuccess(Map<String, List<String>> snippets) {
		IAsyncHandler<List<Detection>> detectionsHandler = detectionRequestController.getHandler();
		if (detectionsHandler != null && detectionsHandler.hasFinished()) {
			onAllSuccess(detectionsHandler.getLastResult(), snippets);
		}
	}

	private void onFail(ApiException exception) {
		Display.getDefault().asyncExec(() -> {
			detectionRequestController.getHandler().cancel();
			snippetsRequestController.getHandler().cancel();
			getView().showLoadingFailed(ErrorHandler.logAndGetMessage(exception));
		});
	}

	private void onAllSuccess(List<Detection> detections, Map<String, List<String>> snippets) {
		Display.getDefault().asyncExec(() -> {
			if ((detections == null || detections.isEmpty()) && (snippets == null || snippets.isEmpty())) {
				getView().showMessage("There are no recommendations to show.");
				return;
			}

			IProject project = getModel().getApiMigrationParameters().getProject();
			IJavaProject javaProject = JavaCore.create(project);

			RecommendationTreeModel model = new RecommendationTreeModel(javaProject);
			RecommendationTreeView view = new RecommendationTreeView();
			RecommendationTreeController controller = new RecommendationTreeController(this, model, view);
			controller.init();

			getView().showResults(view);

			if (detections != null) {
				controller.add(detections, getModel().getApiMigrationParameters());
			}
			if (snippets != null) {
				controller.add(snippets);
			}
		});
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof ReparseRequestEvent) {
			Display.getDefault().asyncExec(() -> {
				IAsyncHandler<List<Detection>> detectionHandler = detectionRequestController.getHandler();
				IAsyncHandler<Map<String, List<String>>> snippetsHandler = snippetsRequestController.getHandler();

				if (detectionHandler != null && snippetsHandler != null && detectionHandler.hasFinished()
						&& snippetsHandler.hasFinished()) {
					getSubControllers(RecommendationTreeController.class).forEach(controller -> {
						controller.clear();

						controller.add(detectionHandler.getLastResult(), getModel().getApiMigrationParameters());
						controller.add(snippetsHandler.getLastResult());
					});
				}
			});

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}
}
