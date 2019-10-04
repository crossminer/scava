package org.eclipse.scava.crossflow.examples.ack;

import java.util.Timer;
import java.util.TimerTask;

public class Source extends SourceBase {

	@Override
	public void produce() throws Exception {
//		Timer t = new Timer();
//		t.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				IntElement intElement1 = new IntElement((int) (Math.random() * 10));
//				// intElement1.setValue( int );
//				System.out.println(intElement1.value);
//				sendToNumbers(intElement1);
//			}
//		}, 0, 50);
//
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				t.cancel();
//			}
//		}, 1000);
		
		for(int i = 0;i<10;i++) {
			IntElement intElement1 = new IntElement(i);
			// intElement1.setValue( int );
			System.out.println(intElement1.value);
			sendToNumbers(intElement1);		
		}
	}

}