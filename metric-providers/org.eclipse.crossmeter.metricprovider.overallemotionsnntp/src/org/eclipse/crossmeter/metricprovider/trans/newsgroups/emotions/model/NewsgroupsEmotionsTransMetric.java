/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.emotions.model;

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