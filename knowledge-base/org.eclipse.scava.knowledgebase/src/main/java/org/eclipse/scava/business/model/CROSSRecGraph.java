package org.eclipse.scava.business.model;

import java.io.*;
import java.util.*;

public class CROSSRecGraph {

	public CROSSRecGraph () {		
		outLinks = new HashMap<Integer,Set<Integer>>();
		nodeCount = 0;
	}

	private Map<Integer,Set<Integer>> outLinks;
	
	private int nodeCount;
	
	private Map<String, Integer> dictionary = new HashMap<String, Integer>();
	
	private Map<Integer, String> dictionarySwitch = new HashMap<Integer, String>();
	
	
	public void setDictionary(Map<String, Integer> dictionary) {
		this.dictionary = dictionary;
	}
	
	public Map<String, Integer> getDictionary(){
		return this.dictionary;
	}

	
	public Map<Integer,Set<Integer>> getOutLinks(){
		return outLinks;
	}
	
	public void setOutLinks(Map<Integer,Set<Integer>> OutLinks){
		this.outLinks = OutLinks;
	}
	
	public void setNodeCount(int n){
		this.nodeCount = n;
	}
	
	public int getNodeCount(){
		return this.nodeCount;
	}

	public Map<Integer, String> getDictionarySwitch() {
		return dictionarySwitch;
	}

	public void setDictionarySwitch(Map<Integer, String> dictionarySwitch) {
		this.dictionarySwitch = dictionarySwitch;
	}	
}
