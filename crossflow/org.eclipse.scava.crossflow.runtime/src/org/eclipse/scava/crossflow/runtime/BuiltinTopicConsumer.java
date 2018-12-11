package org.eclipse.scava.crossflow.runtime;

public interface BuiltinTopicConsumer<T> {
	
	public void consume(T t);
	
}
