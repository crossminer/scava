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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;

public abstract class HighlightDefinition implements IHighlightDefinition {
	private final IResource resource;
	private final Map<String, Object> attributes;

	public HighlightDefinition(IResource resource, Map<String, Object> attributes) {
		super();
		this.resource = resource;
		this.attributes = attributes;
	}

	public HighlightDefinition(IResource resource) {
		this(resource, new HashMap<>());
	}

	protected void setAttribute(String attribute, Object value) {
		attributes.put(attribute, value);
	}

	protected Object getAttribute(String attribute) {
		return attributes.get(attribute);
	}

	@Override
	public IResource getResource() {
		return resource;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	@Override
	public String toString() {
		return "HighlightDefinition [resource=" + resource + ", attributes=" + attributes + "]";
	}

}
