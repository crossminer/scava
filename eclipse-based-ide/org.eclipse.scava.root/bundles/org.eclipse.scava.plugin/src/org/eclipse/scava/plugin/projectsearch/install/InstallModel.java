/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.projectsearch.install;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.scava.plugin.mvc.model.Model;

import io.swagger.client.model.Artifact;

public class InstallModel extends Model {
	private final Set<Artifact> selectedForInstall;
	private final Set<Artifact> installed;
	private String basePath;

	public InstallModel(Set<Artifact> selectedForInstall) {
		super();
		this.selectedForInstall = selectedForInstall;
		this.installed = new HashSet<>();
	}

	public Set<Artifact> getSelectedForInstall() {
		return Collections.unmodifiableSet(selectedForInstall);
	}

	public Set<Artifact> getNotInstalled() {
		HashSet<Artifact> copy = new HashSet<>(selectedForInstall);
		copy.removeAll(installed);
		return Collections.unmodifiableSet(copy);
	}

	public void setInstalled(Artifact project) {
		installed.add(project);
	}

	public boolean isAllProjectInstalled() {
		return selectedForInstall.containsAll(installed) && installed.containsAll(selectedForInstall);
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	public String getBasePath() {
		return basePath;
	}

}
