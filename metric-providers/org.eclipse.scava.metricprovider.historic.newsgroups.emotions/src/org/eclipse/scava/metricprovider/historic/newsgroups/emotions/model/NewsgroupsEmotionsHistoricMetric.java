/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.emotions.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupsEmotionsHistoricMetric extends Pongo {
	
	protected List<Newsgroups> newsgroups = null;
	protected List<Emotion> emotions = null;
	
	
	public NewsgroupsEmotionsHistoricMetric() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
		dbObject.put("emotions", new BasicDBList());
	}
	
	
	
	
	
	public List<Newsgroups> getNewsgroups() {
		if (newsgroups == null) {
			newsgroups = new PongoList<Newsgroups>(this, "newsgroups", true);
		}
		return newsgroups;
	}
	public List<Emotion> getEmotions() {
		if (emotions == null) {
			emotions = new PongoList<Emotion>(this, "emotions", true);
		}
		return emotions;
	}
	
	
}
