package org.eclipse.scava.crossflow.tests.configurable.addition;

public class PostConfigurationConfigSource extends PostConfigurationConfigSourceBase {
	
	@Override
	public void produce() throws Exception {
		sendToPostConfigurationTopic(new PostConfiguration());
		System.out.println("sent: "+new PostConfiguration());
	}


}
