/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test;

import org.eclipse.scava.test.crossrec.CROSSRECTest;
import org.eclipse.scava.test.importer.GithubImporterTest;
import org.eclipse.scava.test.manager.ClusterManagerTest;
import org.eclipse.scava.test.manager.SimilarityManagerTest;
import org.eclipse.scava.test.recommendation.providers.AlternativeLibrariesRecommendationProviderTest;
import org.eclipse.scava.test.rest.RecommenderRestTest;
import org.eclipse.scava.test.similarity.CROSSSimSimilarityCalculatorTest;
import org.eclipse.scava.test.similarity.CompoundSimilarityCalculatorTest;
import org.eclipse.scava.test.similarity.DependencySimilarityCalculatorTest;
import org.eclipse.scava.test.similarity.ReadmeSimilarityCalculatorTest;
import org.eclipse.scava.test.similarity.RepoPalCompoundSimilarityCalculatorTest;
import org.eclipse.scava.test.similarity.RepoPalStarSimilarityCalculatorTest;
import org.eclipse.scava.test.similarity.RepoPalTimeSimilarityCalculatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CompoundSimilarityCalculatorTest.class, CROSSSimSimilarityCalculatorTest.class,
		DependencySimilarityCalculatorTest.class, ReadmeSimilarityCalculatorTest.class,
		RepoPalCompoundSimilarityCalculatorTest.class, RepoPalStarSimilarityCalculatorTest.class,
		RepoPalTimeSimilarityCalculatorTest.class, SimilarityManagerTest.class, GithubImporterTest.class,
		ClusterManagerTest.class, DependencyServiceTest.class, RecommenderRestTest.class, CROSSRECTest.class,
		AlternativeLibrariesRecommendationProviderTest.class, AlternativeLibrariesRecommendationProviderTest.class})
public class AllTests {

}
