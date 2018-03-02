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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.business.IAggregatedSimilarityCalculator;
import org.eclipse.scava.business.IDependencyService;
import org.eclipse.scava.business.impl.simrank.utils.Graph;
import org.eclipse.scava.business.impl.simrank.utils.SimRank;
import org.eclipse.scava.business.model.Artifact;
import org.eclipse.scava.business.model.GithubUser;
import org.eclipse.scava.business.model.Stargazers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable; 
@Service
@Qualifier("CrossSim")
public class CROSSSimSimilarityCalculator implements IAggregatedSimilarityCalculator {

	
	@Autowired
	private IDependencyService dependencyService;
	
	@Override
	public boolean appliesTo(Artifact art) {
		return (!art.getStarred().isEmpty() &&	!art.getDependencies().isEmpty())? true : false;
	}

	@Override
	public String getSimilarityName() {
		return "CrossSim";
	}

	@Override
	public Table<String, String, Double> calculateAggregatedSimilarityValues(List<Artifact> artifacts, Map<String, String> params) {
		boolean dep = false;
		int freqDeps = Integer.MAX_VALUE;
		boolean committers = false;
		boolean stargazers = false;
		if(params.containsKey("committers"))
			committers = true;
		if(params.containsKey("deps"))
			dep = true;
		if(params.containsKey("stargazers"))
			stargazers = true;
		if(params.containsKey("freqDeps"))
			freqDeps = Integer.parseInt(params.get("freqDeps"));
		
		
		Map<String, Integer> dictionary = new HashMap<>();
		Graph graph = new Graph();
		Map<Integer, String> repoList = new HashMap<>();
		int artifactId = 0;
		
		Map<Integer,Set<Integer>> inLinksMap = new HashMap<>();
		Set<Integer> inlinksSet;
		Set<Integer> nodes = new HashSet<>();
		
		
		for (Artifact artifact : artifacts) {
			String repoUrl = artifact.getFullName();
			if (!dictionary.containsKey(repoUrl)) {
				dictionary.put(repoUrl, artifactId);
				repoList.put(artifactId, artifact.getFullName());
				artifactId++;
			}
						
			Integer repoId = dictionary.get(repoUrl);
			nodes.add(repoId);
			inLinksMap = graph.getInLinks();
			
			if(inLinksMap.containsKey(repoId))	inlinksSet = inLinksMap.get(repoId);										
			else inlinksSet = new HashSet<>();									
			
			
			if(committers){
				/*
				 * Committers
				 */
				for (GithubUser user : artifact.getCommitteers()) {
					String login = user.getLogin();
					Integer userId = 0;
					if (!dictionary.containsKey(login)) {
						dictionary.put(login, artifactId);
						userId = artifactId;
						artifactId++;
					} else
						userId = dictionary.get(login);
					nodes.add(userId);
					inlinksSet.add(userId);
				}
			}
			
			if(stargazers){
				/* Star events: user -> project */
				for (Stargazers user : artifact.getStarred()) {
					String login = user.getLogin();			
					Integer userId = 0;
					if (!dictionary.containsKey(login)) {
						dictionary.put(login, artifactId);
						userId = artifactId;
						artifactId++;
					} else
						userId = dictionary.get(login);							
					nodes.add(userId);												
					inlinksSet.add(userId);							
				}
			}
			if (dep){				
				Map<String, Integer> occurrencesMap = dependencyService.getMapDependencyAndNumOfProjectThatUseIt();
				/* Dependencies: dependency -> project */
				Set<String> deps = new HashSet<>(artifact.getDependencies());
				for (String depName : deps) {
					int occurrences = occurrencesMap.get(depName);
					if(occurrences < freqDeps){			
						Integer depId = 0;
						if (!dictionary.containsKey(depName)) {
							dictionary.put(depName, artifactId);
							depId = artifactId;
							artifactId++;
						} else
							depId = dictionary.get(depName);										
						nodes.add(depId);														
						inlinksSet.add(depId);							
					}
				}
			}
			inLinksMap.put(repoId, inlinksSet);			
		}
		graph.setInLinks(inLinksMap);		
		graph.setNodesCount(nodes.size());
		
		SimRank simrank = new SimRank(graph);
		simrank.computeSimRank();
		
		Set<Integer> keySet = repoList.keySet();

		String repo1 = "";
		String repo2 = "";
		int id1 = 0;
		int id2 = 0;
		double val=0;
		Table<String, String, Double> result = HashBasedTable.create();
		for (Integer k1 : keySet) {
			repo1 = repoList.get(k1);
			for (Integer k2 : keySet) {
				repo2 = repoList.get(k2);
				id1 = dictionary.get(repo1);
				id2 = dictionary.get(repo2);
				val = simrank.getSimRank(id1, id2);
				result.put(repo1, repo2, val);
			}
		}
		return result;
	}

}
