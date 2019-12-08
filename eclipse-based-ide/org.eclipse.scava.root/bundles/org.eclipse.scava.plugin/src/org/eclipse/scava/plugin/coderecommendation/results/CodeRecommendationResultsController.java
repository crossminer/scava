/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation.results;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendation;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationRequest;
import org.eclipse.scava.plugin.coderecommendation.CodeRecommendationTarget;
import org.eclipse.scava.plugin.coderecommendation.ICodeRecommendationElement;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelViewController;
import org.eclipse.scava.plugin.mvc.event.routed.IRoutedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class CodeRecommendationResultsController
		extends ModelViewController<CodeRecommendationResultsModel, CodeRecommendationResultsView>
		implements ICodeRecommendationResultsViewEventListener {

	public CodeRecommendationResultsController(Controller parent, CodeRecommendationResultsModel model,
			CodeRecommendationResultsView view) {
		super(parent, model, view);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void requestViewClose() {
		dispose();
	}

	@Override
	protected void onReceiveRoutedEventFromParentController(IRoutedEvent routedEvent) {

		if (routedEvent instanceof AddCodeRecommendationsRequestEvent) {
			AddCodeRecommendationsRequestEvent event = (AddCodeRecommendationsRequestEvent) routedEvent;

			CodeRecommendationTarget target = event.getTarget();

			getModel().add(target);

			getView().showRecommendations(getModel().getRecommendationTargets(),
					target.getCodeRecommendationsRequests().iterator().next());

			return;
		}

		super.onReceiveRoutedEventFromParentController(routedEvent);
	}

	@Override
	public void onCodeRecommendationSelected(ICodeRecommendationElement element) {
		routeEventToParentController(new CodeRecommendationSelectedEvent(this, element));
	}

	@Override
	public void onTargetDoubleClicked(CodeRecommendationTarget target) {
		IPath location = target.getFile().getLocation();

		try {
			IDE.openEditorOnFileStore(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
					EFS.getLocalFileSystem().getStore(location));
		} catch (PartInitException e) {
			e.printStackTrace();
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error",
					"Unexpected error during opening file \n" + location + "\n" + e);
		}
	}

	@Override
	public void onDrop(CodeRecommendation codeRecommendation) {
		getModel().remove(codeRecommendation);
		getView().showRecommendations(getModel().getRecommendationTargets());

		Set<ICodeRecommendationElement> droppedElements = new HashSet<>();
		droppedElements.add(codeRecommendation);
		routeEventToParentController(new CodeRecommendationDroppedEvent(this, droppedElements));
	}

	@Override
	public void onDrop(CodeRecommendationRequest codeRecommendationRequest) {
		getModel().remove(codeRecommendationRequest);
		getView().showRecommendations(getModel().getRecommendationTargets());

		Set<ICodeRecommendationElement> droppedElements = new HashSet<>();
		droppedElements.add(codeRecommendationRequest);
		codeRecommendationRequest.getCodeRecommendations().forEach(droppedElements::add);
		routeEventToParentController(new CodeRecommendationDroppedEvent(this, droppedElements));
	}

	@Override
	public void onDrop(CodeRecommendationTarget target) {
		getModel().remove(target);
		getView().showRecommendations(getModel().getRecommendationTargets());

		Set<ICodeRecommendationElement> droppedElements = new HashSet<>();
		droppedElements.add(target);
		target.getCodeRecommendationsRequests().forEach(codeRecommendationRequest -> {
			droppedElements.add(codeRecommendationRequest);
			codeRecommendationRequest.getCodeRecommendations().forEach(droppedElements::add);
		});
		routeEventToParentController(new CodeRecommendationDroppedEvent(this, droppedElements));
	}

	@Override
	public void onDropAll() {
		Set<ICodeRecommendationElement> droppedElements = new HashSet<>();
		getModel().getRecommendationTargets().forEach(target -> {
			droppedElements.add(target);
			target.getCodeRecommendationsRequests().forEach(codeRecommendationRequest -> {
				droppedElements.add(codeRecommendationRequest);
				codeRecommendationRequest.getCodeRecommendations().forEach(droppedElements::add);
			});
		});

		getModel().removeAll();
		getView().showRecommendations(getModel().getRecommendationTargets());

		routeEventToParentController(new CodeRecommendationDroppedEvent(this, droppedElements));
	}

}
