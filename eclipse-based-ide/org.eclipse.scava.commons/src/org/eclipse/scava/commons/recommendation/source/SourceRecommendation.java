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
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.commons.recommendation.source;

import java.util.Date;

import org.eclipse.scava.commons.highlight.IHighlightable;
import org.eclipse.scava.commons.recommendation.Recommendation;
import org.eclipse.scava.commons.recommendation.RecommendationKind;

/**
 * Provides a representation of a source code related recommendation.
 *
 */
public abstract class SourceRecommendation extends Recommendation implements IHighlightable {
	private final String targetFile;
	private final int startChar;
	private final int endChar;
	private transient int additionalOffset;
	
	public SourceRecommendation(String id, Date requestTimeStamp, String targetFile, int startChar, int endChar) {
		super(id, requestTimeStamp, RecommendationKind.SOURCE_REPLACE);
		this.targetFile = targetFile;
		this.startChar = startChar;
		this.endChar = endChar;
		additionalOffset = 0;
	}
	
	@Override
	public String toString() {
		return "SourceRecommendation [targetFile=" + targetFile + ", additionalOffset=" + additionalOffset
				+ ", getOriginalStartChar()=" + getOriginalStartChar() + ", getOriginalEndChar()="
				+ getOriginalEndChar() + ", getStartChar()=" + getStartChar() + ", getEndChar()=" + getEndChar()
				+ ", getId()=" + getId() + ", getRequestTimeStamp()=" + getRequestTimeStamp() + ", getKind()="
				+ getKind() + "]";
	}
	
	public String getTargetFile() {
		return targetFile;
	}
	
	public int getOriginalStartChar() {
		return startChar;
	}
	
	public int getOriginalEndChar() {
		return endChar;
	}
	
	public int getStartChar() {
		return startChar + additionalOffset;
	}
	
	public int getEndChar() {
		return endChar + additionalOffset;
	}
	
	public void offsetPosition(int offset) {
		additionalOffset += offset;
	}
}
