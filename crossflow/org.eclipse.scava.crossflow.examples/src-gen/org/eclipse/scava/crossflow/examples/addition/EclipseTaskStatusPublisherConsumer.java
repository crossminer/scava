package org.eclipse.scava.crossflow.examples.addition;

import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public interface EclipseTaskStatusPublisherConsumer {
	public void consumeEclipseTaskStatusPublisher(TaskStatus status);
}