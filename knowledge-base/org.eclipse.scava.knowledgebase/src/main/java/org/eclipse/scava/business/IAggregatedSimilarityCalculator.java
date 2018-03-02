/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business;

import java.util.List;
import java.util.Map;

import org.eclipse.scava.business.model.Artifact;

import com.google.common.collect.Table;

/**
 * @author Juri Di Rocco
 *
 */
public interface IAggregatedSimilarityCalculator extends ISimilarityCalculator{
	Table<String, String, Double> calculateAggregatedSimilarityValues(List<Artifact> atifacts, Map<String, String> params);
}
