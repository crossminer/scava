/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.coderecommendation;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.scava.plugin.collections.AutoSortedArrayList;

public class CodeRecommendationTarget implements Comparable<CodeRecommendationTarget>, ICodeRecommendationElement {
	private final IFile file;
	private final Collection<CodeRecommendationRequest> codeRecommendationsRequests;

	public CodeRecommendationTarget(IFile file) {
		super();
		this.file = file;
		codeRecommendationsRequests = new AutoSortedArrayList<>();
	}

	public IFile getFile() {
		return file;
	}

	public Collection<CodeRecommendationRequest> getCodeRecommendationsRequests() {
		return codeRecommendationsRequests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file.getFullPath() == null) ? 0 : file.getFullPath().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeRecommendationTarget other = (CodeRecommendationTarget) obj;
		if (file.getFullPath() == null) {
			if (other.file.getFullPath() != null)
				return false;
		} else if (!file.getFullPath().equals(other.file.getFullPath()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodeRecommendationTarget [file=" + file + "]";
	}

	@Override
	public int compareTo(CodeRecommendationTarget o) {
		return file.getFullPath().toOSString().compareToIgnoreCase(o.getFile().getFullPath().toOSString());
	}
}
