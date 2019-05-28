/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.featuremethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.Classifier;
import org.eclipse.scava.platform.logging.OssmeterLogger;

public class CleanQuestionWordsMethod {

	private static Set<String> questionWordList;
	private static String questionWordsFileName = "classifierFiles/questionWords";
	protected static OssmeterLogger logger;
	
	public static int predict(ClassificationInstance xmlResourceItem) {
		if (questionWordList==null) {
			logger = (OssmeterLogger) OssmeterLogger.getLogger("contentclassifier.opennlptartarus.libsvm.featuremethods");
			
			try {
				questionWordList = loadSetFromFile();
				logger.info("Lexicon has been sucessfully loaded");
			} catch (IOException e) {
				logger.error("Error while loading the lexicon:", e);
				e.printStackTrace();
			}
		}
		if (containsQuestionWords(xmlResourceItem))
			return 1;	//	"Request"
		return 0;	//	"Reply"
	}

	private static Boolean containsQuestionWords(ClassificationInstance xmlResourceItem) {
		for (String questionWord: questionWordList) {
			String regularExpressionString = "[^\\d\\w]" + questionWord + "[^\\d\\w]";
		    Pattern pattern = Pattern.compile(regularExpressionString);
		    Matcher matcher = pattern.matcher(xmlResourceItem.getCleanText().toLowerCase());
			if (matcher.find())
				return true;
		}
		return false;
	}

	private static Set<String> loadSetFromFile() throws IOException
	{
		ClassLoader cl = (new Classifier()).getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(questionWordsFileName);
		if(resource==null)
		{
			String path = (new Classifier()).getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if (path.endsWith("bin/"))
				path = path.substring(0, path.lastIndexOf("bin/"));
			if (path.endsWith("target/classes/"))
				path = path.substring(0, path.lastIndexOf("target/classes/"));
			File file= new File(path+"/"+questionWordsFileName);
			if(!Files.exists(file.toPath()))
				throw new FileNotFoundException("The file "+questionWordsFileName+" has not been found");
			else
				resource=new FileInputStream(file);
		}
			
		BufferedReader model = new BufferedReader(new InputStreamReader(resource, "UTF-8"));
		String line;
		HashSet<String> hashSet = new HashSet<String>();
		while((line = model.readLine()) != null)
		{
			hashSet.add(line.trim());
		}
		model.close();
		resource.close();
		return hashSet;
	}
	

}
