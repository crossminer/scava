/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.install.installable;

import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.model.Artifact;

public class InstallableModel extends Model {
	private final Artifact project;
	private boolean customDestinationUsed;
	private String destination;

	public InstallableModel(Artifact project, String destination) {
		super();
		this.project = project;
		this.destination = destination;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Artifact getProject() {
		return project;
	}

	public boolean isCustomDestinationUsed() {
		return customDestinationUsed;
	}

	public void setCustomDestinationUsed(boolean customDestinationUsed) {
		this.customDestinationUsed = customDestinationUsed;
	}

}
