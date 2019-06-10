/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.nlp.tools.license.ranking;

import org.eclipse.scava.nlp.tools.license.prediction.LicensePrediction;

public class Rank {
	
	private String name;
	private int rankPoints = 0;
	private double score = 0;
	private int nGramsMatched = 0;
	private int nGramsNotMatched = 0;
	private int ngramsInSource = 0;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRankPoints() {
		return rankPoints;
	}
	public void setRankPoints(int rankPoints) {
		this.rankPoints = rankPoints;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getnGramsMatched() {
		return nGramsMatched;
	}
	public void setnGramsMatched(int nGramsMatched) {
		this.nGramsMatched = nGramsMatched;
	}
	
	public void addRankPoints(int points) {
		
		setRankPoints(getRankPoints() + points);
	
	}
	
	public int getnGramsNotMatched() {
		return nGramsNotMatched;
	}
	public void setnGramsNotMatched(int nGramsNotMatched) {
		this.nGramsNotMatched = nGramsNotMatched;
	}
	
	
	public LicensePrediction toPrediction(LicensePrediction prediction) {
		
		prediction.setLabel(this.getName());
		prediction.setLicenseName(this.getName());
		double ngramsMatchedPercent = (double) this.getnGramsMatched() / (double) this.getNgramsInSource();
		prediction.setNgramsMatchedPercent(ngramsMatchedPercent);
		prediction.setScore(this.getScore());
		return prediction;		
	}
	public int getNgramsInSource() {
		return ngramsInSource;
	}
	public void setNgramsInSource(int ngramsInSource) {
		this.ngramsInSource = ngramsInSource;
	}

}
