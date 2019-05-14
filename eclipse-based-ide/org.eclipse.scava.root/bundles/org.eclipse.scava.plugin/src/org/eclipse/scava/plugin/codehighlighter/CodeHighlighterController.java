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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.controller.ModelController;

public class CodeHighlighterController extends ModelController<CodeHighlighterModel> {

	private final Collection<IMarker> markers;

	public CodeHighlighterController(Controller parent, CodeHighlighterModel model) {
		super(parent, model);

		markers = new ArrayList<>();
	}

	@Override
	public void init() {
		highlightCodeChunks();
	}

	private void highlightCodeChunks() {
		try {
			Collection<CodeChunk> codeChunks = getModel().getCodeChunks();
			for (CodeChunk codeChunk : codeChunks) {
				highlight(codeChunk);
			}
		} catch (CoreException ex) {

		}
	}

	private void highlight(CodeChunk codeChunk) throws CoreException {
		IProject project = codeChunk.getProject();
		String filePath = codeChunk.getFilePath();
		IFile file = project.getFile(filePath);
		String markerID = getModel().getMarkerID();

		IMarker marker = file.createMarker(markerID);
		initializeMarker(marker, codeChunk);
		markers.add(marker);
	}

	private void initializeMarker(IMarker marker, CodeChunk codeChunk) throws CoreException {
		marker.setAttribute(IMarker.CHAR_START, codeChunk.getStartPosition());
		marker.setAttribute(IMarker.CHAR_END, codeChunk.getEndPosition() + 1);
		marker.setAttribute(IMarker.LOCATION, codeChunk.getFilePath() + ": from " + codeChunk.getStartPosition()
				+ " to " + codeChunk.getEndPosition());
		marker.setAttribute(IMarker.MESSAGE,
				"This code chunk has been marked by the CROSSMINER IDE Plug-in. Currently no more information is available.");
		marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		// marker.setAttribute(IMarker.SOURCE_ID, value); //NOTE could be useful
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
		marker.setAttribute(IMarker.TRANSIENT, true);
		marker.setAttribute(IMarker.USER_EDITABLE, false);
	}

	@Override
	protected void disposeController() {
		removeHighlights();
	}

	private void removeHighlights() {
		try {
			for (IMarker marker : markers) {
				remove(marker);
			}
		} catch (CoreException ex) {

		}
	}

	private void remove(IMarker marker) throws CoreException {
		if (marker.exists()) {
			marker.delete();
		}
	}
}
