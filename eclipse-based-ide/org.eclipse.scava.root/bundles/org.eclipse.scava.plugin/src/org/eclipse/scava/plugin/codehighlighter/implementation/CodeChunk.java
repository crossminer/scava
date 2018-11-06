/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.codehighlighter.implementation;

import org.eclipse.core.resources.IProject;
import org.eclipse.scava.plugin.codehighlighter.ICodeChunk;

public class CodeChunk implements ICodeChunk {
	private final IProject project;
	private final String filePath;
	private final int startPosition;
	private final int endPosition;

	public CodeChunk(IProject project, String filePath, int startPosition, int endPosition) {
		this.project = project;
		this.filePath = filePath;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public int getStartPosition() {
		return startPosition;
	}

	@Override
	public int getEndPosition() {
		return endPosition;
	}

}
