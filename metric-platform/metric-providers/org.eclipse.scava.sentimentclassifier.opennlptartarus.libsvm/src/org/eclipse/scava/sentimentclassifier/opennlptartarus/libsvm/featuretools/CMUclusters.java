/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.sentimentclassifier.opennlptartarus.libsvm.featuretools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CMUclusters {

	private static Map<String, String> wordsToCMUclusters;
	private static Set<String> CMUclusters;
	private static List<String> sortedCMUclusters; 
	private static String filename = "SentimentAnalysis/CMUclusters/50mpaths2";
	
	public void load() {
		if (wordsToCMUclusters==null) {
	     	String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
	     	if (path.endsWith("bin/"))
	     		path = path.substring(0, path.lastIndexOf("bin/"));
	     	loadWordsToCMUclusters(path + filename);
		}
		System.out.println("CMU clusters loaded");
	}

	public boolean contains(String word) {
		return wordsToCMUclusters.containsKey(word);
	}
	
	public String getCluster(String word) {
		return wordsToCMUclusters.get(word);
	}
	
	public List<String> getSortedClusters() {
		if (sortedCMUclusters==null)
			sortedCMUclusters = sortSet(CMUclusters);
		return sortedCMUclusters;
	}
	
	private static List<String> sortSet(Set<String> set) {
		TreeSet<String> sortedSet = new TreeSet<String>();
		sortedSet.addAll(set);
		List<String> sortedList = new ArrayList<String>(sortedSet);
		return sortedList;
	}

	private Map<String, String> loadWordsToCMUclusters(String filename) {
		wordsToCMUclusters = new HashMap<String, String>();
		CMUclusters = new HashSet<String>();
		String CMUcontent = null;
		try {
			CMUcontent = readFileAsString(new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String CMUlineString: CMUcontent.split("\n")) {
			String[] CMUline = CMUlineString.split("\t");
			if (CMUline.length==3) {
				if (wordsToCMUclusters.containsKey(CMUline[1]))
					System.err.println("Word " + CMUline[1] + " exists in two clusters: " + 
									   CMUline[0] + " " + wordsToCMUclusters.get(CMUline[1]));
				wordsToCMUclusters.put(CMUline[1], CMUline[0]);
				CMUclusters.add(CMUline[0]);
			}
		}
		return wordsToCMUclusters;
	}

	static String readFileAsString(File afile) throws java.io.IOException{
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(afile), "UTF-8"));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while((line = bufferedReader.readLine()) != null)
			stringBuffer.append(line).append("\n");
		bufferedReader.close();
		return stringBuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		(new CMUclusters()).load();
	}

}
