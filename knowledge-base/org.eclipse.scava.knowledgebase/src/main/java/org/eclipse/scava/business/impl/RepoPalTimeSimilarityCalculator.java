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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.scava.business.ISimilarityCalculator;
import org.eclipse.scava.business.ISingleSimilarityCalculator;
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
@Qualifier("RepoPalTime")
public class RepoPalTimeSimilarityCalculator implements ISingleSimilarityCalculator {

	private static final Logger logger = Logger.getLogger(RepoPalTimeSimilarityCalculator.class);
	@Autowired
	@Qualifier("Readme")
	private ISimilarityCalculator simReadmeCalculator;
	
	@Override
	public double calculateSimilarity(Artifact prj1, Artifact prj2) {
		double val1 = 0;
		double val2 = 0;
		
		Set<String> usersWhoStarRepo1 = prj1.getStarred().stream().map(z->z.getLogin()).collect(Collectors.toSet());
		Set<String> usersWhoStarRepo2 = prj2.getStarred().stream().map(z->z.getLogin()).collect(Collectors.toSet());
		
		Set<String> common = Sets.intersection(usersWhoStarRepo1, usersWhoStarRepo2);
		long timeStamp1;
		long timeStamp2 = 1;
		for (String userID: common) {
			Optional<String> dateStar1 = prj1.getStarred().stream().filter(z -> z.getLogin().equals(userID)).map(z -> z.getDatestamp()).findFirst();
			Optional<String> dateStar2 = prj2.getStarred().stream().filter(z -> z.getLogin().equals(userID)).map(z -> z.getDatestamp()).findFirst();
			try {
				if(dateStar1.isPresent() && dateStar2.isPresent()) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
						timeStamp1 = df.parse(dateStar1.get()).getTime();
						timeStamp2 = df.parse(dateStar2.get()).getTime();
						if(timeStamp1 == timeStamp2)
							timeStamp2+=1;
						val1+=(double)1/Math.abs(timeStamp1-timeStamp2);
				}
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
		}
		if(common.isEmpty())
			return 0;
		val2 = (double)1/common.size();	
		return val2*val1;				

	}

	@Override
	public String getSimilarityName() {
		return "RepoPalTime";
	}
	@Override
	public boolean appliesTo(Artifact art) {
		if (!art.getStarred().isEmpty())
			return true;
		else return false;
	}
}
