package org.eclipse.scava.crossflow.runtime.permanentqueues;

import org.eclipse.scava.crossflow.runtime.utils.TaskStatus;

public interface TaskStatusPublisherConsumer {
	public void consumeTaskStatusPublisher(TaskStatus status);
}