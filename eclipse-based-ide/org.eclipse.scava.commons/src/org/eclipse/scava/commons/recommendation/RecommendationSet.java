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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Provides a container for {@link Recommendation}s.
 *
 */
public class RecommendationSet implements Iterable<Recommendation> {
	private final List<Recommendation> recommendations;

	public RecommendationSet(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	@Override
	public Iterator<Recommendation> iterator() {
		return recommendations.iterator();
	}

	public List<Recommendation> asList() {
		return Collections.unmodifiableList(recommendations);
	}

	@Override
	public String toString() {
		return "RecommendationSet [recommendations=" + recommendations + "]";
	}
}
