/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

public class ScavaProject {

	private final String projectId;
	private final IProject project;
	private List<String> files = new ArrayList<>();

	public ScavaProject(String projectId, IProject project) {
		super();
		this.projectId = projectId;
		this.project = project;
	}

	public String getProjectId() {
		return projectId;
	}

	public IProject getProject() {
		return project;
	}

	public void addFileToProject(String file) {
		files.add(file);
	}

	@Override
	public String toString() {

		String description = projectId + " \n";
		for (String file : files) {
			description += "\t " + file;
		}

		return description;
	}

	public List<String> getFiles() {
		return files;
	}

}
