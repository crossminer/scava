package org.eclipse.scava.crossflow.tests.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scava.crossflow.runtime.BuiltinChannelConsumer;

public class BuiltinChannelRecorder<T> implements BuiltinChannelConsumer<T> {
	
	protected List<T> recorded = new ArrayList<>();
	
	@Override
	public void consume(T t) {
		recorded.add(t);
	}

	public List<T> getRecorded() {
		return recorded;
	}
	
}
