package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;

import org.eclipse.scava.crossflow.runtime.Workflow.ChannelType;

public interface Channel {
	
	public void stop() throws Exception;

	public ChannelType getType();

	public Collection<String> getPhysicalNames();
	
}
