package org.eclipse.scava.metricprovider.historic.forums.emotions.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ForumsEmotionsHistoricMetric extends Pongo {
	
	protected List<ForumEmotionData> forumEmotionData = null;
	protected List<Dimension> dimension = null;
	
	
	public ForumsEmotionsHistoricMetric() { 
		super();
		dbObject.put("forumEmotionData", new BasicDBList());
		dbObject.put("dimension", new BasicDBList());
	}
	
	
	
	
	
	public List<ForumEmotionData> getForumEmotionData() {
		if (forumEmotionData == null) {
			forumEmotionData = new PongoList<ForumEmotionData>(this, "forumEmotionData", true);
		}
		return forumEmotionData;
	}
	public List<Dimension> getDimension() {
		if (dimension == null) {
			dimension = new PongoList<Dimension>(this, "dimension", true);
		}
		return dimension;
	}
	
	
}