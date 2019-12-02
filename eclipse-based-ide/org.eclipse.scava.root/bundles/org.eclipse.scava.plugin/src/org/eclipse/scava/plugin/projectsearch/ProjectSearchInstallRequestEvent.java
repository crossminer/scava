/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch;

import java.util.Collections;
import java.util.Set;

import org.eclipse.scava.plugin.mvc.controller.Controller;
import org.eclipse.scava.plugin.mvc.event.routed.RoutedEvent;

import io.swagger.client.model.Artifact;

public class ProjectSearchInstallRequestEvent extends RoutedEvent {
	private final Set<Artifact> projects;

	public ProjectSearchInstallRequestEvent(Controller source, Set<Artifact> projects) {
		super(source);
		this.projects = projects;
	}

	public Set<Artifact> getProjects() {
		return Collections.unmodifiableSet(projects);
	}
}
