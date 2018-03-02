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
package org.eclipse.scava.commons.recommendation.library;

import java.util.Date;

import org.eclipse.scava.commons.buildsystem.BuildSystemType;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.commons.recommendation.RecommendationKind;

/**
 * Provides a representation of library adder recommendation.
 *
 */
public abstract class AddLibraryRecommendation extends LibraryRecommendation {

	public AddLibraryRecommendation(String id, Date requestTimeStamp, RecommendationKind kind,
			BuildSystemType buildSystemType, Library target) {
		super(id, requestTimeStamp, kind, buildSystemType, target);
	}

}
