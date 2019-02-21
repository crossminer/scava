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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class SenticNet5Singleton
{
	private static SenticNet5Singleton singleton = new SenticNet5Singleton();
	private HashMap<String, HashMap<String, Double>> dictionary = new HashMap<String, HashMap<String, Double>>();
	private List<String> listPos;
	private List<String> listNeg;
	
	private SenticNet5Singleton()
	{
		BufferedReader model;
		try
		{
			model = loadFile();
			listPos=new ArrayList<String>();
			listNeg=new ArrayList<String>();
			readModel(model);
		}
		catch (ModelExceptions | IOException  e) 
		{
			e.printStackTrace();
		}		
	}
	
	private void readModel(BufferedReader model) throws IOException, ModelExceptions
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
					throw new ModelExceptions("The file loaded model has errors in its format in line: "+line); 
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
	
	private void checkModelFile(Path path) throws ModelExceptions
	{
		if(!Files.exists(path))
        {
        	throw new ModelExceptions("The file "+path+" has not been found"); 
        }
	}
	
	private BufferedReader  loadFile() throws ModelExceptions, FileNotFoundException
	{
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		if (path.endsWith("target/classes/"))
			path = path.substring(0, path.lastIndexOf("target/classes/"));
		File file= new File(path+"lexicon/SenticNet5Lexicon.txt");
		checkModelFile(file.toPath());
		return new BufferedReader(new FileReader(file));
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
