/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostDetectingEmotionClassificationCollection extends PongoCollection<ForumPostDetectingEmotionClassification> {
	
	public ForumPostDetectingEmotionClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostDetectingEmotionClassification> findById(String id) {
		return new IteratorIterable<ForumPostDetectingEmotionClassification>(new PongoCursorIterator<ForumPostDetectingEmotionClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ForumPostDetectingEmotionClassification> iterator() {
		return new PongoCursorIterator<ForumPostDetectingEmotionClassification>(this, dbCollection.find());
	}
	
	public void add(ForumPostDetectingEmotionClassification forumPostDetectingEmotionClassification) {
		super.add(forumPostDetectingEmotionClassification);
	}
	
	public void remove(ForumPostDetectingEmotionClassification forumPostDetectingEmotionClassification) {
		super.remove(forumPostDetectingEmotionClassification);
	}
	
}