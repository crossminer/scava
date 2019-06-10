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
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;
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

	public static SingleLabelPredictionCollection predict(LicensePredictionCollection licensePredictionCollection) {
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

			licensePrediction.setLabel("No license found");
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
		lincensePrediction.setLabel("no license found");
		lincensePrediction.setLicenseFound(false);
		lincensePrediction.setIsGroup(false);
		lincensePrediction.setLicenseGroup("");
		return lincensePrediction;// nothing found
	}

	private static Map<String, Rank> analyseLicenseHeaders(Map<String, Rank> headerRankings, List<String> ngrams) {

		Map<String, Integer> ngramsMatched = new HashMap<>();
		Map<String, Integer> ngramsNotMatched = new HashMap<>();
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
							// keep track of not matched
							ngramsNotMatched = Utils.tracknGrams(license, ngramsNotMatched);
						}
					} else {
						scores = Utils.addScore(license, modifier, scores);
						// keep track of not matched
						ngramsNotMatched = Utils.tracknGrams(license, ngramsNotMatched);
					}
				}
			}
		}

		return Ranking.calculateIndividualRank(scores, ngramsMatched, ngramsNotMatched, ngrams.size(), modifier);
	}

	private static Map<String, Rank> analyseGroupLicences(Map<String, Double> scores, List<String> ngrams) {

		Map<String, Integer> ngramsMatched = new HashMap<>();
		Map<String, Integer> ngramsNotMatched = new HashMap<>();

		for (String license : licenseGroupHierarchy.keySet()) {
			for (String ngram : ngrams) {
				if (groupLanguageModel.containsKey(ngram)) {
					if (groupLanguageModel.get(ngram).containsKey(license)) {
						// tracks score
						scores = Utils.addScore(license, groupLanguageModel.get(ngram).get(license), scores);
						// keep track of match
						ngramsMatched = Utils.tracknGrams(license, ngramsMatched);
					} else {
						scores = Utils.addScore(license, modifier, scores);
						// keep track of not matched
						ngramsNotMatched = Utils.tracknGrams(license, ngramsNotMatched);
					}
				} else {
					scores = Utils.addScore(license, modifier, scores);
					// keep track of not matched
					ngramsNotMatched = Utils.tracknGrams(license, ngramsNotMatched);
				}
			}
		}
		return Ranking.calculateGroupRank(scores, ngramsMatched, ngramsNotMatched, ngrams.size(), modifier);

	}

	private static Map<String, Rank> analyseIndividualLicenses(Rank rank, List<String> ngrams) {

		Map<String, Double> scores = new HashMap<>();
		Map<String, Integer> ngramsMatched = new HashMap<>();
		Map<String, Integer> ngramsNotMatched = new HashMap<>();

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
						// keep track of not matched
						ngramsNotMatched = Utils.tracknGrams(license.getLicenseId(), ngramsNotMatched);
					}
				} else {
					scores = Utils.addScore(license.getLicenseId(), modifier, scores);
					// keep track of not matched
					ngramsNotMatched = Utils.tracknGrams(license.getLicenseId(), ngramsNotMatched);
				}
			}

		}

		return Ranking.calculateIndividualRank(scores, ngramsMatched, ngramsNotMatched, ngrams.size(), modifier);

	}

	public static void main(String[] args) {

		
		String test0 = "\"\"\"Load configuration from guesslang `config` directory\"\"\"\n" + 
				"\n" + 
				"import json\n" + 
				"import logging.config\n" + 
				"from pathlib import Path\n" + 
				"import platform\n" + 
				"from typing import cast, Dict, Any, Tuple, Optional\n" + 
				"\n" + 
				"from pkg_resources import (\n" + 
				"    Requirement, resource_string, resource_filename, DistributionNotFound)\n" + 
				"\n" + 
				"import tensorflow as tf\n" + 
				"\n" + 
				"\n" + 
				"LOGGER = logging.getLogger(__name__)\n" + 
				"\n" + 
				"PACKAGE = Requirement.parse('guesslang')\n" + 
				"DATADIR = 'guesslang/data/{}'\n" + 
				"DATA_FALLBACK = Path(__file__).parent.joinpath('data')\n" + 
				"\n" + 
				"\n" + 
				"class ColorLogFormatter(logging.Formatter):\n" + 
				"    \"\"\"Logging formatter that prints pretty colored log messages\"\"\"\n" + 
				"\n" + 
				"    STYLE = {\n" + 
				"        # Log messages styles\n" + 
				"        'DEBUG': '\\033[94m',\n" + 
				"        'INFO': '\\033[0m',\n" + 
				"        'WARNING': '\\033[93m',\n" + 
				"        'ERROR': '\\033[1;91m',\n" + 
				"        'CRITICAL': '\\033[1;95m',\n" + 
				"        # Other styles\n" + 
				"        'LEVEL': '\\033[1m',\n" + 
				"        'END': '\\033[0m',\n" + 
				"    }\n" + 
				"\n" + 
				"    def format(self, record: logging.LogRecord) -> str:\n" + 
				"        \"\"\"Format log records to produce colored messages.\n" + 
				"        :param record: log record\n" + 
				"        :return: log message\n" + 
				"        \"\"\"\n" + 
				"        if platform.system() != 'Linux':  # Avoid funny logs on Windows & MacOS\n" + 
				"            return super().format(record)\n" + 
				"\n" + 
				"        record.msg = (\n" + 
				"            self.STYLE[record.levelname] + record.msg + self.STYLE['END'])\n" + 
				"        record.levelname = (\n" + 
				"            self.STYLE['LEVEL'] + record.levelname + self.STYLE['END'])\n" + 
				"        return super().format(record)\n" + 
				"\n" + 
				"\n" + 
				"def config_logging(debug: bool = False) -> None:\n" + 
				"    \"\"\"Set-up application and `tensorflow` logging.\n" + 
				"    :param debug: show or hide debug messages\n" + 
				"    \"\"\"\n" + 
				"    if debug:\n" + 
				"        level = 'DEBUG'\n" + 
				"        tf_level = tf.logging.INFO\n" + 
				"    else:\n" + 
				"        level = 'INFO'\n" + 
				"        tf_level = tf.logging.ERROR\n" + 
				"\n" + 
				"    logging_config = config_dict('logging.json')\n" + 
				"    for logger in logging_config['loggers'].values():\n" + 
				"        logger['level'] = level\n" + 
				"\n" + 
				"    logging.config.dictConfig(logging_config)\n" + 
				"    tf.logging.set_verbosity(tf_level)\n" + 
				"\n" + 
				"\n" + 
				"def config_dict(name: str) -> Dict[str, Any]:\n" + 
				"    \"\"\"Load a JSON configuration dict from Guesslang config directory.\n" + 
				"    :param name: the JSON file name.\n" + 
				"    :return: configuration\n" + 
				"    \"\"\"\n" + 
				"    try:\n" + 
				"        content = resource_string(PACKAGE, DATADIR.format(name)).decode()\n" + 
				"    except DistributionNotFound as error:\n" + 
				"        LOGGER.warning(\"Cannot load %s from packages: %s\", name, error)\n" + 
				"        content = DATA_FALLBACK.joinpath(name).read_text()\n" + 
				"\n" + 
				"    return cast(Dict[str, Any], json.loads(content))\n" + 
				"\n" + 
				"\n" + 
				"def model_info(model_dir: Optional[str] = None) -> Tuple[str, bool]:\n" + 
				"    \"\"\"Retrieve Guesslang model directory name,\n" + 
				"    and tells if it is the default model.\n" + 
				"    :param model_dir: model location, if `None` default model is selected\n" + 
				"    :return: selected model directory with an indication\n" + 
				"        that the model is the default or not\n" + 
				"    \"\"\"\n" + 
				"    if model_dir is None:\n" + 
				"        try:\n" + 
				"            model_dir = resource_filename(PACKAGE, DATADIR.format('model'))\n" + 
				"        except DistributionNotFound as error:\n" + 
				"            LOGGER.warning(\"Cannot load model from packages: %s\", error)\n" + 
				"            model_dir = str(DATA_FALLBACK.joinpath('model').absolute())\n" + 
				"        is_default_model = True\n" + 
				"    else:\n" + 
				"        is_default_model = False\n" + 
				"\n" + 
				"    model_path = Path(model_dir)\n" + 
				"    model_path.mkdir(exist_ok=True)\n" + 
				"    LOGGER.debug(\"Using model: %s, default: %s\", model_path, is_default_model)\n" + 
				"\n" + 
				"return (model_dir, is_default_model)";

		// GPL (header)
		String test1 = "# Copyright (C) 2009-2015 Luis Adrián Cabrera-Diego;Juan-Manuel Torres-Moreno\n" + "#\n"
				+ "# This program is free software: you can redistribute it and/or modify\n"
				+ "# it under the terms of the GNU General Public License as published by\n"
				+ "# the Free Software Foundation, either version 3 of the License, or\n"
				+ "# (at your option) any later version.\n" + "#\n"
				+ "# This program is distributed in the hope that it will be useful,\n"
				+ "# but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
				+ "# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n"
				+ "# GNU General Public License for more details.\n" + "#\n"
				+ "# You should have received a copy of the GNU General Public License\n"
				+ "# along with this program. If not, see <http://www.gnu.org/licenses/>.\n" + "#\n"
				+ "# File: n-grams1.perl\n" + "# V 1.1 27 january 2016\n" + "# Luis Adrián Cabrera Diego:\n"
				+ "# LIA/UAPV - Avignon, France\n" + "# Adoc Talent Management - Paris, France\n"
				+ "# adrian.9819@gmail.com\n" + "#";

		// NO License
		String test2 = "fastText\n" + "\n"
				+ "fastText is a library for efficient learning of word representations and sentence classification.\n"
				+ "\n" + "CircleCI\n" + "Table of contents\n" + "\n" + " Resources\n" + "Models\n"
				+ " Supplementary data\n" + " FAQ\n" + " Cheatsheet\n" + " Requirements\n" + " Building fastText\n"
				+ " Getting the source code\n" + " Building fastText using make (preferred)\n"
				+ " Building fastText using cmake\n" + " Building fastText for Python\n" + " Example use cases\n"
				+ " Word representation learning\n" + " Obtaining word vectors for out-of-vocabulary words\n"
				+ " Text classification\n" + " Full documentation\n" + " References\n"
				+ " Enriching Word Vectors with Subword Information\n"
				+ " Bag of Tricks for Efficient Text Classification\n"
				+ " FastText.zip: Compressing text classification models\n" + " Join the fastText community\n"
				+ " License\n" + "\n" + "Resources";

		// EPL
		String test3 = "Eclipse Public License - v 2.0\n" + "\n"
				+ "    THE ACCOMPANYING PROGRAM IS PROVIDED UNDER THE TERMS OF THIS ECLIPSE\n"
				+ "    PUBLIC LICENSE (\"AGREEMENT\"). ANY USE, REPRODUCTION OR DISTRIBUTION\n"
				+ "    OF THE PROGRAM CONSTITUTES RECIPIENT'S ACCEPTANCE OF THIS AGREEMENT.\n" + "\n"
				+ "1. DEFINITIONS\n" + "\n" + "\"Contribution\" means:\n" + "\n"
				+ "  a) in the case of the initial Contributor, the initial content\n"
				+ "     Distributed under this Agreement, and\n" + "\n"
				+ "  b) in the case of each subsequent Contributor:\n" + "     i) changes to the Program, and\n"
				+ "     ii) additions to the Program;\n"
				+ "  where such changes and/or additions to the Program originate from\n"
				+ "  and are Distributed by that particular Contributor. A Contribution\n"
				+ "  \"originates\" from a Contributor if it was added to the Program by\n"
				+ "  such Contributor itself or anyone acting on such Contributor's behalf.\n"
				+ "  Contributions do not include changes or additions to the Program that\n"
				+ "  are not Modified Works.\n" + "\n"
				+ "\"Contributor\" means any person or entity that Distributes the Program.\n" + "\n"
				+ "\"Licensed Patents\" mean patent claims licensable by a Contributor which\n"
				+ "are necessarily infringed by the use or sale of its Contribution alone\n"
				+ "or when combined with the Program.\n" + "\n"
				+ "\"Program\" means the Contributions Distributed in accordance with this\n" + "Agreement.\n" + "\n"
				+ "\"Recipient\" means anyone who receives the Program under this Agreement\n"
				+ "or any Secondary License (as applicable), including Contributors.\n" + "\n"
				+ "\"Derivative Works\" shall mean any work, whether in Source Code or other\n"
				+ "form, that is based on (or derived from) the Program and for which the\n"
				+ "editorial revisions, annotations, elaborations, or other modifications\n"
				+ "represent, as a whole, an original work of authorship.\n" + "\n"
				+ "\"Modified Works\" shall mean any work in Source Code or other form that\n"
				+ "results from an addition to, deletion from, or modification of the\n"
				+ "contents of the Program, including, for purposes of clarity any new file\n"
				+ "in Source Code form that contains any contents of the Program. Modified\n"
				+ "Works shall not include works that contain only declarations,\n"
				+ "interfaces, types, classes, structures, or files of the Program solely\n"
				+ "in each case in order to link to, bind by name, or subclass the Program\n"
				+ "or Modified Works thereof.\n" + "\n"
				+ "\"Distribute\" means the acts of a) distributing or b) making available\n"
				+ "in any manner that enables the transfer of a copy.\n" + "\n"
				+ "\"Source Code\" means the form of a Program preferred for making\n"
				+ "modifications, including but not limited to software source code,\n"
				+ "documentation source, and configuration files.\n" + "\n"
				+ "\"Secondary License\" means either the GNU General Public License,\n"
				+ "Version 2.0, or any later versions of that license, including any\n"
				+ "exceptions or additional permissions as identified by the initial\n" + "Contributor.\n" + "\n"
				+ "2. GRANT OF RIGHTS\n" + "\n"
				+ "  a) Subject to the terms of this Agreement, each Contributor hereby\n"
				+ "  grants Recipient a non-exclusive, worldwide, royalty-free copyright\n"
				+ "  license to reproduce, prepare Derivative Works of, publicly display,\n"
				+ "  publicly perform, Distribute and sublicense the Contribution of such\n"
				+ "  Contributor, if any, and such Derivative Works.\n" + "\n"
				+ "  b) Subject to the terms of this Agreement, each Contributor hereby\n"
				+ "  grants Recipient a non-exclusive, worldwide, royalty-free patent\n"
				+ "  license under Licensed Patents to make, use, sell, offer to sell,\n"
				+ "  import and otherwise transfer the Contribution of such Contributor,\n"
				+ "  if any, in Source Code or other form. This patent license shall\n"
				+ "  apply to the combination of the Contribution and the Program if, at\n"
				+ "  the time the Contribution is added by the Contributor, such addition\n"
				+ "  of the Contribution causes such combination to be covered by the\n"
				+ "  Licensed Patents. The patent license shall not apply to any other\n"
				+ "  combinations which include the Contribution. No hardware per se is\n" + "  licensed hereunder.\n"
				+ "\n" + "  c) Recipient understands that although each Contributor grants the\n"
				+ "  licenses to its Contributions set forth herein, no assurances are\n"
				+ "  provided by any Contributor that the Program does not infringe the\n"
				+ "  patent or other intellectual property rights of any other entity.\n"
				+ "  Each Contributor disclaims any liability to Recipient for claims\n"
				+ "  brought by any other entity based on infringement of intellectual\n"
				+ "  property rights or otherwise. As a condition to exercising the\n"
				+ "  rights and licenses granted hereunder, each Recipient hereby\n"
				+ "  assumes sole responsibility to secure any other intellectual\n"
				+ "  property rights needed, if any. For example, if a third party\n"
				+ "  patent license is required to allow Recipient to Distribute the\n"
				+ "  Program, it is Recipient's responsibility to acquire that license\n"
				+ "  before distributing the Program.\n" + "\n"
				+ "  d) Each Contributor represents that to its knowledge it has\n"
				+ "  sufficient copyright rights in its Contribution, if any, to grant\n"
				+ "  the copyright license set forth in this Agreement.\n" + "\n"
				+ "  e) Notwithstanding the terms of any Secondary License, no\n"
				+ "  Contributor makes additional grants to any Recipient (other than\n"
				+ "  those set forth in this Agreement) as a result of such Recipient's\n"
				+ "  receipt of the Program under the terms of a Secondary License\n"
				+ "  (if permitted under the terms of Section 3).\n" + "\n" + "3. REQUIREMENTS\n" + "\n"
				+ "3.1 If a Contributor Distributes the Program in any form, then:\n" + "\n"
				+ "  a) the Program must also be made available as Source Code, in\n"
				+ "  accordance with section 3.2, and the Contributor must accompany\n"
				+ "  the Program with a statement that the Source Code for the Program\n"
				+ "  is available under this Agreement, and informs Recipients how to\n"
				+ "  obtain it in a reasonable manner on or through a medium customarily\n"
				+ "  used for software exchange; and\n" + "\n"
				+ "  b) the Contributor may Distribute the Program under a license\n"
				+ "  different than this Agreement, provided that such license:\n"
				+ "     i) effectively disclaims on behalf of all other Contributors all\n"
				+ "     warranties and conditions, express and implied, including\n"
				+ "     warranties or conditions of title and non-infringement, and\n"
				+ "     implied warranties or conditions of merchantability and fitness\n"
				+ "     for a particular purpose;\n" + "\n"
				+ "     ii) effectively excludes on behalf of all other Contributors all\n"
				+ "     liability for damages, including direct, indirect, special,\n"
				+ "     incidental and consequential damages, such as lost profits;\n" + "\n"
				+ "     iii) does not attempt to limit or alter the recipients' rights\n"
				+ "     in the Source Code under section 3.2; and\n" + "\n"
				+ "     iv) requires any subsequent distribution of the Program by any\n"
				+ "     party to be under a license that satisfies the requirements\n" + "     of this section 3.\n"
				+ "\n" + "3.2 When the Program is Distributed as Source Code:\n" + "\n"
				+ "  a) it must be made available under this Agreement, or if the\n"
				+ "  Program (i) is combined with other material in a separate file or\n"
				+ "  files made available under a Secondary License, and (ii) the initial\n"
				+ "  Contributor attached to the Source Code the notice described in\n"
				+ "  Exhibit A of this Agreement, then the Program may be made available\n"
				+ "  under the terms of such Secondary Licenses, and\n" + "\n"
				+ "  b) a copy of this Agreement must be included with each copy of\n" + "  the Program.\n" + "\n"
				+ "3.3 Contributors may not remove or alter any copyright, patent,\n"
				+ "trademark, attribution notices, disclaimers of warranty, or limitations\n"
				+ "of liability (\"notices\") contained within the Program from any copy of\n"
				+ "the Program which they Distribute, provided that Contributors may add\n"
				+ "their own appropriate notices.\n" + "\n" + "4. COMMERCIAL DISTRIBUTION\n" + "\n"
				+ "Commercial distributors of software may accept certain responsibilities\n"
				+ "with respect to end users, business partners and the like. While this\n"
				+ "license is intended to facilitate the commercial use of the Program,\n"
				+ "the Contributor who includes the Program in a commercial product\n"
				+ "offering should do so in a manner which does not create potential\n"
				+ "liability for other Contributors. Therefore, if a Contributor includes\n"
				+ "the Program in a commercial product offering, such Contributor\n"
				+ "(\"Commercial Contributor\") hereby agrees to defend and indemnify every\n"
				+ "other Contributor (\"Indemnified Contributor\") against any losses,\n"
				+ "damages and costs (collectively \"Losses\") arising from claims, lawsuits\n"
				+ "and other legal actions brought by a third party against the Indemnified\n"
				+ "Contributor to the extent caused by the acts or omissions of such\n"
				+ "Commercial Contributor in connection with its distribution of the Program\n"
				+ "in a commercial product offering. The obligations in this section do not\n"
				+ "apply to any claims or Losses relating to any actual or alleged\n"
				+ "intellectual property infringement. In order to qualify, an Indemnified\n"
				+ "Contributor must: a) promptly notify the Commercial Contributor in\n"
				+ "writing of such claim, and b) allow the Commercial Contributor to control,\n"
				+ "and cooperate with the Commercial Contributor in, the defense and any\n"
				+ "related settlement negotiations. The Indemnified Contributor may\n"
				+ "participate in any such claim at its own expense.\n" + "\n"
				+ "For example, a Contributor might include the Program in a commercial\n"
				+ "product offering, Product X. That Contributor is then a Commercial\n"
				+ "Contributor. If that Commercial Contributor then makes performance\n"
				+ "claims, or offers warranties related to Product X, those performance\n"
				+ "claims and warranties are such Commercial Contributor's responsibility\n"
				+ "alone. Under this section, the Commercial Contributor would have to\n"
				+ "defend claims against the other Contributors related to those performance\n"
				+ "claims and warranties, and if a court requires any other Contributor to\n"
				+ "pay any damages as a result, the Commercial Contributor must pay\n" + "those damages.\n" + "\n"
				+ "5. NO WARRANTY\n" + "\n" + "EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, AND TO THE EXTENT\n"
				+ "PERMITTED BY APPLICABLE LAW, THE PROGRAM IS PROVIDED ON AN \"AS IS\"\n"
				+ "BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, EITHER EXPRESS OR\n"
				+ "IMPLIED INCLUDING, WITHOUT LIMITATION, ANY WARRANTIES OR CONDITIONS OF\n"
				+ "TITLE, NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR\n"
				+ "PURPOSE. Each Recipient is solely responsible for determining the\n"
				+ "appropriateness of using and distributing the Program and assumes all\n"
				+ "risks associated with its exercise of rights under this Agreement,\n"
				+ "including but not limited to the risks and costs of program errors,\n"
				+ "compliance with applicable laws, damage to or loss of data, programs\n"
				+ "or equipment, and unavailability or interruption of operations.\n" + "\n"
				+ "6. DISCLAIMER OF LIABILITY\n" + "\n"
				+ "EXCEPT AS EXPRESSLY SET FORTH IN THIS AGREEMENT, AND TO THE EXTENT\n"
				+ "PERMITTED BY APPLICABLE LAW, NEITHER RECIPIENT NOR ANY CONTRIBUTORS\n"
				+ "SHALL HAVE ANY LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,\n"
				+ "EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING WITHOUT LIMITATION LOST\n"
				+ "PROFITS), HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n"
				+ "CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n"
				+ "ARISING IN ANY WAY OUT OF THE USE OR DISTRIBUTION OF THE PROGRAM OR THE\n"
				+ "EXERCISE OF ANY RIGHTS GRANTED HEREUNDER, EVEN IF ADVISED OF THE\n"
				+ "POSSIBILITY OF SUCH DAMAGES.\n" + "\n" + "7. GENERAL\n" + "\n"
				+ "If any provision of this Agreement is invalid or unenforceable under\n"
				+ "applicable law, it shall not affect the validity or enforceability of\n"
				+ "the remainder of the terms of this Agreement, and without further\n"
				+ "action by the parties hereto, such provision shall be reformed to the\n"
				+ "minimum extent necessary to make such provision valid and enforceable.\n" + "\n"
				+ "If Recipient institutes patent litigation against any entity\n"
				+ "(including a cross-claim or counterclaim in a lawsuit) alleging that the\n"
				+ "Program itself (excluding combinations of the Program with other software\n"
				+ "or hardware) infringes such Recipient's patent(s), then such Recipient's\n"
				+ "rights granted under Section 2(b) shall terminate as of the date such\n" + "litigation is filed.\n"
				+ "\n" + "All Recipient's rights under this Agreement shall terminate if it\n"
				+ "fails to comply with any of the material terms or conditions of this\n"
				+ "Agreement and does not cure such failure in a reasonable period of\n"
				+ "time after becoming aware of such noncompliance. If all Recipient's\n"
				+ "rights under this Agreement terminate, Recipient agrees to cease use\n"
				+ "and distribution of the Program as soon as reasonably practicable.\n"
				+ "However, Recipient's obligations under this Agreement and any licenses\n"
				+ "granted by Recipient relating to the Program shall continue and survive.\n" + "\n"
				+ "Everyone is permitted to copy and distribute copies of this Agreement,\n"
				+ "but in order to avoid inconsistency the Agreement is copyrighted and\n"
				+ "may only be modified in the following manner. The Agreement Steward\n"
				+ "reserves the right to publish new versions (including revisions) of\n"
				+ "this Agreement from time to time. No one other than the Agreement\n"
				+ "Steward has the right to modify this Agreement. The Eclipse Foundation\n"
				+ "is the initial Agreement Steward. The Eclipse Foundation may assign the\n"
				+ "responsibility to serve as the Agreement Steward to a suitable separate\n"
				+ "entity. Each new version of the Agreement will be given a distinguishing\n"
				+ "version number. The Program (including Contributions) may always be\n"
				+ "Distributed subject to the version of the Agreement under which it was\n"
				+ "received. In addition, after a new version of the Agreement is published,\n"
				+ "Contributor may elect to Distribute the Program (including its\n"
				+ "Contributions) under the new version.\n" + "\n"
				+ "Except as expressly stated in Sections 2(a) and 2(b) above, Recipient\n"
				+ "receives no rights or licenses to the intellectual property of any\n"
				+ "Contributor under this Agreement, whether expressly, by implication,\n"
				+ "estoppel or otherwise. All rights in the Program not expressly granted\n"
				+ "under this Agreement are reserved. Nothing in this Agreement is intended\n"
				+ "to be enforceable by any entity that is not a Contributor or Recipient.\n"
				+ "No third-party beneficiary rights are created under this Agreement.\n" + "\n"
				+ "Exhibit A - Form of Secondary Licenses Notice\n" + "\n"
				+ "\"This Source Code may also be made available under the following \n"
				+ "Secondary Licenses when the conditions for such availability set forth \n"
				+ "in the Eclipse Public License, v. 2.0 are satisfied: {name license(s),\n"
				+ "version(s), and exceptions or additional permissions here}.\"\n" + "\n"
				+ "  Simply including a copy of this Agreement, including this Exhibit A\n"
				+ "  is not sufficient to license the Source Code under Secondary Licenses.\n" + "\n"
				+ "  If it is not possible or desirable to put the notice in a particular\n"
				+ "  file, then You may include the notice in a location (such as a LICENSE\n"
				+ "  file in a relevant directory) where a recipient would be likely to\n"
				+ "  look for such a notice.\n" + "\n"
				+ "You may add additional accurate notices of copyright ownership.";

		String test4= "BSD License\n" + 
				"\n" + 
				"For fastText software\n" + 
				"\n" + 
				"Copyright (c) 2016-present, Facebook, Inc. All rights reserved.\n" + 
				"\n" + 
				"Redistribution and use in source and binary forms, with or without modification,\n" + 
				"are permitted provided that the following conditions are met:\n" + 
				"\n" + 
				" * Redistributions of source code must retain the above copyright notice, this\n" + 
				"   list of conditions and the following disclaimer.\n" + 
				"\n" + 
				" * Redistributions in binary form must reproduce the above copyright notice,\n" + 
				"   this list of conditions and the following disclaimer in the documentation\n" + 
				"   and/or other materials provided with the distribution.\n" + 
				"\n" + 
				" * Neither the name Facebook nor the names of its contributors may be used to\n" + 
				"   endorse or promote products derived from this software without specific\n" + 
				"   prior written permission.\n" + 
				"\n" + 
				"THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND\n" + 
				"ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED\n" + 
				"WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\n" + 
				"DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR\n" + 
				"ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\n" + 
				"(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;\n" + 
				"LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\n" + 
				"ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n" + 
				"(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\n" + 
				"SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";
		
		String test5="The contents of this file are subject to the Mozilla Public License\n" + 
				"Version 1.1 (the \"License\"); you may not use this file except in\n" + 
				"compliance with the License. You may obtain a copy of the License at\n" + 
				"https://www.mozilla.org/MPL/\n" + 
				"\n" + 
				"Software distributed under the License is distributed on an \"AS IS\"\n" + 
				"basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the\n" + 
				"License for the specific language governing rights and limitations\n" + 
				"under the License.\n" + 
				"\n";
				/*"The Original Code is from Edge Hill University.\n" + 
				"\n" + 
				"The Initial Developer of the Original Code is Daniel Campbell.\n" + 
				"Portions created by Luis Adrián Cabrera Diego are Copyright (C) Edge Hill University. All Rights Reserved.\n" + 
				"\n" + 
				"Contributor(s): Daniel Campbell, Luis Adrián Cabrera Diego.\n" + 
				"\n" + 
				"Alternatively, the contents of this file may be used under the terms\n" + 
				"of the BLABLABLA license (the  \"BLABLABLA License\"), in which case the\n" + 
				"provisions of BLABLABLA License are applicable instead of those\n" + 
				"above. If you wish to allow use of your version of this file only\n" + 
				"under the terms of the BLABLABLA License and not to allow others to use\n" + 
				"your version of this file under the MPL, indicate your decision by\n" + 
				"deleting the provisions above and replace them with the notice and\n" + 
				"other provisions required by the BLABLABLA License. If you do not delete\n" + 
				"the provisions above, a recipient may use your version of this file\n" + 
				"under either the MPL or the BLABLABLA License.";*/
		
		String test6 = "The MIT License (MIT)\n" + 
				"\n" + 
				"Copyright (c) 2017 Y. SOMDA\n" + 
				"\n" + 
				"Permission is hereby granted, free of charge, to any person obtaining a copy\n" + 
				"of this software and associated documentation files (the \"Software\"), to deal\n" + 
				"in the Software without restriction, including without limitation the rights\n" + 
				"to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" + 
				"copies of the Software, and to permit persons to whom the Software is\n" + 
				"furnished to do so, subject to the following conditions:\n" + 
				"\n" + 
				"The above copyright notice and this permission notice shall be included in all\n" + 
				"copies or substantial portions of the Software.\n" + 
				"\n" + 
				"THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" + 
				"IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" + 
				"FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" + 
				"AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" + 
				"LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" + 
				"OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" + 
				"SOFTWARE.";
		
		LicensePredictionCollection collection = new LicensePredictionCollection(4);
		
		/*collection.addText("0", test0);
		collection.addText("1", test1);
		collection.addText("2", test2);
		collection.addText("3", test3);
		collection.addText("4", test4);
		collection.addText("5", test5);*/
		collection.addText("6", test6);
		
		LicenseAnalyser.predict(collection);
		
		/*LicensePrediction prediction0 = new LicensePrediction("0", test0);
		prediction0 = LicenseAnalyser.predict(prediction0);

		LicensePrediction prediction1 = new LicensePrediction("1", test1);
		prediction1 = LicenseAnalyser.predict(prediction1);

		LicensePrediction prediction2 = new LicensePrediction("2", test2);
		prediction2 = LicenseAnalyser.predict(prediction2);

		LicensePrediction prediction3 = new LicensePrediction("3", test3);
		prediction3 = LicenseAnalyser.predict(prediction3);*/

		System.out.println("\nExample Results:");

		/*System.err.println("Prediction 0 : Has a license been found? " + prediction0.getLicenseFound());

		System.err.println("Prediction 1 : We have found a license belonging to the group "
				+ prediction1.getLicenseGroup() + ". We believe it is the license is ["
				+ prediction1.getLicenseName() + "]. Is it a header? (" + prediction1.getIsHeader() + ")");

		System.err.println("Prediction 2 : This text matched a total of " + prediction2.getNgramsMatchedPercent()
				+ " ngrams. Did it match any licenses? (" + prediction2.getLicenseFound() + ")");

		System.err.println("Prediction 3 : We have found a " + prediction3.getLicenseName()
				+ " license. It recieved a score of" + prediction3.getScore());*/
		
		HashMap<Object, String> output = collection.getIdsWithPredictedLabel();
		
		System.out.println("Finished");

	}

}
