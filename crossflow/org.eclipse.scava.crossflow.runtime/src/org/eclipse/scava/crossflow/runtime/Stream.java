package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;

public interface Stream {
	
	public void stop() throws Exception;

	public boolean isBroadcast();
	
	public Collection<String> getDestinationNames();
	
}
