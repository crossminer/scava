package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;

public interface Channel {
	
	public void stop() throws Exception;

	public boolean isBroadcast();
	
	public Collection<String> getPhysicalNames();
	
}
