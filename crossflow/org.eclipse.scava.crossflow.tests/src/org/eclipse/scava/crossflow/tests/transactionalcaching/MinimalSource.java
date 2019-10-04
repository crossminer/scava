package org.eclipse.scava.crossflow.tests.transactionalcaching;

import java.util.List;

public class MinimalSource extends MinimalSourceBase {

	protected List<String> elements;

	@Override
	public void produce() {
		for (String n : elements) {
			sendToInput(new Element(n));
		}
	}

	public void setElements(List<String> e) {
		this.elements = e;
	}

}