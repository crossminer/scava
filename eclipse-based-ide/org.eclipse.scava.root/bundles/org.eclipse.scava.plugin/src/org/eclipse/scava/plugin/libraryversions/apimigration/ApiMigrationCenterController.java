/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.plugin.async.api.ApiAsyncRequestController;
import org.eclipse.scava.plugin.libraryversions.Library;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationParameters;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.ApiMigrationView;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.ApiMigrationRecommendationTreeEclipseInterfaceEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

import io.swagger.client.ApiException;

public class ApiMigrationCenterController extends ModelViewController<ApiMigrationCenterModel, ApiMigrationCenterView>
		implements IApiMigrationCenterViewEventListener {

	private final ApiAsyncRequestController<File> m3RequestController;

	public ApiMigrationCenterController(Controller parent, ApiMigrationCenterModel model, ApiMigrationCenterView view) {
		super(parent, model, view);

		m3RequestController = new ApiAsyncRequestController<>(this,
				b -> b.onFail(this::onFailGettingM3Model).executeWith(Display.getDefault()::asyncExec));
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public void request(IProject project, File jar, Map<Library, Library> libraries) {
		m3RequestController.execute(getModel().getM3FromJarAsync(jar)
				.onSuccess(result -> onSuccessGettingM3Model(result, project, libraries)));
	}

	private void onSuccessGettingM3Model(File m3, IProject project, Map<Library, Library> libraries) {
		libraries.forEach((oldVersion, newVersion) -> {

			ApiMigrationParameters apiMigrationParameters = new ApiMigrationParameters(oldVersion, newVersion, m3,
					project);
			ApiMigrationModel apiMigrationModel = new ApiMigrationModel(getModel().getKnowledgeBaseAccess(),
					apiMigrationParameters);
			ApiMigrationView apiMigrationView = new ApiMigrationView();
			ApiMigrationController apiMigrationController = new ApiMigrationController(this, apiMigrationModel,
					apiMigrationView);
			apiMigrationController.init();

			getView().showMigration(apiMigrationView, oldVersion.toMavenCoord() + " -> " + newVersion.getVersion());
		});
	}

	private void onFailGettingM3Model(ApiException e) {
		ErrorHandler.logAndShowErrorMessage(getView().getViewSite().getShell(),
				"Error during requesting M3 model for your project.", e);
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {
		if (routedEvent instanceof ApiMigrationRecommendationTreeEclipseInterfaceEvent) {
			ApiMigrationRecommendationTreeEclipseInterfaceEvent event = (ApiMigrationRecommendationTreeEclipseInterfaceEvent) routedEvent;

			try {
				ApiMigrationCenterView.open(ApiMigrationCenterView.ID);

				Set<ApiMigrationController> subControllers = getSubControllers(ApiMigrationController.class);
				for (ApiMigrationController controller : subControllers) {
					if (event.getDetectionHighlightData().getApiMigrationParameters()
							.equals(controller.getApiMigrationParameters())) {
						getView().focusOnMigration(controller.getView());
						break;
					}
				}

			} catch (PartInitException e) {
				e.printStackTrace();
			}

			// note: intended no blocking of propagating event to sub controllers
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}
}
