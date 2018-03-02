/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.requestreplyclassifier.opennlptartarus.libsvm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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
		System.err.println("Lemma " + lemma + " not found in LemmaFeatures structure.");
		return -1;
	}

	public String getLemma(int order) {
		if (orderLemma.containsKey(order))
			return orderLemma.get(order);
		System.err.println("Order " + order + " not found in LemmaFeatures structure.");
		return null;
	}

	public String getCleanLemma(int order) {
		if (orderLemma.containsKey(order))
			return clean(orderLemma.get(order));
		System.err.println("Order " + order + " not found in LemmaFeatures structure.");
		return null;
	}

	private static String[] problematicSymbols = {"'"};
	
	private String clean(String lemma) {
		for (String symbol: problematicSymbols)
			lemma = lemma.replace(symbol, "_");
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
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        if (path.endsWith("bin/"))
        	path = path.substring(0, path.lastIndexOf("bin/"));
		File file = new File(path, filename);
		String content = null;
		try {
			content = readFileAsString(file);
		} catch (IOException e) {
			System.err.println("Unable to read file: " + filename);
			e.printStackTrace();
		}
		for (String line: content.split("\\n")) {
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

	private static String readFileAsString(File afile) throws java.io.IOException{
	    byte[] buffer = new byte[(int) afile.length()];
	    BufferedInputStream f = null;
	    try {
	        f = new BufferedInputStream(new FileInputStream(afile));
	        f.read(buffer);
	    } finally { 
	    	if (f != null) try { f.close(); } catch (IOException ignored) { }
	    }
	    return new String(buffer);
	}

}
