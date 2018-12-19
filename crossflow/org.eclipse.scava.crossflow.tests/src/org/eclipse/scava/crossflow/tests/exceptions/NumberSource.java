package org.eclipse.scava.crossflow.tests.exceptions;

import java.util.Arrays;

public class NumberSource extends NumberSourceBase {
	
	@Override
	public void produce() throws Exception {
		for (int i : Arrays.asList(1, 2, 3)) {
			sendToNumbers(new Number(i));
		}
	}

}