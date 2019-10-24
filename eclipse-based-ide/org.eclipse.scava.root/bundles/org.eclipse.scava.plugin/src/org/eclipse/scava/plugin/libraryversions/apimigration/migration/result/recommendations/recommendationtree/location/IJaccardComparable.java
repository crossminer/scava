/*********************************************************************
* Copyright c 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse PublicLicense 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.libraryversions.apimigration.migration.result.recommendations.recommendationtree.location;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public interface IJaccardComparable extends Comparator<IJaccardComparable> {
	Set<PathPart> getJaccardInput();

	default float jaccardSimilarity(IJaccardComparable other) {
		Set<PathPart> a = getJaccardInput();
		Set<PathPart> b = other.getJaccardInput();

		HashSet<PathPart> common = new HashSet<>(a);
		common.retainAll(b);
		return common.size() / (float) (a.size() + b.size() - common.size());
	}

	default <T extends IJaccardComparable> T findMostJaccardSimilar(Collection<T> inputs) {
		float mostSimilarScore = 0;
		T mostSimilar = null;
		for (T input : inputs) {
			float score = jaccardSimilarity(input);
			if (mostSimilar == null || mostSimilarScore < score) {
				mostSimilarScore = score;
				mostSimilar = input;

				if (score == 1.0f) {
					break;
				}
			}
		}

		return mostSimilar;
	}

	default <T extends IJaccardComparable> Collection<T> findMostJaccardSimilars(Collection<T> inputs) {
		float mostSimilarScore = 0;
		Set<T> mostSimilars = new HashSet<>();
		for (T input : inputs) {
			float score = jaccardSimilarity(input);
			if (mostSimilars.isEmpty() || mostSimilarScore < score) {
				mostSimilarScore = score;
				mostSimilars.clear();
				mostSimilars.add(input);
			} else if (mostSimilarScore == score) {
				mostSimilars.add(input);
			}
		}

		return mostSimilars;
	}

	default <T extends IJaccardComparable> T findMostJaccardSimilar(Stream<T> inputs) {
		return inputs.max(this::compare).get();
	}

	@Override
	default int compare(IJaccardComparable o1, IJaccardComparable o2) {
		float similarity1 = jaccardSimilarity(o1);
		float similarity2 = jaccardSimilarity(o2);
		return Float.compare(similarity1, similarity2);
	}
}
