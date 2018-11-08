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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.scava.plugin.codehighlighter.ICodeChunk;
import org.eclipse.scava.plugin.codehighlighter.ICodeHighlighterModel;
import org.eclipse.scava.plugin.mvc.implementation.AbstractModel;

public class CodeHighlighterModel extends AbstractModel implements ICodeHighlighterModel {
	private final Collection<ICodeChunk> codeChunks;
	private final String markerID;

	public CodeHighlighterModel(Collection<ICodeChunk> codeChunks, String markerID) {
		this.codeChunks = codeChunks;
		this.markerID = markerID;
	}

	@Override
	public Collection<ICodeChunk> getCodeChunks() {
		return Collections.unmodifiableCollection(codeChunks);
	}

	@Override
	public String getMarkerID() {
		return markerID;
	}

}
