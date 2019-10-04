package org.eclipse.scava.crossflow.tests.configurable.addition;

public class PostConfigSource extends PostConfigSourceBase {
	
	@Override
	public void produce() throws Exception {
		sendToPostConfigTopic(new Post());
		System.out.println("sent: "+new Post());
	}


}
