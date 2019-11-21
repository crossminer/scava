/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license.ranking;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.scava.nlp.tools.license.LicenseAnalyser;

public class Ranking {

	final static double threshold = 0.5; // cut of point
	
	final static double thresholdNgrams = 5;
	
	private static Map<String, Integer> lowestNgramCount;
	
	static
	{
		lowestNgramCount=new HashMap<String, Integer>();
		setLowestGroupNgramCount();
	}
	
	private static void setLowestGroupNgramCount() {
		
		for(String group : LicenseAnalyser.getHierarchy().keySet())
		{
			LicenseAnalyser.getHierarchy().get(group).stream().forEach(license -> {	
				if (!lowestNgramCount.containsKey(group))
					lowestNgramCount.put(group, license.getNumberOfNgrams());
				else if (license.getNumberOfNgrams() < lowestNgramCount.get(group))
					lowestNgramCount.put(group, license.getNumberOfNgrams());
			});
		}
	}

	public static Map<String, Rank> calculateGroupRank(Map<String, Double> scores, Map<String, Integer> ngramsMatched,
			Map<String, Integer> ngramsNotMatched, int ngramsInSource, double modifier) {

		// lets first remove all entries from the whose score is maximum no match value
		// as these are not interesting and that fall below the threshold
		double noMatchValue = ngramsInSource * modifier;
		
		scores.values().removeIf(v-> v==noMatchValue);
		
		ngramsMatched.keySet().removeIf(k -> !scores.containsKey(k));
		ngramsMatched.values().removeIf(v -> v<thresholdNgrams);
		
		scores.keySet().removeIf(k-> !ngramsMatched.containsKey(k));
		
		
		
		List<String> rankedLicencesPerNgramsMatched = rankNgramsMatched(ngramsMatched);

		// then lets also remove instances where the min number of ngrams for the
		// smallest license in the group < 50%

		List<String> keysToBeRemoved = new ArrayList<>();

		scores.entrySet().stream().forEach(entry -> {
			
			double result=0.0;
			if(rankedLicencesPerNgramsMatched.contains(entry.getKey()))
			{
				result = (double) ngramsMatched.get(entry.getKey()) / (double) lowestNgramCount.get(entry.getKey());
			
				result += ((double) rankedLicencesPerNgramsMatched.indexOf(entry.getKey())+1)/ (double) rankedLicencesPerNgramsMatched.size();
				result /=2.0;
			}
			if (result < threshold) {

				keysToBeRemoved.add(entry.getKey());
			}

		});

		keysToBeRemoved.stream().forEach(key -> scores.remove(key));

		return rankScores(scores, ngramsInSource, ngramsMatched, modifier);
	}
	
	private static List<String> rankNgramsMatched(Map<String, Integer> ngramsMatched)
	{
		List<String> licenseName = new ArrayList<>(ngramsMatched.size()); 
		ngramsMatched.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(e -> licenseName.add(e.getKey()));
		return licenseName;
	}

	public static Map<String, Rank> calculateHeaderRank(Map<String, Double> scores, Map<String, Integer> ngramsMatched,
			Map<String, Integer> ngramsNotMatched, int ngramsInSource, double modifier) {

		List<String> keysToBeRemoved = new ArrayList<>();

		scores.entrySet().stream().forEach(entry -> {

			AtomicInteger lowestNgramCount = new AtomicInteger();

			double result = (double) ngramsMatched.get(entry.getKey()) / (double) lowestNgramCount.get();

			if (result < 0) {

				keysToBeRemoved.add(entry.getKey());
			}

		});

		keysToBeRemoved.stream().forEach(key -> scores.remove(key));

		return rankScores(scores, ngramsInSource, ngramsMatched, modifier);

	}

	public static Map<String, Rank> calculateIndividualRank(Map<String, Double> scores,
			Map<String, Integer> ngramsMatched, Map<String, Integer> ngramsNotMatched, int ngramsInSource,
			double modifier) {

		// lets first remove all entries from the whose score is maximum no match value
		// as these are not interesting and that fall below the threshold
		double noMatchValue = ngramsInSource * modifier;
		while (scores.values().remove(noMatchValue))
			;

		List<String> keysToBeRemoved = new ArrayList<>();

		scores.entrySet().stream().forEach(entry -> {

			AtomicInteger lowestNgramCount = new AtomicInteger();

			double result = (double) ngramsMatched.get(entry.getKey()) / (double) lowestNgramCount.get();

			if (result < threshold) {

				keysToBeRemoved.add(entry.getKey());
			}

		});

		keysToBeRemoved.stream().forEach(key -> scores.remove(key));

		return rankScores(scores, ngramsInSource, ngramsMatched, modifier);

	}

	private static Map<String, Rank> rankScores(Map<String, Double> scores, int ngramsInSource,
			Map<String, Integer> ngramsMatched, double modifier) {

		Map<String, Rank> rankings = new HashMap<>();
		// Then sort
		Map<String, Double> sorted = scores.entrySet().stream().sorted(comparingByValue())
				.collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

		AtomicInteger pos = new AtomicInteger(0);

		// populate Rankings and assign initial Rank score
		sorted.entrySet().stream().forEach(entry -> {
			Rank rank = new Rank();
			rank.setName(entry.getKey());
			rank.setScore(scores.get(entry.getKey()));
			rank.setnGramsMatched(ngramsMatched.get(entry.getKey()));
			rank.setNgramsInSource(ngramsInSource);
			rank.setRankPoints(pos.get());
			rankings.put(entry.getKey(), rank);
			pos.incrementAndGet();

		});
		// Used for debugging
		// System.out.println("----------------------------");
		// sorted.entrySet().stream().forEach(e -> System.out.println(e.getKey() + " " +
		// e.getValue()));
		// System.out.println("----------------------------\n");

		return rankings;
	}

	public static Rank getTopRank(Map<String, Rank> rankings) {

		Rank rank = null;

		for (Entry<String, Rank> e : rankings.entrySet()) {

			if (rank == null) {

				rank = e.getValue();

			} else if (rank.getScore() < e.getValue().getScore()) {

				rank = e.getValue();
			}
		}

		return rank;
	}
}