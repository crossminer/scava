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

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.ResourceManager;

public abstract class ScavaMarkerResolution extends MarkerResolution {

	@Override
	public Image getImage() {
		return ResourceManager.getPluginImage("org.eclipse.scava.plugin", "icons/icon-16-white.png");
	}
}
