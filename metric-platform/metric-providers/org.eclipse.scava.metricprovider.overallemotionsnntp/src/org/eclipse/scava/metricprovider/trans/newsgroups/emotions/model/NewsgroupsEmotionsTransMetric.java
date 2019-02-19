/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsEmotionsTransMetric extends PongoDB {
	
	public NewsgroupsEmotionsTransMetric() {}
	
	public NewsgroupsEmotionsTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	protected EmotionDimensionCollection dimensions = null;
	
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public EmotionDimensionCollection getDimensions() {
		return dimensions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsEmotionsTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
		dimensions = new EmotionDimensionCollection(db.getCollection("NewsgroupsEmotionsTransMetric.dimensions"));
		pongoCollections.add(dimensions);
	}
}
