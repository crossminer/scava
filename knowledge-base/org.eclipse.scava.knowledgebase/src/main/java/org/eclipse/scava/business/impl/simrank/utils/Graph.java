/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl.simrank.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class Graph {

	/**
	 *  A Map storing InLinks. For each identifier (the key), another Map is stored,
	 *  containing for each inlink an associated "connection weight"
	 */
	
	private static final Logger logger = Logger.getLogger(Graph.class);
	
	private Map<Integer,Set<Integer>> inLinks;
	
	/** The number of nodes in the graph */
	private int nodeCount;
	
	public Set<Integer> inLinks(int id){		
		return inLinks.get(id);		
	}
	
	public int getNodeCount() {
		return nodeCount;
	}
	
	public Map<Integer,Set<Integer>> getInLinks(){
		return inLinks;
	}
	
	public void setInLinks(Map<Integer,Set<Integer>> InLinks){
		this.inLinks = InLinks;
	}
	
	public void setNodesCount(int n){
		this.nodeCount = n;
	}
	
	public Graph () {		
		inLinks = new HashMap<>();
		nodeCount = 0;
	}	
	
	public void printGraph(){
		Set<Integer> keySet = inLinks.keySet();
		for(int key:keySet){
			Set<Integer> list = inLinks.get(key);
			logger.debug(key + ": ");
			if(list!=null){
				for(int j:list)System.out.print(j + " ");
			}
			logger.debug("");
		}
		return;
	}
}
