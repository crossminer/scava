/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.eclipse.markerresolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public abstract class MarkerResolution extends WorkbenchMarkerResolution implements IMarkerResolution {
	private static final IMarker[] empty = new IMarker[0];

	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		return empty;
	}
}
