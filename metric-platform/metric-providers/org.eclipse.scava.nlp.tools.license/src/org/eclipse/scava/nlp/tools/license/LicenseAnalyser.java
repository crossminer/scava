/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.nlp.tools.license.prediction.LicensePrediction;
import org.eclipse.scava.nlp.tools.license.prediction.LicensePredictionCollection;
import org.eclipse.scava.nlp.tools.license.processing.NgramTokeniser;
import org.eclipse.scava.nlp.tools.license.processing.TextProcessor;
import org.eclipse.scava.nlp.tools.license.ranking.Rank;
import org.eclipse.scava.nlp.tools.license.ranking.Ranking;
import org.eclipse.scava.nlp.tools.license.utils.Utils;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPrediction;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public class LicenseAnalyser {
	protected static OssmeterLogger logger;
	private static Map<String, Map<String, Double>> groupLanguageModel, singleLanguageModel, headerLanguageModel;
	private static Map<String, Integer> headerLicenseStats;
	private static Map<String, List<License>> licenseGroupHierarchy;
	
	private static final double modifier = -100;

	static {

		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.classifiers.licenseclassifier");
		groupLanguageModel = LicenseAnalyserSingleton.getInstance().getGroupLicenseModel();

		singleLanguageModel = LicenseAnalyserSingleton.getInstance().getIndividualLicenseModel();

		headerLanguageModel = LicenseAnalyserSingleton.getInstance().getLicenseHeaderModel();
		headerLicenseStats = LicenseAnalyserSingleton.getInstance().getLiceseHeaderStats();

		licenseGroupHierarchy = LicenseAnalyserSingleton.getInstance().getLicenseHierarchy();
		
	}
	

	public static Map<String, List<License>> getHierarchy() {
		return licenseGroupHierarchy;
	}

	public static Map<String, Map<String, Double>> getSingleLicenseLanguageModel() {

		return singleLanguageModel;
	}

	public static Map<String, Map<String, Double>> getLicenseHeaderLanguageModel() {

		return headerLanguageModel;
	}

	public static LicensePredictionCollection predict(LicensePredictionCollection licensePredictionCollection) {
		boolean predictionsSet = false;
		for(SingleLabelPrediction licensePrediction : licensePredictionCollection.getPredictionCollection())
		{
			predict((LicensePrediction) licensePrediction);
			if(predictionsSet==false)
				predictionsSet=true;
		}
		licensePredictionCollection.setPredictionSet(predictionsSet);
		return licensePredictionCollection;
	}

	public static LicensePrediction predict(LicensePrediction licensePrediction) {


		if (!licensePrediction.getText().isEmpty()) {
			TextProcessor processedText;
			NgramTokeniser tokens;

			processedText = new TextProcessor(licensePrediction.getText());
			tokens = new NgramTokeniser(processedText.getProcessedText());

			return analyse(tokens.getNgrams(), licensePrediction);

		} else {

			licensePrediction.setLabel("");
			licensePrediction.setLicenseFound(false);
			return licensePrediction;
		}
	}

	private static LicensePrediction analyse(List<String> ngrams,
			LicensePrediction lincensePrediction) {

		logger.info("Starting License Analysis");

		Map<String, Double> scores = new HashMap<>();
		Map<String, Rank> groupRankings, indiviudalRankings, headerRankings = new HashMap<>();

		// Rankings are always returned sorted (closest to zero first)
		logger.info("Analysing License Groups");
		groupRankings = analyseGroupLicences(scores, ngrams);

		if (!groupRankings.isEmpty()) {

			Rank topGroup = Ranking.getTopRank(groupRankings);
			String groupName = topGroup.getName();

			logger.info("Analysing Licenses within group");
			indiviudalRankings = analyseIndividualLicenses(topGroup, ngrams);

			if (!indiviudalRankings.isEmpty()) {

				Rank topIndividual = Ranking.getTopRank(indiviudalRankings);

				lincensePrediction = topIndividual.toPrediction(lincensePrediction);
				lincensePrediction.setIsGroup(false);
				lincensePrediction.setIsHeader(false);
				lincensePrediction.setLicenseFound(true);
				lincensePrediction.setLicenseGroup(groupName);

				logger.info("Analysing those licenses that have headers");
				headerRankings = analyseLicenseHeaders(indiviudalRankings, ngrams);

				if (!headerRankings.isEmpty()) {

					Rank topHeader = Ranking.getTopRank(headerRankings);

					if (topHeader.getScore() > topIndividual.getScore()) {

						lincensePrediction = topHeader.toPrediction(lincensePrediction);
						lincensePrediction.setIsGroup(false);
						lincensePrediction.setIsHeader(true);
						lincensePrediction.setLicenseFound(true);
						lincensePrediction.setLicenseGroup(groupName);

						return lincensePrediction;// returns header

					} 
				}

				return lincensePrediction;// returns individual license

			} else {

				lincensePrediction = topGroup.toPrediction(lincensePrediction);
				lincensePrediction.setLicenseFound(true);
				lincensePrediction.setIsGroup(true);
				lincensePrediction.setLicenseGroup(groupName);

				return lincensePrediction; // returns group
			}

		}
		lincensePrediction.setLabel("");
		lincensePrediction.setLicenseFound(false);
		lincensePrediction.setIsGroup(false);
		lincensePrediction.setLicenseGroup("");
		return lincensePrediction;// nothing found
	}

	private static Map<String, Rank> analyseLicenseHeaders(Map<String, Rank> headerRankings, List<String> ngrams) {

		Map<String, Integer> ngramsMatched = new HashMap<>();
		Map<String, Double> scores = new HashMap<>();

		for (String license : headerRankings.keySet()) {

			if (headerLicenseStats.containsKey(license)) {

				for (String ngram : ngrams) {

					if (headerLanguageModel.containsKey(ngram)) {

						if (headerLanguageModel.get(ngram).containsKey(license)) {
							// tracks score
							scores = Utils.addScore(license, headerLanguageModel.get(ngram).get(license), scores);
							// keep track of match
							ngramsMatched = Utils.tracknGrams(license, ngramsMatched);
						} else {
							scores = Utils.addScore(license, modifier, scores);
						}
					} else {
						scores = Utils.addScore(license, modifier, scores);
					}
				}
			}
		}

		return Ranking.calculateIndividualRank(scores, ngramsMatched, ngrams.size(), modifier);
	}

	private static Map<String, Rank> analyseGroupLicences(Map<String, Double> scores, List<String> ngrams) {

		Map<String, Integer> ngramsMatched = new HashMap<>();

		
		for (String ngram : ngrams) {
			if (groupLanguageModel.containsKey(ngram)) {
				for (String license : licenseGroupHierarchy.keySet()) {
					if (groupLanguageModel.get(ngram).containsKey(license)) {
						// tracks score
						scores = Utils.addScore(license, groupLanguageModel.get(ngram).get(license), scores);
						// keep track of match
						ngramsMatched = Utils.tracknGrams(license, ngramsMatched);
					} else {
						scores = Utils.addScore(license, modifier, scores);
					}
				}
			} else {
				for (String license : licenseGroupHierarchy.keySet()) {
					scores = Utils.addScore(license, modifier, scores);
				}
			}
		}
		return Ranking.calculateGroupRank(scores, ngramsMatched, ngrams.size(), modifier);

	}

	private static Map<String, Rank> analyseIndividualLicenses(Rank rank, List<String> ngrams) {

		Map<String, Double> scores = new HashMap<>();
		Map<String, Integer> ngramsMatched = new HashMap<>();

		for (License license : LicenseAnalyser.getHierarchy().get(rank.getName())) {

			for (String ngram : ngrams) {

				if (singleLanguageModel.containsKey(ngram)) {

					if (singleLanguageModel.get(ngram).containsKey(license.getLicenseId())) {
						// tracks score
						scores = Utils.addScore(license.getLicenseId(),
								singleLanguageModel.get(ngram).get(license.getLicenseId()), scores);
						// keep track of match
						ngramsMatched = Utils.tracknGrams(license.getLicenseId(), ngramsMatched);
					} else {
						scores = Utils.addScore(license.getLicenseId(), modifier, scores);
					}
				} else {
					scores = Utils.addScore(license.getLicenseId(), modifier, scores);
				}
			}

		}

		return Ranking.calculateIndividualRank(scores, ngramsMatched, ngrams.size(), modifier);

	}

	

}
