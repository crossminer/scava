/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.resources.sentinet5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

import org.eclipse.scava.platform.logging.OssmeterLogger;

class SenticNet5Singleton
{
	private static SenticNet5Singleton singleton = new SenticNet5Singleton();
	private HashMap<String, HashMap<String, Double>> dictionary = new HashMap<String, HashMap<String, Double>>();
	private List<String> listPos;
	private List<String> listNeg;
	
	protected OssmeterLogger logger;
	private String modelPath="/lexicon/SenticNet5Lexicon.txt";
	
	private SenticNet5Singleton()
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("nlp.resources.sentinet5");
		BufferedReader model;
		try
		{
			model = loadFile();
			listPos=new ArrayList<String>();
			listNeg=new ArrayList<String>();
			readModel(model);
			logger.info("Lexicon has been sucessfully loaded");
		}
		catch (IOException  e) 
		{
			logger.error("Error while loading the lexicon:", e);
			e.printStackTrace();
		}		
	}
	
	private void readModel(BufferedReader model) throws IOException, InputMismatchException
	{
		String line;
		String[] entry;
		double score;
		HashMap<String, Double> values;
		while((line = model.readLine()) != null)
		{
			if (!line.trim().startsWith("#"))
			{
				values = new HashMap<String, Double>();
				entry=line.split("\\t");
				if(entry.length!=8)
				{
					throw new InputMismatchException("The lexicon "+ modelPath+" has errors in its format in line: "+line); 
				}
				score = Double.valueOf(entry[7]);
				if(score>0)
				{
					listPos.add(entry[0]);
				}
				else if(score<0)
				{
					listNeg.add(entry[0]);
				}
				values.put("Pleasantness", Double.valueOf(entry[1]));
				values.put("Attention", Double.valueOf(entry[2]));
				values.put("Sensitivity", Double.valueOf(entry[3]));
				values.put("Aptitude", Double.valueOf(entry[4]));
				values.put("Polarity", score);
				dictionary.put(entry[0], values);
				
			}
		}
	}
	
	private BufferedReader loadFile() throws UnsupportedEncodingException, FileNotFoundException 
	{
		ClassLoader cl = getClass().getClassLoader();
		InputStream resource = cl.getResourceAsStream(modelPath);
		if(resource==null)
			throw new FileNotFoundException("The file "+modelPath+" has not been found");
		return new BufferedReader(new InputStreamReader(resource, "UTF-8"));
	}
	
	public HashMap<String, HashMap<String, Double>> getDictionary()
	{
		return dictionary;
	}
	
	public List<String> getPolarityLexicon()
	{
		List<String> lexicon = new ArrayList<>();
		lexicon.addAll(listPos);
		lexicon.addAll(listNeg);
		return lexicon;
	}

	public List<String> getPositivePolarityLexicon()
	{
		return listPos;
	}
	
	public List<String> getNegativePolarityLexicon()
	{
		return listNeg;
	}
	
	public static SenticNet5Singleton getInstance()
	{
		return singleton;
	}

}
