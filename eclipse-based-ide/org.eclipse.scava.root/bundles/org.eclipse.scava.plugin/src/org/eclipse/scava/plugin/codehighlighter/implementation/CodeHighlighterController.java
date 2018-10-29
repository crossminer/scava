/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.codehighlighter.implementation;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.codehighlighter.ICodeChunk;
import org.eclipse.scava.plugin.codehighlighter.ICodeHighlighterController;
import org.eclipse.scava.plugin.codehighlighter.ICodeHighlighterModel;
import org.eclipse.scava.plugin.mvc.IController;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModelController;

public class CodeHighlighterController extends AbstractModelController<ICodeHighlighterModel>
		implements ICodeHighlighterController {

	private final Collection<IMarker> markers;

	public CodeHighlighterController(IController parent, ICodeHighlighterModel model) {
		super(parent, model);

		markers = new ArrayList<>();
	}
	
	@Override
	public void init() {
		highlightCodeChunks();
	}

	private void highlightCodeChunks() {
		try {
			Collection<ICodeChunk> codeChunks = getModel().getCodeChunks();
			for (ICodeChunk codeChunk : codeChunks) {
				highlight(codeChunk);
			}
		} catch (CoreException ex) {

		}
	}

	private void highlight(ICodeChunk codeChunk) throws CoreException {
		IProject project = codeChunk.getProject();
		String filePath = codeChunk.getFilePath();
		IFile file = project.getFile(filePath);
		String markerID = getModel().getMarkerID();
		
		IMarker marker = file.createMarker(markerID);
		initializeMarker(marker, codeChunk);
		markers.add(marker);
	}

	private void initializeMarker(IMarker marker, ICodeChunk codeChunk) throws CoreException {
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
