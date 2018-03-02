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
package org.eclipse.scava.commons.context.sourcecode.lineinfo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.commons.context.sourcecode.lineinfo.detail.ASTDetail;

/**
 * Provides a container class for informations related to a line.
 * This class stores the raw text of the line and the detailed informations
 * about the elements of the code in the same order as they appear
 * in the source code.
 *
 */
public class LineInfo {
	private String rawText;
	private List<ASTDetail> details;
	
	public LineInfo(String rawText) {
		this.rawText = rawText;
		details = new ArrayList<>();
	}
	
	/**
	 * Adds a detail to the line.
	 * @param detail the {@link ASTDetail} detail to be added
	 */
	public void addDetail(ASTDetail detail) {
		details.add(detail);
	}
	
	public String getRawText() {
		return rawText;
	}
	
	/**
	 * Returns the details in the same order as they were added.
	 * @return the list of {@link ASTDetail}s contained in this line
	 */
	public List<ASTDetail> getDetails() {
		return details;
	}
}
