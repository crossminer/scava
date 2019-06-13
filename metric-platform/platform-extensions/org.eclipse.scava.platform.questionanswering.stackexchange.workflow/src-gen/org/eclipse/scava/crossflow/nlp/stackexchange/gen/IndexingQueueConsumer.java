package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import javax.jms.Message;

public interface IndexingQueueConsumer {
	
	void consumeIndexingQueueWithNotifications(Post post) throws Exception;
	
}