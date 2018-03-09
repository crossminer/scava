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
package org.eclipse.scava.commons.recommendation;

import java.util.Date;

import org.eclipse.scava.commons.ITypeRepresentedByKind;

/**
 * Provides a representation of an applicable recommendation.
 *
 */
public abstract class Recommendation implements ITypeRepresentedByKind {
	private final String id;
	private final Date requestTimeStamp;
	private final RecommendationKind kind;
	
	public Recommendation(String id, Date requestTimeStamp, RecommendationKind kind) {
		this.id = id;
		this.requestTimeStamp = requestTimeStamp;
		this.kind = kind;
	}
	
	@Override
	public String toString() {
		return "Recommendation [id=" + id + ", requestTimeStamp=" + requestTimeStamp + "]";
	}
	
	public String getId() {
		return id;
	}
	
	public Date getRequestTimeStamp() {
		return requestTimeStamp;
	}
	
	@Override
	public RecommendationKind getKind() {
		return kind;
	}
	
}
