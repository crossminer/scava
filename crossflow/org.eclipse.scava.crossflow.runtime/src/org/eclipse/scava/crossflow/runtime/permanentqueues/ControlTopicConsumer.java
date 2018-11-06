package org.eclipse.scava.crossflow.runtime.permanentqueues;

import org.eclipse.scava.crossflow.runtime.utils.ControlSignal;

public interface ControlTopicConsumer {
	public void consumeControlTopic(ControlSignal signal);
}