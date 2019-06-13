package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import javax.jms.Message;

public interface PlainTextQueueConsumer {
	
	void consumePlainTextQueueWithNotifications(Post post) throws Exception;
	
}