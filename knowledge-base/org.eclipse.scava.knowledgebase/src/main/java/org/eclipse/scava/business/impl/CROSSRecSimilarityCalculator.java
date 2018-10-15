/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.CROSSRecGraphRepository;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.CROSSRecGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table; 
@Service
@Qualifier("CrossRec")
public class CROSSRecSimilarityCalculator implements IAggregatedSimilarityCalculator {

	private static final Logger logger = LoggerFactory.getLogger(CROSSRecSimilarityCalculator.class);
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	@Autowired
	private CROSSRecGraphRepository crossRecGraphRepository;
	
	@Override
	public boolean appliesTo(Artifact art) {
		return (!art.getStarred().isEmpty() &&	!art.getDependencies().isEmpty())? true : false;
	}

	@Override
	public String getSimilarityName() {
		return "CrossRec";
	}

	@Override
	public Table<String, String, Double> calculateAggregatedSimilarityValues(List<Artifact> artifacts, Map<String, String> params) {
		Table<String, String, Double> result = HashBasedTable.create();
		for (Artifact artifact : artifacts) {
			List<Dependency> deps = new ArrayList<>();
			for (String dependency : artifact.getDependencies()) {
				Dependency dep = new Dependency();
				dep.setName(dependency);
				dep.setArtifactID(artifact.getName());
				deps.add(dep);
			}
			try {
				Map<String, Double> map = computeWeightCosineSimilarity(deps);
				for (Map.Entry<String, Double> entry : map.entrySet()){
				    result.put(artifact.getFullName(), artifactRepository.findOne(entry.getKey()).getFullName(), entry.getValue());
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}
		
	/*
	 * 
	 */
	public Map<String, Double> computeWeightCosineSimilarity(List<Dependency> dependencies) throws Exception{
		CROSSRecGraph bigGraph;
		List<CROSSRecGraph> tempGraphs = crossRecGraphRepository.findAll();
		if(tempGraphs.size() == 0) {
			bigGraph = createCROSSRecGraph();
		}
		else bigGraph = tempGraphs.get(0);

		Set<String> allLibs = extractDepsfromGraph(bigGraph);
		/*add all libraries from the training set*/
		CROSSRecGraph projectGraph = createGraphFromListDependecies(dependencies);		
		Map<String, Double> sim = new HashMap<String, Double>();
		Set<String> queryLibs = extractDepsfromGraph(projectGraph);
		
		allLibs.addAll(queryLibs);
		CROSSRecGraph globalGraph = combine(bigGraph, projectGraph);
		
		Map<Integer, Set<Integer>> graphEdges = globalGraph.getOutLinks();
		Set<Integer> keySet = graphEdges.keySet();
								
		Map<Integer, Double> libWeight = new HashMap<Integer, Double>();
		double freq = 0;
		for(Integer startNode:keySet){					
			Set<Integer> outlinks = graphEdges.get(startNode);					
			for(Integer endNode:outlinks){						
				if(libWeight.containsKey(endNode)) {
					freq = libWeight.get(endNode)+1;												
				} else freq = 1;
				libWeight.put(endNode, freq);					
			}				
		}
		/*get the number of projects in the whole graph*/
		int numberOfProjects = keySet.size();				
		keySet = libWeight.keySet();				
		double weight = 0, idf = 0;								
		for(Integer libID:keySet){
			freq = libWeight.get(libID);
			weight = (double)numberOfProjects/freq;
			idf = Math.log(weight);
			libWeight.put(libID, idf);									
		}			
		
		
		for (String artifactName : extractProjectsfromGraph(bigGraph)) {
			Artifact artifact = artifactRepository.findOne(artifactName);
			if(artifact != null) {
				Set<String> specificLibs = new HashSet<String>();
				for (String string : artifact.getDependencies()) {
					specificLibs.add(("#DEP#" + string).replace(".", "_")); 
				}
				List<String> libSet = new ArrayList<>(Sets.union(specificLibs, queryLibs));
				int size = libSet.size();
				double vector1[] = new double[size];
				double vector2[] = new double[size];
				double val=0;
				for(int i=0;i<size;i++) {	
					String lib = libSet.get(i);
					if(queryLibs.contains(lib)) {
						int libID = globalGraph.getDictionary().get(lib);
						vector1[i]=libWeight.get(libID);
					}
					else vector1[i]=0;
					
					if(specificLibs.contains(lib)) {
						int libID = globalGraph.getDictionary().get(lib);
						vector2[i]=libWeight.get(libID);
					}
					else vector2[i]=0;					
				}
				/*Using Cosine Similarity*/
				val = cosineSimilarity(vector1,vector2);					
				sim.put(artifact.getId(), val);		
			}
		}												
		return sim;
	}
	
	public CROSSRecGraph createCROSSRecGraph() {
		List<Artifact> arts = artifactRepository.findAll();
		CROSSRecGraph graph = null;
		for (Artifact artifact : arts) {
			CROSSRecGraph graph1 = createGraphFromArtifact(artifact);
			graph = combine(graph, graph1);
		}
		crossRecGraphRepository.deleteAll();
		crossRecGraphRepository.save(graph);
		return graph;
	}
	
	public CROSSRecGraph createGraphFromArtifact(Artifact art) {
		CROSSRecGraph graph = new CROSSRecGraph();
		// Populate dictionary
		Map<Integer, String> backgroundDictionary = new HashMap<Integer, String>();
		Map<String, Integer> backgroundDictionarySwitch = new HashMap<String, Integer>();
		backgroundDictionary.put(1, art.getId());
		backgroundDictionarySwitch.put(art.getId(), 1);
		int count = 2;
		for (String dep : art.getDependencies()) {
			backgroundDictionary.put(count, "#DEP#" + dep.replace(".", "_"));
			backgroundDictionarySwitch.put("#DEP#" + dep.replace(".", "_"), count);
			count++;
		}
		count --;
		// Populate graph
		Set<Integer> outlinks = new HashSet<Integer>();
		Set<Integer> nodes = new HashSet<Integer>();
		for (int i = 1; i <= count; i++) {
			nodes.add(i);
		}
		for (int i = 2; i <= count; i++) {
			outlinks.add(i);
		}
		graph.getOutLinks().put(1, outlinks);
		graph.setNodeCount(nodes.size());
		graph.setDictionary(backgroundDictionarySwitch);
		graph.setDictionarySwitch(backgroundDictionary);
		return graph;
	}
	
	public CROSSRecGraph combine(CROSSRecGraph bigGraph, CROSSRecGraph graph) {
		if(bigGraph == null)
			return graph;
		if(graph == null)
			return bigGraph;
		Set<Integer> outlinks = new HashSet<Integer>();
		Set<Integer> mainOutlinks = new HashSet<Integer>();
		Set<Integer> key = graph.getOutLinks().keySet();
		String artifact = "";
		int idEndNode = 0, idStartNode = 0;
		for (Integer startNode : key) {
			outlinks = graph.getOutLinks().get(startNode);
			artifact = graph.getDictionarySwitch().get(startNode);
			idStartNode = extractKey(bigGraph, artifact);

			for (Integer endNode : outlinks) {
				artifact = graph.getDictionarySwitch().get(endNode);
				idEndNode = extractKey(bigGraph, artifact);
				if (bigGraph.getOutLinks().containsKey(idStartNode)) {
					mainOutlinks = bigGraph.getOutLinks().get(idStartNode);
				} else {
					mainOutlinks = new HashSet<Integer>();
				}
				mainOutlinks.add(idEndNode);
				bigGraph.getOutLinks().put(idStartNode, mainOutlinks);
			}
		}
		Set<Integer> nodes = new HashSet<Integer>();
		key = bigGraph.getOutLinks().keySet();
		
		for (Integer startNode : key) {
			nodes.add(startNode);
			mainOutlinks = bigGraph.getOutLinks().get(startNode);
			for (Integer endNode : mainOutlinks) {
				nodes.add(endNode);
			}
		}
		bigGraph.setNodeCount(nodes.size());
		return bigGraph;
	}
	
	private int extractKey(CROSSRecGraph g, String s) {
		if (g.getDictionary().containsKey(s))
			return g.getDictionary().get(s);
		else {
			int c = g.getDictionary().size() + 1;
			g.getDictionarySwitch().put(c, s);
			g.getDictionary().put(s, c);
			return c;
		}
	}
	
	private Set<String> extractDepsfromGraph(CROSSRecGraph graph) {
		Set<String> result = null;
		result = graph.getDictionary().keySet().stream().filter(z->z.startsWith("#DEP#")).collect(Collectors.toSet());
		return result;
	}
	
	public CROSSRecGraph createGraphFromListDependecies(List<Dependency> dependencies) {
		Artifact art = new Artifact();
		art.setName("currentProject");
		art.setId("currentProject");
		for (Dependency dependency : dependencies) {
			art.getDependencies().add(dependency.getName());
		}
		return createGraphFromArtifact(art);
	}
	
	private Set<String> extractProjectsfromGraph(CROSSRecGraph graph) {
		Set<String> result = null;
		result = graph.getDictionary().keySet().stream().filter(z->!z.startsWith("#DEP#")).collect(Collectors.toSet());
		return result;
	}
	private double cosineSimilarity(double[] vector1, double[] vector2) {
		double sclar = 0, norm1 = 0, norm2 = 0;
		int length = vector1.length;
		for (int i = 0; i < length; i++)
			sclar += vector1[i] * vector2[i];
		for (int i = 0; i < length; i++)
			norm1 += vector1[i] * vector1[i];
		for (int i = 0; i < length; i++)
			norm2 += vector2[i] * vector2[i];
		double ret = 0;
		double norm = norm1 * norm2;
		ret = (double) sclar / Math.sqrt(norm);
		return ret;
	}
}
