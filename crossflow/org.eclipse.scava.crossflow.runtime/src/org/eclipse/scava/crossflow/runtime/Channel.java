package org.eclipse.scava.crossflow.runtime;

import java.util.Collection;

import org.eclipse.scava.crossflow.runtime.Workflow.ChannelTypes;

public interface Channel {

	public void stop() throws Exception;

	public ChannelTypes type();

	public Collection<String> getPostIds();
	
}
