package org.eclipse.scava.crossflow.examples.ack;

import java.util.LinkedList;

public class Sink extends SinkBase {

	public LinkedList<String> results = new LinkedList<String>();

	@Override
	public void consumeResults(StringElement stringElement) throws Exception {
		results.add(stringElement.value);
	}

}