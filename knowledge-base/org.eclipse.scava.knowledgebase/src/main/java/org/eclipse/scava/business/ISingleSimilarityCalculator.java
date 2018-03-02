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

import org.eclipse.scava.business.model.Artifact;

/**
 * @author Juri Di Rocco
 *
 */
public interface ISingleSimilarityCalculator extends ISimilarityCalculator{
	double calculateSimilarity(Artifact prj1, Artifact prj2);
}
