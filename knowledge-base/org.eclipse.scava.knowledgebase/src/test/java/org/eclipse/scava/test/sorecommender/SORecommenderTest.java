/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.sorecommender;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.SORecommender;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class SORecommenderTest {

	private static final Logger logger = LoggerFactory.getLogger(SORecommenderTest.class);

	@Autowired
	private SORecommender soRecommender;

	@Test
	public void queryBuilderTest() throws IOException {
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
		Map<String, String> param = new HashMap<String, String>();
		param.put("ImportDeclaration", "OR");
		param.put("MethodDeclaration", "OR");
		param.put("MethodInvocation", "OR");
		param.put("VariableDeclaration", "OR");
		param.put("ClassInstance", "OR");
		param.put("VariableDeclarationType", "OR");
		String v = soRecommender.makeBoostedQuery(query, param);
		logger.info(v);
		assertNotEquals("", v);
	}

	@Test
	public void queryBuilderTestNoHeaderInfo() throws IOException {
		String query = "public class ReadmeSimilarityCalculator implements ISingleSimilarityCalculator {\n" + "\n"
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
		Map<String, String> param = new HashMap<String, String>();
		param.put("ImportDeclaration", "OR");
		param.put("MethodDeclaration", "OR");
		param.put("MethodInvocation", "OR");
		param.put("VariableDeclaration", "OR");
		param.put("ClassInstance", "OR");
		param.put("VariableDeclarationType", "OR");
		String v = soRecommender.makeBoostedQuery(query, param);
		logger.info(v);

		assertNotEquals("", v);
	}

	@Test
	
	public void queryBuilderTestWrongCode() throws IOException {
		String query = "else if (analyzer.equals(\"bm25\") && !stopwords && stemmer) \n" + "        {\n"
				+ "            //Analyzer - stopwords + stemmer\n"
				+ "            analyz = new EnglishAnalyzer(CharArraySet.EMPTY_SET);\n"
				+ "            config = new IndexWriterConfig(analyz);\n" + "            //BM25 ranking method\n"
				+ "            config.setSimilarity(new BM25Similarity());\n" + "        } ";
		Map<String, String> param = new HashMap<String, String>();
		param.put("ImportDeclaration", "OR");
		param.put("MethodDeclaration", "OR");
		param.put("MethodInvocation", "OR");
		param.put("VariableDeclaration", "OR");
		param.put("ClassInstance", "OR");
		param.put("VariableDeclarationType", "OR");
		String v = soRecommender.makeBoostedQuery(query, param);
		logger.info(v);

		assertNotEquals("", v);
	}

	@Test
	@Ignore
	public void soRecomendationTest() {
		String query = "package com.mkyong.core;\n" + "\n" + "import java.net.UnknownHostException;\n"
				+ "import java.util.Date;\n" + "import com.mongodb.BasicDBObject;\n" + "import com.mongodb.DB;\n"
				+ "import com.mongodb.DBCollection;\n" + "import com.mongodb.DBCursor;\n"
				+ "import com.mongodb.MongoClient;\n" + "import com.mongodb.MongoException;\n" + "\n" + "/**\n"
				+ " * Java + MongoDB Hello world Example\n" + " * \n" + " */\n" + "public class App {\n"
				+ "  public static void main(String[] args) {\n" + "\n" + "    try {\n" + "\n"
				+ "	/**** Connect to MongoDB ****/\n" + "	// Since 2.10.0, uses MongoClient\n"
				+ "	MongoClient mongo = new MongoClient(\"localhost\", 27017);\n" + "\n"
				+ "	/**** Get database ****/\n" + "	// if database doesn't exists, MongoDB will create it for you\n"
				+ "	DB db = mongo.getDB(\"testdb\");\n" + "\n" + "	/**** Get collection / table from 'testdb' ****/\n"
				+ "	// if collection doesn't exists, MongoDB will create it for you\n"
				+ "	DBCollection table = db.getCollection(\"user\");\n" + "\n" + "	/**** Insert ****/\n"
				+ "	// create a document to store key and value\n" + "	BasicDBObject document = new BasicDBObject();\n"
				+ "	document.put(\"name\", \"mkyong\");\n" + "	document.put(\"age\", 30);\n"
				+ "	document.put(\"createdDate\", new Date());\n" + "	table.insert(document);\n" + "\n"
				+ "	/**** Find and display ****/\n" + "	BasicDBObject searchQuery = new BasicDBObject();\n" + "}}}";
		Query q = new Query();
		q.setCompilationUnit(query);
		Recommendation va = soRecommender.getRecommendation(q);
		assertNotEquals(0, va.getRecommendationItems().size());
		va.getRecommendationItems()
				.forEach(z -> logger.info("https://stackoverflow.com/questions/" + z.getApiDocumentationLink()));
	}

}
