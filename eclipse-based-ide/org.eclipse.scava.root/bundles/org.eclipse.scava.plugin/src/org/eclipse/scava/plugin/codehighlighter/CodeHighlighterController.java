/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.codehighlighter;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelController;

public class CodeHighlighterController extends ModelController<CodeHighlighterModel> {

	public CodeHighlighterController(Controller parent, CodeHighlighterModel model) {
		super(parent, model);
	}

	@Override
	public void init() {
		super.init();
	}

	public void highlight(List<IHighlightDefinition> highlightDefinitions) throws CoreException {
		for (IHighlightDefinition highlightDefinition : highlightDefinitions) {
			highlight(highlightDefinition);
		}
	}

	public IMarker highlight(IHighlightDefinition highlightDefinition) throws CoreException {
		return getModel().createMarker(highlightDefinition);
	}

	@Override
	protected void disposeController() {
		try {
			getModel().removeAllMarkers();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
