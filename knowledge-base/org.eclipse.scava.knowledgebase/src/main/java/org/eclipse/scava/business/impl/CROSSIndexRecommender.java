package org.eclipse.scava.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.scava.business.IRecommendationProvider;
import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.impl.crossindex.codetokenizers.CodeTokenizer;
import org.eclipse.scava.business.impl.crossindex.elasticsearch.ElasticSearchClient;
import org.eclipse.scava.business.impl.crossindex.rake.RAKE;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("CROSSIndexRecommender")
public class CROSSIndexRecommender implements IRecommendationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(CROSSIndexRecommender.class);
	
	private final Pattern removeCharacters = Pattern.compile("[-*'\\^&!|(){}\\[\\]\"~?:\\\\]");
	private final Pattern extraSpacing=Pattern.compile("\\h\\h+");
	
	@Value("${crossindexrecommender.hitsCodeBasedQuery}")
	private int hitsCodeBasedQuery=20;
	
	@Value("${crossindexrecommender.hitsNLBasedQuery}")
	private int hitsNLBasedQuery=20;
	
	@Value("${crossindexrecommender.fieldForCodeBasedQuery}")
	private String fieldForCodeBasedQuery="plain_text";
	
	@Value("${crossindexrecommender.fieldForNLBasedQuery}")
	private String fieldForNLBasedQuery="plain_text";
	
	@Value("${crossindexrecommender.fieldRecommendation}")
	private String fieldRecommendation="plain_text";
	
	@Value("${crossindexrecommender.elasticsearch.hostname}")
	private String hostname="elasticsearch";
	
	@Value("${crossindexrecommender.elasticsearch.scheme}")
	private String scheme="https";
	
	@Value("${crossindexrecommender.elasticsearch.port}")
	private int port=9200;
	
	private ElasticSearchClient client;
	
	public static void main(String[] args) {
		CROSSIndexRecommender crossIndexRecommender=new CROSSIndexRecommender();
		String query = "/*******************************************************************************\n"
				+ " * Copyright (C) 2017 University of L'Aquila\n" + " * \n"
				+ " * This program and the accompanying materials are made\n"
				+ " * available under the terms of the Eclipse Public License 2.0\n"
				+ " * which is available at https://www.eclipse.org/legal/epl-2.0/\n" + " * \n"
				+ " * SPDX-License-Identifier: EPL-2.0\n"
				+ " ******************************************************************************/\n"
				+ "package org.eclipse.scava.business.impl;\n" + "\n" + "import java.io.File;\n"
				+ "import java.io.IOException;\n" + "import java.nio.file.Paths;\n" + "import java.util.Arrays;\n"
				+ "import java.util.HashMap;\n" + "import java.util.List;\n" + "import java.util.Map;\n"
				+ "import java.util.Map.Entry;\n" + "\n" + "/**\n" + " * @author Juri Di Rocco\n" + " *\n" + " */\n"
				+ "@Service\n" + "@Qualifier(\"Readme\")\n"
				+ "public class ReadmeSimilarityCalculator implements ISingleSimilarityCalculator {\n" + "\n"
				+ "	private static final Logger logger = LoggerFactory.getLogger(ReadmeSimilarityCalculator.class);\n"
				+ "	public static final String FIELD_CONTENT = \"contents\";\n" +

				"	\n"
				+ "	private void createIndex(List<Artifact> prjs) throws LockObtainFailedException, IOException {\n"
				+ "\n" + "		File indexDirectory = new File(luceneIndex);\n"
				+ "		org.apache.lucene.store.Directory dir = FSDirectory.open(Paths.get(indexDirectory.getAbsolutePath()));\n"
				+ "		Analyzer analyzer = new EnglishAnalyzer(StandardAnalyzer.STOP_WORDS_SET); // using\n"
				+ "																					// stop\n"
				+ "																					// words\n"
				+ "		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);\n" + "\n"
				+ "		if (indexDirectory.exists()) {\n"
				+ "			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);\n" + "		} else {\n"
				+ "			// Add new documents to an existing index:\n"
				+ "			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);\n" + "		}\n" + "\n"
				+ "		IndexWriter writer = new IndexWriter(dir, iwc);\n" + "		for (Artifact project : prjs) {\n"
				+ "			Document doc = new Document();\n" + "			FieldType fieldType = new FieldType();\n"
				+ "\n" + "			fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);\n"
				+ "			fieldType.setStored(true);\n" + "			fieldType.setStoreTermVectors(true);\n"
				+ "			fieldType.setTokenized(true);\n"
				+ "			Field contentField = new Field(FIELD_CONTENT, project.getReadmeText(), fieldType);\n"
				+ "			doc.add(contentField);\n" + "			writer.addDocument(doc);\n" + "		}\n"
				+ "		\n" + "\n" + "		writer.close();\n" + "	}\n" + "}\n";
		
		Query q = new Query();
		q.setCompilationUnit(query);
		try {
			Recommendation va = crossIndexRecommender.getRecommendation(q);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Autowired
	public void setElasticSearchClient()
	{
		client = new ElasticSearchClient(scheme, hostname, port);
	}
	
	@Override
	public Recommendation getRecommendation(Query query) throws Exception {
		return getRecommendation(query, "java");
	}
	
	public Recommendation getRecommendation(Query query, String language) throws Exception {
		Recommendation rec = new Recommendation();
		SearchHits hits = codeBasedQuery(query.getCompilationUnit(), language);
		if(hits!=null)
		{
			hits=naturalLanguageBasedQuery(hits);
			if(hits!=null)
			{
				Map<String, Object> sourceAsMap;
				RecommendationItem ri;
				for(SearchHit hit : hits)
				{
					sourceAsMap = hit.getSourceAsMap();
					ri = new RecommendationItem();
					ri.setSignificance(hit.getScore());
					ri.setApiDocumentationLink((String)sourceAsMap.get(fieldRecommendation));
					rec.getRecommendationItems().add(ri);
				}
			}
		}
		return rec;
	}
	
	private SearchHits codeBasedQuery(String snippet, String language)
	{
		Set<String> tokens = CodeTokenizer.tokenize(snippet, language, false);
		try {
			HashMap<String, Double> tokensEntropy = getEntropy(snippet, tokens);
			String boostedQuery=makeBoostedQuery(tokensEntropy);
			return client.queryBoostedQueryIndex(boostedQuery, fieldForCodeBasedQuery, hitsCodeBasedQuery);
			
		} catch (IOException e) {
			logger.error("There was an issue quering ElasticSearch index: ", e);
			return null;
		}	
	}
	
	private SearchHits naturalLanguageBasedQuery(SearchHits hits)
	{
		List<String> documents = new ArrayList<String>();
		List<Double> scores = new ArrayList<Double>();
		HashMap<String, Double> keywordsWeights = new HashMap<String, Double>();
		double maxScore=hits.getMaxScore();
		Map<String, Object> sourceAsMap;
		for(SearchHit hit : hits)
		{
			sourceAsMap = hit.getSourceAsMap();
			documents.add((String)sourceAsMap.get(fieldForNLBasedQuery));
			scores.add(hit.getScore()/maxScore);
		}
		for(int i=0; i<documents.size(); i++)
		{
			Map<String, Double> keywords = RAKE.extractKeywords(documents.get(i));
			processKeywords(keywords, scores.get(i), keywordsWeights);
		}
		
		normalizeKeywordsWeights(keywordsWeights);
		
		Map<String, Double> finalKeywords = keywordsWeights.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit((int) Math.round(keywordsWeights.size()/3.0))
				.collect(Collectors.toMap(
				          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		String boostedQuery=makeBoostedQuery(finalKeywords);
		
		System.err.println(boostedQuery);
		
		try {
			return client.queryBoostedQueryIndex(boostedQuery, fieldForNLBasedQuery, hitsNLBasedQuery);
		} catch (IOException e) {
			logger.error("There was an issue quering ElasticSearch index: ", e);
			return null;
		}
		
	}
	
	private void normalizeKeywordsWeights(HashMap<String, Double> keywordsWeights)
	{
		double min = Collections.min(keywordsWeights.values());
		double max = Collections.max(keywordsWeights.values())-min;
		keywordsWeights.replaceAll((k,v)->v=weightRange((v-min)/max));
	}
	
	private double weightRange(double weight)
	{
		if (weight > 0 && weight < 0.25)
			return 1.0;
		else if (weight > 0.25 && weight < 0.4)
			return 2.0;
		else if (weight > 0.4 && weight < 0.6)
			return 3.0;
		else if (weight > 0.6 && weight < 0.75)
			return 4.0;
		else if (weight > 0.75 && weight < 1.0)
			return 5.0;
		else
			return 1.0;
	}
	
	private void processKeywords(Map<String, Double> keywords, double normalizedScore, HashMap<String, Double> keywordsWeights)
	{
		Double modifiedScore;
		for(String keyword : keywords.keySet())
		{
			modifiedScore=(keywords.get(keyword)*normalizedScore);
			keyword=removeCharacters.matcher(keyword).replaceAll(" ");
			keyword=extraSpacing.matcher(keyword).replaceAll(" ");
			if(keywordsWeights.containsKey(keyword))
				keywordsWeights.put(keyword, keywordsWeights.get(keyword)+modifiedScore);
			else
				keywordsWeights.put(keyword, modifiedScore);
		}
	}
	
	private String makeBoostedQuery(Map<String, Double> tokensWeights)
	{
		List<String> weightedTokens = new ArrayList<String>(tokensWeights.size());
		tokensWeights.forEach((k,v) -> {
			if(k.contains(" "))
				k="("+k+")";
			weightedTokens.add(k+"^"+v);
			});
		return String.join(" OR ", weightedTokens);
	}
	
	private HashMap<String, Double> getEntropy(String snippet, Set<String> tokens)
			throws IOException {
		HashMap<String, Double> entropies = new HashMap<String, Double>();
		int tokenSize = tokens.size();
		for (String token : tokens) {
			entropies = getEntropy(token, snippet, entropies, true, tokenSize);
		}
		return entropies;
	}
	
	private HashMap<String, Double> getEntropy(String target, String snippet, HashMap<String, Double> entropies,
			Boolean normalization, int tokens) throws IOException {
		int tot = tokens;
		double np;
		double count = StringUtils.countMatches(snippet, target);
		double p = count / (double) tot;
		np = 1 - p;
		double ent = -p * (Math.log(p) / Math.log(2)) - np * (Math.log(np) / Math.log(2));
		if (normalization) {
			entropies.put(target, ent);
		} else {
			entropies.put(target.toLowerCase(Locale.ENGLISH), weightRange(ent)); 
		}
		return entropies;
	}

}
