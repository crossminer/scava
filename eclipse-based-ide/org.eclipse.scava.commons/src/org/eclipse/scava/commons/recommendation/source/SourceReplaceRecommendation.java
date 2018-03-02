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

/**
 * Provides a representation of a source code modifier recommendation.
 *
 */
public class SourceReplaceRecommendation extends SourceRecommendation {
	private final String newCodeChunk;
	
	public SourceReplaceRecommendation(String id, Date requestTimeStamp, String targetFile, int startChar, int endChar, String newCode) {
		super(id, requestTimeStamp, targetFile, startChar, endChar);
		this.newCodeChunk = newCode;
	}
	
	public String getNewCode() {
		return newCodeChunk;
	}

	@Override
	public String toString() {
		return "SourceReplaceRecommendation [newCode=" + newCodeChunk + ", getNewCode()=" + getNewCode() + ", getTargetFile()=" + getTargetFile() + ", getStartChar()=" + getStartChar() + ", getEndChar()=" + getEndChar() + ", getId()=" + getId()
				+ ", getRequestTimeStamp()=" + getRequestTimeStamp() + "]";
	}
	
}
