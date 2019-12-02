/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.codehighlighter;

import java.util.Map;

import org.eclipse.core.resources.IResource;

public class BasicHighlightDefinition extends HighlightDefinition {

	public BasicHighlightDefinition(IResource resource) {
		super(resource);
	}

	public BasicHighlightDefinition(IResource resource, Map<String, Object> attributes) {
		super(resource, attributes);
	}

	@Override
	public void setAttribute(String attribute, Object value) {
		super.setAttribute(attribute, value);
	}

	@Override
	public Object getAttribute(String attribute) {
		return super.getAttribute(attribute);
	}
}
