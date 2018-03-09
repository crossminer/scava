/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.recommendation.highlight;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.commons.highlight.IHighlightable;
import org.eclipse.scava.commons.recommendation.source.SourceReplaceRecommendation;
import org.eclipse.scava.plugin.ui.abstractmvc.AbstractView;
import org.eclipse.scava.plugin.utils.Utils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class SourceCodeHighlighterView extends AbstractView {
	private final List<IMarker> markers;
	private final IProject project;
	
	public static final String MARKER_ID = "org.scava.plugin.markers.sourcecodehighlight";
	
	public SourceCodeHighlighterView(IProject project) {
		this.project = project;
		markers = new ArrayList<>();
	}
	
	public void showMarkers(List<IHighlightable> highlightables) {
		removeMarkers();
		
		for (IHighlightable highlightable : highlightables) {
			highlight(highlightable);
		}
	}
	
	private String buildMessage(IHighlightable highlightable) {
		SourceReplaceRecommendation sourceReplaceRecommendation = (SourceReplaceRecommendation) highlightable;
		return "SCAVA Source Replace Highlight:\n"
				+ "The marked code chunk recommended to be replaced with the following one:\n\n" + "\""
				+ sourceReplaceRecommendation.getNewCode() + "\"";
	}
	
	private void setupAttributes(IMarker marker, IHighlightable highlightable) throws CoreException {
		marker.setAttribute(IMarker.CHAR_START, highlightable.getStartChar());
		marker.setAttribute(IMarker.CHAR_END, highlightable.getEndChar() + 1);
		marker.setAttribute(IMarker.LOCATION, highlightable.getTargetFile() + ": from " + highlightable.getStartChar()
				+ " to " + highlightable.getEndChar());
		marker.setAttribute(IMarker.MESSAGE, buildMessage(highlightable));
		marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		// marker.setAttribute(IMarker.SOURCE_ID, value); //TODO could be useful
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.TRANSIENT, true);
		marker.setAttribute(IMarker.USER_EDITABLE, false);
	}
	
	private void setupMarker(IHighlightable highlightable) {
		try {
			IFile file = project.getFile(highlightable.getTargetFile());
			IMarker marker = file.createMarker(MARKER_ID);
			setupAttributes(marker, highlightable);
			markers.add(marker);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private void openTargetFileInEditor(String targetFile) throws FileNotFoundException {
		IFile file = Utils.openFileInProject(project, targetFile);
		Display.getDefault().asyncExec(() -> {
			try {
				IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), file);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void highlight(IHighlightable highlightable) {
		setupMarker(highlightable);
		
		try {
			String targetFile = highlightable.getTargetFile();
			openTargetFileInEditor(targetFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void removeMarkers() {
		for (IMarker marker : markers) {
			if (marker.exists()) {
				try {
					marker.delete();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
		markers.clear();
	}
	
	@Override
	public void dispose() {
		removeMarkers();
	}
	
}
