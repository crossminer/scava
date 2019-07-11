package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import javax.jms.Message;

public interface CodeDetectorQueueConsumer {
	
	void consumeCodeDetectorQueueWithNotifications(Post post) throws Exception;
	
}