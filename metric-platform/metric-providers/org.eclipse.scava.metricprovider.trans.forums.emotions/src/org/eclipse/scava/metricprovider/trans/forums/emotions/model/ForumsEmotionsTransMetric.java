package org.eclipse.scava.metricprovider.trans.forums.emotions.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class ForumsEmotionsTransMetric extends PongoDB {
	
	public ForumsEmotionsTransMetric() {}
	
	public ForumsEmotionsTransMetric(DB db) {
		setDb(db);
	}
	
	protected ForumEmotionsDataCollection forums = null;
	protected EmotionDimensionCollection dimensions = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public ForumEmotionsDataCollection getForums() {
		return forums;
	}
	
	public EmotionDimensionCollection getDimensions() {
		return dimensions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		forums = new ForumEmotionsDataCollection(db.getCollection("ForumsEmotionsTransMetric.forums"));
		pongoCollections.add(forums);
		dimensions = new EmotionDimensionCollection(db.getCollection("ForumsEmotionsTransMetric.dimensions"));
		pongoCollections.add(dimensions);
	}
}