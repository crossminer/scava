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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
import org.eclipse.scava.business.integration.GithubUserRepository;
import org.eclipse.scava.business.model.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
/**
 * @author Juri Di Rocco
 *
 */
@Service
@Qualifier("RepoPalStar")
public class RepoPalStarSimilarityCalculator implements ISingleSimilarityCalculator {

	private static final Logger logger = Logger.getLogger(RepoPalStarSimilarityCalculator.class);
	
	
	private Map<String, Set<String>> userRepo = new HashMap<>();
	
	@Autowired
	@Qualifier("Readme")
	private ISimilarityCalculator simReadmeCalculator;

	@Autowired
	private GithubUserRepository githubUserRepo;
	
	@Override
	public double calculateSimilarity(Artifact prj1, Artifact prj2) {
		double tmp = 0;
		double val1 = 0;
		double val2 = 0;
		Set<String> usersWhoStarRepo1 = prj1.getStarred().stream().map(z->z.getLogin()).collect(Collectors.toSet());
		Set<String> usersWhoStarRepo2 = prj2.getStarred().stream().map(z->z.getLogin()).collect(Collectors.toSet());
						
			for (String user2: usersWhoStarRepo2) {
				for (String user1: usersWhoStarRepo1) {			
				tmp+= userSimilarity(user1,user2);				
			}					
		}
		int size1= usersWhoStarRepo1.size();
		int size2= usersWhoStarRepo2.size();
		
		val1 = (double)1/(size1*size2);
		val2 = val1*tmp;	
				
		return val2;
	}
	
	public double userSimilarity(String userID1, String userID2) {
		Set<String> reposOfUser1 = userRepo.get(userID1);
		if(reposOfUser1 == null){
			reposOfUser1 = githubUserRepo.findOneByLogin(userID1).getStarredRepo();
			userRepo.put(userID1, reposOfUser1);
		}
		Set<String> reposOfUser2 = userRepo.get(userID2);
		if(reposOfUser2 == null){
			reposOfUser2 = githubUserRepo.findOneByLogin(userID2).getStarredRepo();
			userRepo.put(userID2, reposOfUser2);
		}
		
		double tmp = 0;
		
		
		Set<String> common = Sets.intersection(reposOfUser1, reposOfUser2);
		double size1 = common.size();
		double size2 = reposOfUser1.size() + reposOfUser2.size() - size1;
		tmp = size1 / size2;
		return tmp;
	}

	@Override
	public String getSimilarityName() {
		return "RepoPalStar";
	}
	@Override
	public boolean appliesTo(Artifact art) {
		if (!art.getStarred().isEmpty())
			return true;
		else return false;
	}
    
}
