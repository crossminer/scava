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
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.mvc.model.Model;

public class CodeHighlighterModel extends Model {
	private final String markerID;
	private final Collection<IMarker> markers;

	public CodeHighlighterModel(String markerID) {
		super();
		this.markerID = markerID;
		markers = new ArrayList<>();
	}

	public IMarker createMarker(IHighlightDefinition highlightDefinition) throws CoreException {
		IResource resource = highlightDefinition.getResource();
		Map<String, Object> attributes = highlightDefinition.getAttributes();

		IMarker marker = resource.createMarker(markerID);
		marker.setAttributes(attributes);

		markers.add(marker);
		return marker;
	}

	public void removeAllMarkers() throws CoreException {
		for (IMarker marker : markers) {
			marker.delete();
		}
	}
}
