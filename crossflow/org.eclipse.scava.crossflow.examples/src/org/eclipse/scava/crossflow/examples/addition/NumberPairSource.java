package org.eclipse.scava.crossflow.examples.addition;

import java.util.Random;

public class NumberPairSource extends NumberPairSourceBase {

	@Override
	public void produce() {

		for (int i = 0; i < 10; i++) {
			sendToAdditions(new NumberPair(new Random().nextInt(2), new Random().nextInt(2)));
		}

	}

}
