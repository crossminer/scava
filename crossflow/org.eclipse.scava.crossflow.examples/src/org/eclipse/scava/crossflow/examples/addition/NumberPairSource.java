package org.eclipse.scava.crossflow.examples.addition;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NumberPairSource extends NumberPairSourceBase {

	@Override
	public void produce() {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				NumberPair pair = new NumberPair();
				pair.setA(new Random().nextInt(2));
				pair.setB(new Random().nextInt(2));
				//System.out.println("[" + workflow.getName() + "] Sending " + pair.getA() + " + " + pair.getB());
				getAdditions().send(pair);
			}
		}, 0, 100);
	}

}
