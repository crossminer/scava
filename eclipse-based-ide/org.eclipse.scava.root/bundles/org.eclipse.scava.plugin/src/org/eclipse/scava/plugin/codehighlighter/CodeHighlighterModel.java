/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.codehighlighter;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.scava.plugin.mvc.model.Model;

public class CodeHighlighterModel extends Model {
	private final Collection<CodeChunk> codeChunks;
	private final String markerID;

	public CodeHighlighterModel(Collection<CodeChunk> codeChunks, String markerID) {
		this.codeChunks = codeChunks;
		this.markerID = markerID;
	}

	public Collection<CodeChunk> getCodeChunks() {
		return Collections.unmodifiableCollection(codeChunks);
	}

	public String getMarkerID() {
		return markerID;
	}

}
