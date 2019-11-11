/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.search.selectedResult;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

import io.swagger.client.model.Artifact;

public class DeselectRequestEvent extends RoutedEvent {

	private final Artifact project;

	public DeselectRequestEvent(Controller source, Artifact project) {
		super(source);
		this.project = project;
	}

	public Artifact getProject() {
		return project;
	}

}
