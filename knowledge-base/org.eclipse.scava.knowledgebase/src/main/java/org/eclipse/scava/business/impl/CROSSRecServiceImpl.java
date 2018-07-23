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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.eclipse.scava.business.dto.Dependency;
import org.eclipse.scava.business.dto.Recommendation;
import org.eclipse.scava.business.dto.RecommendationItem;
import org.eclipse.scava.business.dto.RecommendedLibrary;
import org.eclipse.scava.business.integration.ArtifactRepository;
import org.eclipse.scava.business.integration.ArtifactTypeRepository;
import org.eclipse.scava.business.integration.CROSSRecGraphRepository;
import org.eclipse.scava.business.model.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

class ValueComparator implements Comparator<String> {

	Map<String, Double> base;

	public ValueComparator(Map<String, Double> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}

@Service
public class CROSSRecServiceImpl {
	@Value("${crossrec.numberOfNeighbours}")
	private int numberOfNeighbours;
	
	@Value("${crossrec.numberOfRecommendedLibs}")
	private int numberOfRecommendedLibs;
	
	@Autowired
	private ArtifactRepository artifactRepository;
	
	
	@Autowired
	private CROSSRecSimilarityCalculator crossRecSimilarityCalculator;
	
	private static final Logger logger = LoggerFactory.getLogger(CROSSRecServiceImpl.class);
	

	public Recommendation run(List<Dependency> dependencies) throws Exception {
		Recommendation result = new Recommendation();
		
		Map<String, Double> sim = crossRecSimilarityCalculator.computeWeightCosineSimilarity(dependencies);
		Map<String, Double> res = userBasedRecommendation(dependencies, sim);
		ValueComparator bvc =  new ValueComparator(res);        
		TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
		sorted_map.putAll(res);
		int guard = 0;
		for(Map.Entry<String,Double> entry : sorted_map.entrySet()) {
			if (guard < numberOfRecommendedLibs) {
				Double value = entry.getValue();
				String lib = entry.getKey();
				RecommendationItem ri = new RecommendationItem();
				
				RecommendedLibrary rl = new RecommendedLibrary();
				rl.setLibraryName(lib);
				rl.setUrl("https://mvnrepository.com/artifact/"+ lib.replaceAll(":", "/"));
				ri.setRecommendedLibrary(rl);
				ri.setSignificance(value);
				ri.setRecommendationType("RecommendedLibrary");
				result.getRecommendationItems().add(ri);
				guard++;
			}
			else break;
		}
		return result;
	}

	/*Recommends libraries to an input project using the user-based collaborative-filtering technique*/
	public Map<String, Double> userBasedRecommendation(List<Dependency> dependencies, Map<String, Double> sim){
		Set<String> depsStringSet = dependencies.stream().map(z -> z.getName()).collect(Collectors.toSet());
		
		Map<String, Set<String>> allNeighbourLibs = new HashMap<String, Set<String>>();
		List<String> libSet = new ArrayList<String>();			
						
									
		Map<String, Double> recommendations = new HashMap<String, Double>();
		Map<Integer, Double> simMatrix = new HashMap<Integer, Double>();
					
		ValueComparator bvc =  new ValueComparator(sim);        
		TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
		sorted_map.putAll(sim);
		
		List<String> keySet; 
			if(numberOfNeighbours -1 < sorted_map.keySet().size()-1)
				keySet =  new ArrayList<String>(sorted_map.keySet()).subList(0, numberOfNeighbours -1);
			else
				keySet = new ArrayList<String>(sorted_map.keySet());
		Set<String> libraries = new HashSet<String>();
		//Prendere le libreria per ogni progetto nel set									
		for(String key: keySet) {				
			Artifact art = artifactRepository.findOne(key);
			allNeighbourLibs.put(key, new HashSet<String>(art.getDependencies()));
			libraries.addAll(art.getDependencies());
		}
		allNeighbourLibs.put("currentProject", depsStringSet);
		/*The list of all libraries from the training projects and the testing project*/
		libraries.addAll(dependencies.stream().map(z -> z.getName()).collect(Collectors.toList()));
		/*change the set to an ordered list*/
		libraries.forEach(z-> libSet.add(z));
		
					
		/*Number of projects, including the test project*/
		int M = numberOfNeighbours + 1;
		/*Number of libraries*/
		int N = libraries.size();
					
		double userItemMatrix[][] = new double[M][N];
		
		int ind = 0;
		for(String key : allNeighbourLibs.keySet()){				
			Set<String> tmpLibs = allNeighbourLibs.get(key);
			for(int j=0;j<N;j++) {					
				if(tmpLibs.contains(libSet.get(j))) {											
					userItemMatrix[ind][j]=1.0;						
				}
				else userItemMatrix[ind][j]=0;
			}
			ind++;
		}					
		/*Here is the test project and it needs recommendation. It is located at the end of the list.*/
		for(int j=0;j<N;j++) {
			String str = libSet.get(j);
			if(depsStringSet.contains(str))userItemMatrix[numberOfNeighbours][j]=1.0;
			else {
				userItemMatrix[numberOfNeighbours][j]=-1.0;					
			}
		}
		/*Calculate the missing ratings using the item-based collaborative-filtering recommendation technique*/
		double tmpUserItemMatrix[][] = new double[M][N];
		/*copy the matrix*/			
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++) {
				tmpUserItemMatrix[i][j]=userItemMatrix[i][j];					
			}				
		}
		double val1 = 0;		
		/*average rating is computed for the projects that include a library in the library set, so it is 1*/
		double avgRating = 1.0, tmpRating = 0.0;
		int guard = 0;
		for(Map.Entry<String,Double> entry : sorted_map.entrySet()) {
			if (guard<numberOfNeighbours) {
			  Double value = entry.getValue();
			  val1 += value;
			  simMatrix.put(guard, value);
			  guard++;
			}
			else break;
		} 
			
		for(int j=0;j<N;j++) {				
			if(userItemMatrix[numberOfNeighbours][j]==-1) {					
				double val2=0;					
				for(int k=0;k<numberOfNeighbours;k++) {
					if(k >= artifactRepository.count())
						break;
					tmpRating = 0;
					for(int l=0;l<N;l++)tmpRating+=userItemMatrix[k][l];
					tmpRating = (double)tmpRating/N;						
					val2+=(userItemMatrix[k][j]-tmpRating)*simMatrix.get(k);									
				}					
				userItemMatrix[numberOfNeighbours][j] = avgRating + val2/val1;				
				recommendations.put(libSet.get(j), userItemMatrix[numberOfNeighbours][j]);				
			}				
		}								
		return recommendations;
	}
}
