package org.eclipse.scava.metricprovider.trans.detectingcode.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostDetectingCodeCollection extends PongoCollection<ForumPostDetectingCode> {
	
	public ForumPostDetectingCodeCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostDetectingCode> findById(String id) {
		return new IteratorIterable<ForumPostDetectingCode>(new PongoCursorIterator<ForumPostDetectingCode>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ForumPostDetectingCode> iterator() {
		return new PongoCursorIterator<ForumPostDetectingCode>(this, dbCollection.find());
	}
	
	public void add(ForumPostDetectingCode forumPostDetectingCode) {
		super.add(forumPostDetectingCode);
	}
	
	public void remove(ForumPostDetectingCode forumPostDetectingCode) {
		super.remove(forumPostDetectingCode);
	}
	
}