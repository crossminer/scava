/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.test.crossindexrecommender;

import org.eclipse.scava.business.dto.Query;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.impl.CROSSIndexRecommender;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CROSSIndexRecommenderTest {

	private static final Logger logger = LoggerFactory.getLogger(CROSSIndexRecommenderTest.class);

	@Autowired
	private CROSSIndexRecommender crossIndexRecommender;

	@Test
	@Ignore
	public void queryBuilderTest() throws Exception {
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
		Recommendation va = crossIndexRecommender.getRecommendation(q);
	}

}
