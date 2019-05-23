package org.eclipse.scava.crossflow.tests.configurable.addition;

import java.util.Timer;
import java.util.TimerTask;

public class NumberPairSource extends NumberPairSourceBase {
	
	@Override
	public void produce() throws Exception {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
			
				// TODO: Add implementation that instantiates, sets, and submits source objects (example below)
				NumberPair numberPair1 = new NumberPair();
				//	numberPair1.setA( int );
				//	numberPair1.setB( int );
		
				sendToAdditions( numberPair1);
				
				
			}
		}, 0, 100);
	}


}