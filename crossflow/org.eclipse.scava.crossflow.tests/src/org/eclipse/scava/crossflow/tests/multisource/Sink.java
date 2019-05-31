package org.eclipse.scava.crossflow.tests.multisource;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

public class Sink extends SinkBase {

	private LinkedList<Map.Entry<String, Boolean>> results = new LinkedList<>();

	public LinkedList<Map.Entry<String, Boolean>> getResults() {
		return results;
	}

	@Override
	public void consumeResults(StringElement stringElement) throws Exception {
		String value = stringElement.getValue();
		Boolean isSourceFinished = null;
		if (value.contentEquals("Source1"))
			isSourceFinished = workflow.getSource1().getFinished();
		else if (value.contentEquals("Source2"))
			isSourceFinished = workflow.getSource2().getFinished();

		results.add(new AbstractMap.SimpleEntry<>(value, isSourceFinished));
	}

}