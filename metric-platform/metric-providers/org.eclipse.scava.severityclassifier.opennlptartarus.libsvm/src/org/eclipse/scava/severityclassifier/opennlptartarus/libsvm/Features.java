/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class Features {
	private Map<Integer, String> orderLemma;
	private Map<String, Integer> lemmaOrder;
	private int highestOrder;

	public Features() {
		initialise();
	}
		
	public Features(String filename) {
		initialise();
		loadFromFile(filename);
	}

	private void initialise() {
		orderLemma = new HashMap<Integer, String>(); 
		lemmaOrder = new HashMap<String, Integer>();
		highestOrder = 0;
	}
	
	public void add(int order, String lemma) {
		orderLemma.put(order, lemma);
		lemmaOrder.put(lemma, order);
		if (order > highestOrder) highestOrder = order;
	}
	
	public int getHighestOrder() {
		return highestOrder;
	}
	
	public int getOrder(String lemma) {
		if (lemmaOrder.containsKey(lemma))
			return lemmaOrder.get(lemma);
//		if (!lemma.startsWith("CMUcluster_"))
//			System.err.println("Lemma " + lemma + " not found in LemmaFeatures structure.");
		return 0;
	}

	public String getLemma(int order) {
		if (orderLemma.containsKey(order))
			return orderLemma.get(order);
//		System.err.println("Order " + order + " not found in LemmaFeatures structure.");
		return null;
	}

	public String getCleanLemma(int order) {
		if (orderLemma.containsKey(order))
			return clean(orderLemma.get(order));
		System.err.println("Order " + order + " not found in LemmaFeatures structure.");
		return null;
	}

	private String clean(String lemma) {
		lemma = lemma.replace("\\", "\\\\");
		lemma = lemma.replace("'", "&sq;");
		return lemma;
	}

	public Boolean containsLemma(String lemma) {
		return lemmaOrder.containsKey(lemma);
	}
	
	public SortedSet<String> getSortedLemmas() {
		return new TreeSet<String>(lemmaOrder.keySet());
	}
	
	public SortedSet<Integer> getSortedOrders() {
		return new TreeSet<Integer>(orderLemma.keySet());
	}
	
	private void loadFromFile(String filename) {
		String content = null;
		try {
			URL resource = getClass().getResource("/" + filename);
			if(resource!=null)
				content = Resources.toString(resource, Charsets.UTF_8);
			else
			{
				String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
				if (path.endsWith("bin/"))
					path = path.substring(0, path.lastIndexOf("bin/"));
				if (path.endsWith("target/classes/"))
					path = path.substring(0, path.lastIndexOf("target/classes/"));
				Path filePath= Paths.get(path+"/"+filename);
				if(!Files.exists(filePath))
					throw new FileNotFoundException("The file "+filename+" has not been found");
				else
					content=Resources.toString(filePath.toUri().toURL(), Charsets.UTF_8);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (content != null) {
			for (String line: content.split("\\v+")) {
				if(line.isEmpty())
					continue;
				String[] elements = line.split("\\t");
				int order = Integer.parseInt(elements[0].trim());
				String lemma = elements[1].trim();
				if (lemma.length()>0) {
					orderLemma.put(order, lemma);
					lemmaOrder.put(lemma, order);
					if (order > highestOrder) highestOrder = order;
				}
			}
		}
	}

}
