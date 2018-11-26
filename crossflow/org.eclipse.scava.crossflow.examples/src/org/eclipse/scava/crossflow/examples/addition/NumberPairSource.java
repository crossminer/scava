package org.eclipse.scava.crossflow.examples.addition;

import java.util.Random;

public class NumberPairSource extends NumberPairSourceBase {

	@Override
	public void produce() {

		for (int i = 0; i < 10; i++) {
			NumberPair pair = new NumberPair();
			pair.setA(new Random().nextInt(2));
			pair.setB(new Random().nextInt(2));
			// System.out.println("[" + workflow.getName() + "] Sending " + pair.getA() + "
			// + " + pair.getB());
			sendToAdditions(pair);

		}

	}

}
