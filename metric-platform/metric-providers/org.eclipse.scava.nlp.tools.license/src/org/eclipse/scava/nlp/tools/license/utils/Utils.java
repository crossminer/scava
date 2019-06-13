/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license.utils;

import java.text.DecimalFormat;
import java.util.Map;

public class Utils {

	public static String formatPercent(double done, int digits) {
		DecimalFormat percentFormat = new DecimalFormat("0.0%");
		percentFormat.setDecimalSeparatorAlwaysShown(false);
		percentFormat.setMinimumFractionDigits(digits);
		percentFormat.setMaximumFractionDigits(digits);
		return percentFormat.format(done);

	}
	
	public static Map<String, Integer> tracknGrams(String key, Map<String, Integer> map) {

		if (map.containsKey(key)) {
			map.put(key, map.get(key) + 1);
		} else {
			map.put(key, 1);
		}

		return map;
	}

	
	public static double calculatePercentageNgramsMatched(int ngramsMatched, int ngramsInSource) {
		
		return ((double) ngramsMatched / (double) ngramsInSource);
	}
	
	public static Map<String, Double> addScore(String key, Double probability, Map<String, Double> scores) {

		if (scores.containsKey(key)) {
			scores.put(key, scores.get(key) + probability);
		} else {
			scores.put(key, probability);
		}

		return scores;
	}

	
	
}