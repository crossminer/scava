/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.ApiMigrationResultView;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.apidocumentation.ApiMigrationApiDocumentationController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.apidocumentation.ApiMigrationApiDocumentationModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.ApiMigrationRecommendationController;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.ApiMigrationRecommendationModel;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.JumpToMigrationIssueRequestEvent;
import org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.ShowApiDocumentationRequestEvent;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;

public class ApiMigrationController extends ModelViewController<ApiMigrationModel, ApiMigrationView>
		implements IApiMigrationViewEventListener {
	private IResourceChangeListener resourceChangeListener;

	public ApiMigrationController(Controller parent, ApiMigrationModel model, ApiMigrationView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();

		getModel().getApiMigrationParameters().getProject().getWorkspace()
				.addResourceChangeListener(resourceChangeListener = new IResourceChangeListener() {

					@Override
					public void resourceChanged(IResourceChangeEvent event) {
						Object source = event.getSource();

						if (source instanceof IProject) {
							IProject project = (IProject) source;

							if (!project.equals(getModel().getApiMigrationParameters().getProject())) {
								return;
							}
						}

						try {

							ContentChangeListenerResourceDeltaVisitor contentChangeListenerResourceDeltaVisitor = new ContentChangeListenerResourceDeltaVisitor();
							event.getDelta().accept(contentChangeListenerResourceDeltaVisitor);
							if (contentChangeListenerResourceDeltaVisitor.contentChangesHappened) {
								// routeEventToSubControllers(new
								// ReparseRequestEvent(ApiMigrationController.this));
							}
						} catch (CoreException e) {
							e.printStackTrace();
						}

					}
				}, IResourceChangeEvent.POST_CHANGE);

		ApiMigrationApiDocumentationModel apiMigrationApiDocumentationModel = new ApiMigrationApiDocumentationModel(
				getModel().getKnowledgeBaseAccess(), getModel().getApiMigrationParameters());
		ApiMigrationResultView apiMigrationApiDocumentationView = new ApiMigrationResultView();
		ApiMigrationApiDocumentationController apiMigrationApiDocumentationController = new ApiMigrationApiDocumentationController(
				this, apiMigrationApiDocumentationModel, apiMigrationApiDocumentationView);
		apiMigrationApiDocumentationController.init();
		getView().showApiDocumentation(apiMigrationApiDocumentationView);

		getView().showMigrationInfo(getModel().getApiMigrationParameters().getOldLibraryVersion(),
				getModel().getApiMigrationParameters().getNewLibraryVersion());

		ApiMigrationRecommendationModel apiMigrationRecommendationModel = new ApiMigrationRecommendationModel(
				getModel().getKnowledgeBaseAccess(), getModel().getApiMigrationParameters());
		ApiMigrationResultView apiMigrationDetectionView = new ApiMigrationResultView();
		ApiMigrationRecommendationController apiMigrationRecommendationController = new ApiMigrationRecommendationController(
				this, apiMigrationRecommendationModel, apiMigrationDetectionView);
		apiMigrationRecommendationController.init();
		getView().showDetections(apiMigrationDetectionView);
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	public ApiMigrationParameters getApiMigrationParameters() {
		return getModel().getApiMigrationParameters();
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {
		if (routedEvent instanceof ShowApiDocumentationRequestEvent) {
			Set<ApiMigrationApiDocumentationController> subControllers = getSubControllers(
					ApiMigrationApiDocumentationController.class);
			for (ApiMigrationApiDocumentationController controller : subControllers) {
				getView().focusOn(controller.getView());
				break;
			}
		}

		if (routedEvent instanceof JumpToMigrationIssueRequestEvent) {
			Set<ApiMigrationRecommendationController> subControllers = getSubControllers(
					ApiMigrationRecommendationController.class);
			for (ApiMigrationRecommendationController controller : subControllers) {
				getView().focusOn(controller.getView());
				break;
			}
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	protected void disposeController() {
		getModel().getApiMigrationParameters().getProject().getWorkspace()
				.removeResourceChangeListener(resourceChangeListener);

		super.disposeController();
	}

	private final class ContentChangeListenerResourceDeltaVisitor implements IResourceDeltaVisitor {
		public boolean contentChangesHappened = false;

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
			case IResourceDelta.REMOVED:
				contentChangesHappened = true;
				break;
			case IResourceDelta.CHANGED:
				int flags = delta.getFlags();
				if (flags == 0) {
					return true;
				} else {
					if (flags != 0 && (flags & IResourceDelta.MARKERS) == 0) {
						contentChangesHappened = true;
					}
				}

				break;
			}
			return false;
		}
	}
}
